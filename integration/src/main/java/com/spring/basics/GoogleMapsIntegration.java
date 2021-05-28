package com.spring.basics;

import com.google.gson.Gson;
import com.spring.basics.entity.GoogleAPIResponse;
import com.linecorp.armeria.client.WebClient;
import com.linecorp.armeria.common.AggregatedHttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class GoogleMapsIntegration {
    private static final Logger logger = LoggerFactory.getLogger(GoogleMapsIntegration.class);
    private static final Gson gson = new Gson();

    @Value(value = "${google.api.key}")
    private String googleApiKey;


    public GoogleAPIResponse getLocalizationByCep(String cep) {

        WebClient webClient = WebClient.of();
        AggregatedHttpResponse response = webClient.get("https://maps.googleapis.com/maps/api/geocode/json?address=" +
                cep +
                "&key=" + googleApiKey)
                .aggregate().join();


        return gson.fromJson(response.contentUtf8(), GoogleAPIResponse.class);
    }
}
