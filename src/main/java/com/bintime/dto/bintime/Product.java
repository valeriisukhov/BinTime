package com.bintime.dto.bintime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Product {

    private Long id;
    private String mpn;
    private ArrayList<Long> offers = new ArrayList<>();

    public Product() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMpn() {
        return mpn;
    }

    public void setMpn(String mpn) {
        this.mpn = mpn;
    }

    public ArrayList<Long> getOffers() {
        return offers;
    }

    public void setOffers(ArrayList<Long> offers) {
        this.offers = offers;
    }
}
