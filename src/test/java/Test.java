import com.bintime.dto.bintime.Offer;
import com.bintime.dto.bintime.Product;
import com.bintime.services.impl.RedisService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
        "classpath:/spring/service.xml",
        "classpath:/spring/spring-context.xml"})
public class Test {

    @Autowired
    private RedisService redisService;

    @org.junit.Test
    public void test(){
        Offer offer1 = new Offer();
        offer1.setPrice(100.0);
        offer1.setAvailable(1);
        offer1 = redisService.storeOffer(offer1);

        Offer offer2 = new Offer();
        offer1.setPrice(66.0);
        offer1.setAvailable(2);
        offer2 = redisService.storeOffer(offer2);

        //1
        Product product1 = new Product();
        product1.setMpn("SA-1KP");
        ArrayList<Long> offersId = new ArrayList<Long>();
        offersId.add(offer1.getId());
        offersId.add(offer2.getId());
        product1.setOffers(offersId);
        product1 = redisService.storeProduct(product1);

        Jedis jedis = redisService.getClient();
        Set<String> productKeysSet = jedis.keys("product:*:mpn:SA-1KP");
        System.out.println(productKeysSet);

    }
}
