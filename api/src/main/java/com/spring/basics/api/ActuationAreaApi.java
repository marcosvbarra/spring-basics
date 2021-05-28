package com.spring.basics.api;


import com.spring.basics.api.request.CreateActuationAreaRequest;
import com.spring.basics.api.request.UpdateActuationAreaRequest;
import com.spring.basics.entity.ActuationArea;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "/areas")
public interface ActuationAreaApi {

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    ActuationArea createActuationArea(@RequestBody CreateActuationAreaRequest actuationAreaRequest);

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    List<ActuationArea> findAllActuationAreas();

    @PutMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    ActuationArea updateActuationArea(@RequestBody UpdateActuationAreaRequest updateActuationAreaRequest, @PathVariable Long id);

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    void deleteActuationArea(@PathVariable Long id);
}
