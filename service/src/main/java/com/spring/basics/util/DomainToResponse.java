package com.spring.basics.util;

import com.spring.basics.api.response.WorkerResponse;
import com.spring.basics.entity.ActuationArea;
import com.spring.basics.entity.Avaliation;
import com.spring.basics.entity.Worker;

import java.util.List;

public class DomainToResponse {

    public static WorkerResponse convertToCustomerResponse(Worker worker, List<ActuationArea> actuationAreas, List<Avaliation> avaliations) {

        WorkerResponse workerResponse = new WorkerResponse();
        workerResponse.setId(worker.getId());
        workerResponse.setCep(worker.getCep());
        workerResponse.setLatitude(worker.getLatitude());
        workerResponse.setLongitude(worker.getLongitude());
        workerResponse.setFormattedAddress(worker.getFormattedAddress());
        workerResponse.setCpf(worker.getCpf());
        workerResponse.setName(worker.getName());
        workerResponse.setCreatedAt(worker.getCreatedAt());
        workerResponse.setPhoneNumber(worker.getPhoneNumber());
        workerResponse.setEmail(worker.getEmail());
        workerResponse.setAvaliations(avaliations);
        workerResponse.setActuationAreas(actuationAreas);
        workerResponse.setDocumentPhoto(worker.getDocumentPhoto());
        workerResponse.setDescription(worker.getDescription());
        workerResponse.setIsActive(worker.getIsActive());
        workerResponse.setRating(worker.getRating());
        workerResponse.setPlan(worker.getPlan());
        workerResponse.setRemainingContacts(worker.getRemainingContacts());
        workerResponse.setRevealedContacts(worker.getRevealedContacts());
        workerResponse.setRenewalDate(worker.getRenewalDate());
        workerResponse.setCity(worker.getCity());
        return workerResponse;

    }
}
