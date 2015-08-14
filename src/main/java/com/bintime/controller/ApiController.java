package com.bintime.controller;

import com.bintime.dto.ProductDto;
import com.bintime.services.impl.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/**")
public class ApiController extends BaseController {

    @Autowired
    private RedisService redisService;

    @RequestMapping(value = "product-list")
    public List<ProductDto> productList(){
        return redisService.productList();
    }

}
