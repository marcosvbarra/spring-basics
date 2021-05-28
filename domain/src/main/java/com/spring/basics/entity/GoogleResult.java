package com.spring.basics.entity;

import lombok.Data;

import java.util.List;

@Data
public class GoogleResult {
    private String formatted_address;
    private GoogleGeometry geometry;
    private List<GoogleAddressComponent> address_components;
}
