package com.bintime.controller;

import com.bintime.dto.rest.PriceDto;
import com.bintime.services.RestfulApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/restful.api/**")
public class BintimeController {

    @Autowired
    private RestfulApiService restfulApiService;


    @RequestMapping(value = "getprice", method = RequestMethod.GET)
    public PriceDto getPrice(@RequestParam String mpn,
                             @RequestParam(value = "available", defaultValue = "0") int available,
                             @RequestParam(value = "sortKey", defaultValue = "0") int sortKey){
        PriceDto priceDto = restfulApiService.getPrice(mpn, available, sortKey);
        return priceDto;
    }
}
