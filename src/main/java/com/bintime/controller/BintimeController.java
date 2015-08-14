package com.bintime.controller;

import com.bintime.dto.rest.PriceDto;
import com.bintime.services.RestfulApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/restful.api/**")
public class BintimeController {

    @Autowired
    private RestfulApiService restfulApiService;

    @RequestMapping(value = "getprice")
    public PriceDto getPrice(@RequestParam String mpn,
                             @RequestParam(value = "available", defaultValue = "0") int available,
                             @RequestParam(value = "sortKey", defaultValue = "0") int sortKey){
        return restfulApiService.getPrice(mpn, available, sortKey);
    }
}
