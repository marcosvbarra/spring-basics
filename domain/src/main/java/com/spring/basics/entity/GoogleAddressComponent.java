package com.spring.basics.entity;

import lombok.Data;

import java.util.List;

@Data
public class GoogleAddressComponent {
    private String long_name;
    private String short_name;
    private List<String> types;
}
