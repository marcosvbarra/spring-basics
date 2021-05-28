package com.spring.basics.controller;

import com.spring.basics.AvaliationService;
import com.spring.basics.api.AvaliationApi;
import com.spring.basics.api.request.CreateAvaliationRequest;
import com.spring.basics.entity.Avaliation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class AvaliationController implements AvaliationApi {

    @Autowired
    private AvaliationService avaliationService;

    @Override
    public Avaliation createAvaliation(@Valid @RequestBody CreateAvaliationRequest avaliationRequest) {
        return avaliationService.createAvaliation(avaliationRequest);
    }

    @Override
    public void deleteAvaliation(@PathVariable Long id) {
        avaliationService.deleteAvaliation(id);
    }

}
