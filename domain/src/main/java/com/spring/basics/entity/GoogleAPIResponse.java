package com.spring.basics.entity;

import lombok.Data;

import java.util.List;

@Data
public class GoogleAPIResponse {

    private List<GoogleResult> results;
    private String status;
}
