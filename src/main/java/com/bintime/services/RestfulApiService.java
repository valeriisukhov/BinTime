package com.bintime.services;

import com.bintime.dto.rest.PriceDto;

public interface RestfulApiService {

    PriceDto getPrice(String mpn, int available, int sortKey);
}
