package com.bintime.dto.rest;

import com.bintime.dto.ProductDto;
import com.bintime.dto.bintime.Offer;

import java.util.ArrayList;

public class PriceDto {

    private String mpn = "";
    private String status = "ERROR";
    private Long id;
    private ArrayList<Offer> offers = new ArrayList<>();

    public PriceDto() {
    }

    public String getMpn() {
        return mpn;
    }

    public void setMpn(String mpn) {
        this.mpn = mpn;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ArrayList<Offer> getOffers() {
        return offers;
    }

    public void setOffers(ArrayList<Offer> offers) {
        this.offers = offers;
    }

    public static PriceDto fromProduct(ProductDto product){
        PriceDto priceDto = new PriceDto();
        priceDto.setId(product.getId());
        priceDto.setMpn(product.getMpn());
        priceDto.setStatus("OK");
        priceDto.setOffers(product.getOffers());
        return priceDto;
    }
}
