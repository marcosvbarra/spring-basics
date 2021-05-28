package com.spring.basics;

import com.spring.basics.api.request.CreateActuationAreaRequest;
import com.spring.basics.api.request.UpdateActuationAreaRequest;
import com.spring.basics.entity.ActuationArea;
import com.spring.basics.exception.ActuationAreaAlreadyRegisteredException;
import com.spring.basics.exception.ActuationAreaNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ActuationAreaService {


    @Autowired
    private ActuationAreaRepository actuationAreaRepository;

    public ActuationArea createActuationArea(CreateActuationAreaRequest actuationAreaRequest) {

        Optional<ActuationArea> existentActuationArea = actuationAreaRepository.findByName(actuationAreaRequest.getName());

        if (existentActuationArea.isPresent())
            throw new ActuationAreaAlreadyRegisteredException();

        ActuationArea actuationArea = getActuationAreaByRequest(actuationAreaRequest);
        return actuationAreaRepository.saveAndFlush(actuationArea);
    }

    public List<ActuationArea> findAllActuationAreas() {

        return actuationAreaRepository.findAll();
    }

    public ActuationArea updateActuationArea(UpdateActuationAreaRequest actuationAreaRequest, Long id) {

        Optional<ActuationArea> actuationArea = actuationAreaRepository.findById(id);
        if (actuationArea.isEmpty())
            throw new ActuationAreaNotFoundException();

        ActuationArea newActuationArea = actuationArea.get();
        injectUpdatedFields(actuationAreaRequest, newActuationArea);

        return actuationAreaRepository.saveAndFlush(newActuationArea);

    }

    public void deleteActuationArea(Long id) {

        Optional<ActuationArea> actuationArea = actuationAreaRepository.findById(id);
        if (actuationArea.isEmpty())
            throw new ActuationAreaNotFoundException();

        actuationAreaRepository.deleteById(id);

    }

    private void injectUpdatedFields(UpdateActuationAreaRequest actuationAreaRequest, ActuationArea actuationArea) {

        if (actuationAreaRequest.getName() != null) actuationArea.setName(actuationAreaRequest.getName());
        if (actuationAreaRequest.getIsActive() != null) actuationArea.setIsActive(actuationAreaRequest.getIsActive());
        if (actuationAreaRequest.getDescription() != null)
            actuationArea.setDescription(actuationAreaRequest.getDescription());
    }

    private ActuationArea getActuationAreaByRequest(CreateActuationAreaRequest actuationAreaRequest) {
        ActuationArea actuationArea = new ActuationArea();
        actuationArea.setName(actuationAreaRequest.getName());
        actuationArea.setDescription(actuationAreaRequest.getDescription());
        actuationArea.setIsActive(true);
        actuationArea.setCreatedAt(LocalDateTime.now());
        return actuationArea;
    }
}
