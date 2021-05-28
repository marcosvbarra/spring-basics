package com.spring.basics.api;


import com.spring.basics.api.request.CreateAvaliationRequest;
import com.spring.basics.entity.Avaliation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/avaliations")
public interface AvaliationApi {

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    Avaliation createAvaliation(@RequestBody CreateAvaliationRequest avaliationRequest);

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    void deleteAvaliation(@PathVariable Long id);


}
