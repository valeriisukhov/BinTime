package com.bintime.services.impl;

import com.bintime.dto.ProductDto;
import com.bintime.dto.bintime.Offer;
import com.bintime.dto.bintime.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * <p>Redis service.</p>
 *
 * @author Valerii Sukhov valerii.sukhov@gmail.com
 * @see    Jedis
 */
@Qualifier("redisService")
public class RedisService {
    @Value("#{properties['redis.host']}")
    private String redisHost;
    @Value("#{properties['redis.port']}")
    private int redisPort;
    @Value("#{properties['redis.pass']}")
    private String redisPass;

    private static final String OFFER_KEY = "offer:";
    private static final String PRODUCT_KEY = "product:";

    private static JedisPool jedisPool;

    @PostConstruct
    public void initJedisPool(){
        GenericObjectPoolConfig config = new GenericObjectPoolConfig();
        config.setMaxTotal(64);
        config.setMaxIdle(63);
        config.setMinIdle(1);
        jedisPool = new JedisPool(config,redisHost,redisPort, 2000, redisPass);
    }

    public Jedis getClient(){
        return jedisPool.getResource();
    }

    public void returnClient(Jedis client){
        jedisPool.returnResourceObject(client);
    }

    public Offer storeOffer(Offer offer){
        Jedis jedis = getClient();
        //This is the logic of dynamic Id generation (on real server must be AtomicLong)
        offer.setId(new Date().getTime());
        ObjectMapper mapper = new ObjectMapper();
        String offerJson;
        try {
            offerJson = mapper.writeValueAsString(offer);
            jedis.set(OFFER_KEY+offer.getId(),offerJson);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            returnClient(jedis);
        }
        return offer;
    }

    public Product storeProduct(Product product){
        Jedis jedis = getClient();
        //This is the logic of dynamic Id generation (on real server must be AtomicLong)
        product.setId(new Date().getTime());
        ObjectMapper mapper = new ObjectMapper();
        String productJson;
        try {
            productJson = mapper.writeValueAsString(product);
            jedis.set(PRODUCT_KEY+product.getId()+":mpn:" + product.getMpn(),productJson);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            returnClient(jedis);
        }
        return product;
    }

    private List<Product> getAllProducts(){
        Jedis jedis = getClient();
        Set<String> productKeysSet = jedis.keys(PRODUCT_KEY + "*");
        ArrayList<Product> productList = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        for (String s : productKeysSet){
            String jsonProduct = jedis.get(s);
            try {
                Product product = mapper.readValue(jsonProduct, Product.class);
                productList.add(product);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        returnClient(jedis);
        return productList;
    }
    private List<Product> getAllProducts(String mpn){
        Jedis jedis = getClient();
        Set<String> productKeysSet = jedis.keys(PRODUCT_KEY + "*:mpn:" + mpn);
        ArrayList<Product> productList = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        for (String s : productKeysSet){
            String jsonProduct = jedis.get(s);
            try {
                Product product = mapper.readValue(jsonProduct, Product.class);
                productList.add(product);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        returnClient(jedis);
        return productList;
    }

    private Offer getOfferById(Long id){
        Jedis jedis = getClient();
        String offerJson = jedis.get(OFFER_KEY + id);
        ObjectMapper mapper = new ObjectMapper();
        Offer offer = null;
        try {
            offer = mapper.readValue(offerJson, Offer.class);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            returnClient(jedis);
        }
        return offer;
    }

    public List<ProductDto> productList(){
        ArrayList<ProductDto> result = new ArrayList<>();

        List<Product> productListFromStorage = this.getAllProducts();
        for (Product p : productListFromStorage){
            ProductDto dto = new ProductDto();
            dto.setId(p.getId());
            dto.setMpn(p.getMpn());
            ArrayList<Offer> offers = new ArrayList<>();
            ArrayList<Long> offerIdList = p.getOffers();

            offerIdList.forEach(id -> offers.add(getOfferById(id)));

            dto.setOffers(offers);
            result.add(dto);
        }
        return result;
    }

    public List<ProductDto> productList(String mpn){
        ArrayList<ProductDto> result = new ArrayList<>();

        List<Product> productListFromStorage = this.getAllProducts(mpn);
        for (Product p : productListFromStorage){
            ProductDto dto = new ProductDto();
            dto.setId(p.getId());
            dto.setMpn(p.getMpn());
            ArrayList<Offer> offers = new ArrayList<>();
            ArrayList<Long> offerIdList = p.getOffers();

            offerIdList.forEach(id -> offers.add(getOfferById(id)));

            dto.setOffers(offers);
            result.add(dto);
        }
        return result;
    }

    public void flush(){
        Jedis jedis = this.getClient();
        jedis.flushAll();
        this.returnClient(jedis);
    }

}
