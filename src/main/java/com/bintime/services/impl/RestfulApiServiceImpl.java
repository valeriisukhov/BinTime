package com.bintime.services.impl;

import com.bintime.dto.ProductDto;
import com.bintime.dto.bintime.Offer;
import com.bintime.dto.bintime.Product;
import com.bintime.dto.rest.PriceDto;
import com.bintime.services.RestfulApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class RestfulApiServiceImpl implements RestfulApiService {

    @Autowired
    private RedisService redisService;

    @Override
    public PriceDto getPrice(String mpn, int available, int sortKey) {
        List<ProductDto> productDtos = redisService.productList(mpn);
        final List<ProductDto> sortedList = new ArrayList<>();
        switch (available){
            case 0:
                sortedList.addAll(productDtos);
                break;
            case 1:
                for (ProductDto p : productDtos){
                    p.getOffers().forEach(offer -> {
                        if (offer.getAvailable() == 1 || offer.getAvailable() == 2){
                            sortedList.add(p);
                            return;
                        }
                    });
                }
                break;
            case 2:
                for (ProductDto p : productDtos){
                p.getOffers().forEach(offer -> {
                    if (offer.getAvailable() == 2){
                        sortedList.add(p);
                        return;
                    }
                });
            };
        }
        if (!sortedList.isEmpty()){
            switch (sortKey){
                case 1:
                    sortedList.forEach(productDto -> {
                        ArrayList<Offer> offers = productDto.getOffers();
                        offers.sort((off1, off2) -> off1.getPrice().compareTo(off2.getPrice()));
                    });
                    break;
                case 2:
                    sortedList.forEach(productDto -> {
                        ArrayList<Offer> offers = productDto.getOffers();
                        offers.sort((off1, off2) -> off2.getPrice().compareTo(off1.getPrice()));
                    });
                    break;
            }
        }
        PriceDto result = new PriceDto();
        if (!sortedList.isEmpty()){
            ProductDto target = sortedList.get(0);
            result = PriceDto.fromProduct(target);
        }
        return result;
    }
}
