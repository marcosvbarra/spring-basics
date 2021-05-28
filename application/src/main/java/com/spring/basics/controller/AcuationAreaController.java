package com.spring.basics.controller;

import com.spring.basics.ActuationAreaService;
import com.spring.basics.api.ActuationAreaApi;
import com.spring.basics.api.request.CreateActuationAreaRequest;
import com.spring.basics.api.request.UpdateActuationAreaRequest;
import com.spring.basics.entity.ActuationArea;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
public class AcuationAreaController implements ActuationAreaApi {

    @Autowired
    private ActuationAreaService actuationAreaService;

    @Override
    public ActuationArea createActuationArea(@Valid @RequestBody CreateActuationAreaRequest actuationAreaRequest) {
        return actuationAreaService.createActuationArea(actuationAreaRequest);
    }

    @Override
    public List<ActuationArea> findAllActuationAreas() {
        return actuationAreaService.findAllActuationAreas();
    }

    @Override
    public ActuationArea updateActuationArea(@Valid @RequestBody UpdateActuationAreaRequest updateActuationAreaRequest, @PathVariable Long id) {
        return actuationAreaService.updateActuationArea(updateActuationAreaRequest, id);
    }

    @Override
    public void deleteActuationArea(@PathVariable Long id) {
        actuationAreaService.deleteActuationArea(id);
    }
}
