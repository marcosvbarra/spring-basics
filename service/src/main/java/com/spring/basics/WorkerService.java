package com.spring.basics;

import com.spring.basics.api.request.CreateWorkerRequest;
import com.spring.basics.api.request.UpdateWorkerRequest;
import com.spring.basics.api.response.CreateWorkerResponse;
import com.spring.basics.api.response.WorkerResponse;
import com.spring.basics.entity.*;
import com.spring.basics.exception.ActuationAreaNotFoundException;
import com.spring.basics.exception.CepNotFoundException;
import com.spring.basics.exception.WorkerAlreadyRegisteredException;
import com.spring.basics.exception.WorkerNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.spring.basics.util.DomainToResponse.convertToCustomerResponse;

@Service
public class WorkerService {
    private static final Logger logger = LoggerFactory.getLogger(WorkerService.class);

    @Autowired
    private WorkerRepository workerRepository;
    @Autowired
    private AreaWorkerRepository areaWorkerRepository;
    @Autowired
    private ActuationAreaRepository actuationAreaRepository;
    @Autowired
    private AvaliationRepository avaliationRepository;
    @Autowired
    private GoogleMapsIntegration googleMapsIntegration;

    public CreateWorkerResponse createWorker(CreateWorkerRequest workerRequest) {
        logger.info("Received request to create worker with cpf: {}", workerRequest.getCpf());

        validateIfWorkerIsNew(workerRequest);
        ActuationArea actuationArea = getActuationArea(workerRequest);

        Worker worker = convertToWorker(workerRequest);
        workerRepository.saveAndFlush(worker);
        AreaWorker areaWorker = convertToAreaWorker(actuationArea, worker);
        areaWorkerRepository.saveAndFlush(areaWorker);

        logger.info("Completed request to create worker with cpf: {}", workerRequest.getCpf());
        return convertToCreateWorkerResponse(actuationArea, worker);

    }

    public WorkerResponse findWorkerById(Long id) {
        logger.info("Received request to find worker with id: {}", id);

        Optional<Worker> optionalWorker = workerRepository.findById(id);
        if (optionalWorker.isEmpty())
            throw new WorkerNotFoundException();

        List<ActuationArea> actuationAreas = getActuationAreasByCustomerId(optionalWorker.get().getId());
        List<Avaliation> avaliations = avaliationRepository.findAllByIdWorker(optionalWorker.get().getId());

        logger.info("Completed request to find worker with id: {}", id);
        return convertToCustomerResponse(optionalWorker.get(), actuationAreas, avaliations);
    }

    public WorkerResponse findWorkerByCpf(String cpf) {
        logger.info("Received request to find worker with cpf: {}", cpf);

        Optional<Worker> optionalWorker = workerRepository.findByCpf(cpf);
        if (optionalWorker.isEmpty())
            throw new WorkerNotFoundException();

        List<ActuationArea> actuationAreas = getActuationAreasByCustomerId(optionalWorker.get().getId());
        List<Avaliation> avaliations = avaliationRepository.findAllByIdWorker(optionalWorker.get().getId());

        logger.info("Completed request to find worker with cpf: {}", cpf);
        return convertToCustomerResponse(optionalWorker.get(), actuationAreas, avaliations);
    }

    public Worker updateWorker(UpdateWorkerRequest workerRequest, Long workerId) {

        logger.info("Received request to update worker with id: {}", workerId);


        Optional<Worker> optionalWorker = workerRepository.findById(workerId);
        if (optionalWorker.isEmpty())
            throw new WorkerNotFoundException();

        Worker newWorker = optionalWorker.get();

        injectUpdatedFields(workerRequest, newWorker);

        logger.info("Completed request to update worker with id: {}", workerId);
        return workerRepository.saveAndFlush(newWorker);
    }

    public void deleteWorker(Long workerId) {
        logger.info("Received request to delete worker with id: {}", workerId);


        Optional<Worker> optionalWorker = workerRepository.findById(workerId);
        if (optionalWorker.isEmpty())
            throw new WorkerNotFoundException();

        workerRepository.delete(optionalWorker.get());

        logger.info("Completed request to delete worker with id: {}", workerId);

    }
    //PRIVATE METHODS


    private List<ActuationArea> getActuationAreasByCustomerId(Long customerId) {
        List<AreaWorker> areaWorkerList = areaWorkerRepository.findAllByIdWorker(customerId);
        List<ActuationArea> actuationAreas = new ArrayList<>();
        areaWorkerList.forEach(areaWorker -> {
            Optional<ActuationArea> optionalActuationArea = actuationAreaRepository.findById(areaWorker.getIdArea());
            optionalActuationArea.ifPresent(actuationAreas::add);
        });
        return actuationAreas;
    }

    private CreateWorkerResponse convertToCreateWorkerResponse(ActuationArea actuationArea, Worker worker) {
        CreateWorkerResponse createWorkerResponse = new CreateWorkerResponse();
        createWorkerResponse.setCpf(worker.getCpf());
        createWorkerResponse.setName(worker.getName());
        createWorkerResponse.setCep(worker.getCep());
        createWorkerResponse.setDescription(worker.getDescription());
        createWorkerResponse.setEmail(worker.getEmail());
        createWorkerResponse.setCreatedAt(worker.getCreatedAt());
        createWorkerResponse.setPhoneNumber(worker.getPhoneNumber());
        createWorkerResponse.setIsActive(worker.getIsActive());
        createWorkerResponse.setDocumentPhoto(worker.getDocumentPhoto());
        createWorkerResponse.setId(worker.getId());
        createWorkerResponse.setActuationAreas(Collections.singletonList(actuationArea));
        createWorkerResponse.setRenewalDate(worker.getRenewalDate());
        createWorkerResponse.setRevealedContacts(worker.getRevealedContacts());
        createWorkerResponse.setRemainingContacts(worker.getRemainingContacts());
        createWorkerResponse.setCity(worker.getCity());
        createWorkerResponse.setPlan(worker.getPlan());
        return createWorkerResponse;
    }

    private AreaWorker convertToAreaWorker(ActuationArea actuationArea, Worker worker) {
        AreaWorker areaWorker = new AreaWorker();
        areaWorker.setIdWorker(worker.getId());
        areaWorker.setIdArea(actuationArea.getId());
        areaWorker.setIsActive(true);
        return areaWorker;
    }

    private ActuationArea getActuationArea(CreateWorkerRequest workerRequest) {
        Optional<ActuationArea> optionalActuationArea = actuationAreaRepository.findById(workerRequest.getActuationArea());
        if (optionalActuationArea.isEmpty())
            throw new ActuationAreaNotFoundException();
        return optionalActuationArea.get();
    }

    private Worker convertToWorker(CreateWorkerRequest workerRequest) {
        GoogleAPIResponse localizationByCep = googleMapsIntegration.getLocalizationByCep(workerRequest.getCep());
        if (localizationByCep.getResults().isEmpty())
            throw new CepNotFoundException();
        String city = localizationByCep.getResults().get(0).getAddress_components().stream()
                .filter(googleAddressComponent -> googleAddressComponent.getTypes().contains("administrative_area_level_2"))
                .collect(Collectors.toList()).get(0)
                .getLong_name();

        Worker worker = new Worker();
        worker.setCpf(workerRequest.getCpf());
        worker.setName(workerRequest.getName());
        worker.setCep(workerRequest.getCep());
        worker.setCity(city);
        worker.setLatitude(localizationByCep.getResults().get(0).getGeometry().getLocation().getLat().toString());
        worker.setLongitude(localizationByCep.getResults().get(0).getGeometry().getLocation().getLng().toString());
        worker.setFormattedAddress(localizationByCep.getResults().get(0).getFormatted_address());
        worker.setEmail(workerRequest.getEmail());
        worker.setPhoneNumber(workerRequest.getPhoneNumber());
        worker.setDescription(workerRequest.getDescription());
        worker.setDocumentPhoto(workerRequest.getDocumentPhoto());
        worker.setCreatedAt(LocalDateTime.now());
        worker.setIsActive(true);
        worker.setPlan(workerRequest.getPlan());
        worker.setRemainingContacts(0L);
        worker.setRevealedContacts(0L);
        worker.setRenewalDate(worker.getCreatedAt().plusMonths(1L));
        return worker;
    }

    private void validateIfWorkerIsNew(CreateWorkerRequest workerRequest) {
        Optional<Worker> existentWorkerByCpf = workerRepository.findByCpf(workerRequest.getCpf());
        if (existentWorkerByCpf.isPresent())
            throw new WorkerAlreadyRegisteredException("This cpf is already registered.");
        Optional<Worker> existentWorkerByEmail = workerRepository.findByEmail(workerRequest.getEmail());
        if (existentWorkerByEmail.isPresent())
            throw new WorkerAlreadyRegisteredException("This email is already registered.");
    }

    private void injectUpdatedFields(UpdateWorkerRequest workerRequest, Worker worker) {
        if (workerRequest.getEmail() != null) worker.setEmail(workerRequest.getEmail());
        if (workerRequest.getName() != null) worker.setName(workerRequest.getName());
        if (workerRequest.getCep() != null) {
            worker.setCep(workerRequest.getCep());
            GoogleAPIResponse localizationByCep = googleMapsIntegration.getLocalizationByCep(workerRequest.getCep());
            String city = localizationByCep.getResults().get(0).getAddress_components().stream()
                    .filter(googleAddressComponent -> googleAddressComponent.getTypes().contains("administrative_area_level_2"))
                    .collect(Collectors.toList()).get(0)
                    .getLong_name();
            worker.setLatitude(localizationByCep.getResults().get(0).getGeometry().getLocation().getLat().toString());
            worker.setLongitude(localizationByCep.getResults().get(0).getGeometry().getLocation().getLng().toString());
            worker.setFormattedAddress(localizationByCep.getResults().get(0).getFormatted_address());
            worker.setCity(city);
        }
        if (workerRequest.getPhoneNumber() != null) worker.setPhoneNumber(workerRequest.getPhoneNumber());
        if (workerRequest.getIsActive() != null) worker.setIsActive(workerRequest.getIsActive());
        if (workerRequest.getDocumentPhoto() != null) worker.setDocumentPhoto(workerRequest.getDocumentPhoto());
        if (workerRequest.getRating() != null) worker.setRating(workerRequest.getRating());
        if (workerRequest.getDescription() != null) worker.setDescription(workerRequest.getDescription());
        if (workerRequest.getPlan() != null) {
            if (!workerRequest.getPlan().equals(worker.getPlan())) {
                worker.setPlan(workerRequest.getPlan());
                worker.setRenewalDate(worker.getRenewalDate().plusMonths(1));
            }
        }
    }
}
