package com.spring.basics;

import com.google.gson.Gson;
import com.spring.basics.api.request.CreateCustomerRequest;
import com.spring.basics.api.request.FindCustomerRequest;
import com.spring.basics.api.request.RequestJobRequest;
import com.spring.basics.api.request.UpdateCustomerRequest;
import com.spring.basics.configuration.KafkaProducer;
import com.spring.basics.entity.*;
import com.spring.basics.exception.ActuationAreaNotFoundException;
import com.spring.basics.exception.CustomerAlreadyRegisteredException;
import com.spring.basics.exception.CustomerNotFoundException;
import com.spring.basics.exception.InvalidJobRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);
    private static final Gson gson = new Gson();

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private KafkaProducer kafkaProducer;

    @Autowired
    private JobRequestRepository jobRequestRepository;

    @Autowired
    private ActuationAreaRepository actuationAreaRepository;

    @Autowired
    private GoogleMapsIntegration googleMapsIntegration;

    public Customer createCustomer(CreateCustomerRequest createCustomerRequest) {
        logger.info("Received request to create a new customer");


        Optional<Customer> existentCustomer = customerRepository.findByEmail(createCustomerRequest.getEmail());
        if (existentCustomer.isPresent())
            throw new CustomerAlreadyRegisteredException();

        Customer customer = customerRepository.saveAndFlush(convertCustomerRequestToCustomer(createCustomerRequest));

        logger.info("Completed request to create a new customer");
        return customer;

    }

    public Customer findCustomerById(Long customerId) {
        logger.info("Received request to find a customer by id: {}", customerId);


        Optional<Customer> customer = customerRepository.findById(customerId);
        if (customer.isEmpty())
            throw new CustomerNotFoundException();

        logger.info("Completed request to find a customer by id: {}", customerId);
        return customer.get();
    }


    public Customer findCustomer(FindCustomerRequest findCustomerRequest) {
        logger.info("Received request to find customer: {}", findCustomerRequest);

        Optional<Customer> customer = customerRepository.findByEmail(findCustomerRequest.getEmail());
        if (customer.isEmpty())
            throw new CustomerNotFoundException();

        logger.info("Completed request to find customer: {}", findCustomerRequest);
        return customer.get();

    }

    public Customer updateCustomer(Long customerId, UpdateCustomerRequest updateCustomerRequest) {
        logger.info("Received request to update customer with id: {}", customerId);


        Optional<Customer> customer = customerRepository.findById(customerId);
        if (customer.isEmpty())
            throw new CustomerNotFoundException();

        Customer newCustomer = customer.get();

        injectUpdatedFields(updateCustomerRequest, newCustomer);

        logger.info("Completed request to update customer with id: {}", customerId);
        return customerRepository.saveAndFlush(newCustomer);
    }

    public void deleteCustomer(Long customerId) {
        logger.info("Received request to delete customer with id: {}", customerId);

        Optional<Customer> customer = customerRepository.findById(customerId);
        if (customer.isEmpty())
            throw new CustomerNotFoundException();

        customerRepository.delete(customer.get());
        logger.info("Completed request to delete customer with id: {}", customerId);

    }

    public void requestJob(RequestJobRequest requestJobRequest) {

        logger.info("Received request job for email: {}", requestJobRequest.getEmail());

        if (requestJobRequest.getPriority().equals(PriorityEnum.DATA_ESPECIFICA) && requestJobRequest.getStartDate() == null)
            throw new InvalidJobRequestException("For specific date, job request must have a start date.");

        Optional<ActuationArea> actuationArea = actuationAreaRepository.findById(requestJobRequest.getActuationArea());
        if (actuationArea.isEmpty())
            throw new ActuationAreaNotFoundException();

        Optional<Customer> customer = customerRepository.findByEmail(requestJobRequest.getEmail());
        Customer existentCustomer = customer.orElse(null);
        if (customer.isEmpty()) {
            Customer newCustomer = convertToCustomerByJobRequest(requestJobRequest.getCep(), requestJobRequest.getEmail(), requestJobRequest.getPhoneNumber(), requestJobRequest.getName());
            existentCustomer = customerRepository.saveAndFlush(newCustomer);
            logger.info("Customer created for email: {}", requestJobRequest.getEmail());

        } else {
            if (!customer.get().getCep().equals(requestJobRequest.getCep())) {
                Customer newCustomer = customer.get();
                setNewAddress(newCustomer, requestJobRequest.getCep());
                existentCustomer = customerRepository.saveAndFlush(newCustomer);
            }
        }

        JobRequest jobRequest = createJobRequest(requestJobRequest, existentCustomer);
        logger.info("Job request saved on database for email: {}", requestJobRequest.getEmail());
        kafkaProducer.sendMessage(gson.toJson(jobRequest), jobRequest.getId().toString());

        logger.info("Completed request job for email: {}", requestJobRequest.getEmail());
    }


//    PRIVATE METHODS

    private void setNewAddress(Customer newCustomer, String cep) {
        newCustomer.setCep(cep);
        GoogleAPIResponse localizationByCep = googleMapsIntegration.getLocalizationByCep(cep);
        String city = localizationByCep.getResults().get(0).getAddress_components().stream()
                .filter(googleAddressComponent -> googleAddressComponent.getTypes().contains("administrative_area_level_2"))
                .collect(Collectors.toList()).get(0)
                .getLong_name();

        newCustomer.setLatitude(localizationByCep.getResults().get(0).getGeometry().getLocation().getLat().toString());
        newCustomer.setLongitude(localizationByCep.getResults().get(0).getGeometry().getLocation().getLng().toString());
        newCustomer.setFormattedAddress(localizationByCep.getResults().get(0).getFormatted_address());
        newCustomer.setCity(city);

    }

    private JobRequest createJobRequest(RequestJobRequest requestJobRequest, Customer customer) {
        JobRequest jobRequest = new JobRequest();
        jobRequest.setIdCustomer(customer.getId());
        jobRequest.setIdActuationArea(requestJobRequest.getActuationArea());
        jobRequest.setCep(requestJobRequest.getCep());
        jobRequest.setCity(customer.getCity());
        jobRequest.setLatitude(customer.getLatitude());
        jobRequest.setLongitude(customer.getLongitude());
        jobRequest.setFormattedAddress(customer.getFormattedAddress());
        jobRequest.setPriority(requestJobRequest.getPriority());
        jobRequest.setStartDate(requestJobRequest.getStartDate());
        jobRequest.setJobInformation(requestJobRequest.getJobInformation());
        jobRequest.setStatus(JobRequestStatusEnum.PENDING);
        jobRequest.setRetryAmount(0L);
        jobRequest.setCreatedAt(LocalDateTime.now());
        jobRequest.setInterestedWorkers(0L);
        return jobRequestRepository.saveAndFlush(jobRequest);
    }

    private Customer convertToCustomerByJobRequest(String cep, String email, String phoneNumber, String name) {
        GoogleAPIResponse localizationByCep = googleMapsIntegration.getLocalizationByCep(cep);
        String city = localizationByCep.getResults().get(0).getAddress_components().stream()
                .filter(googleAddressComponent -> googleAddressComponent.getTypes().contains("administrative_area_level_2"))
                .collect(Collectors.toList()).get(0)
                .getLong_name();

        Customer newCustomer = new Customer();
        newCustomer.setCep(cep);
        newCustomer.setCity(city);
        newCustomer.setEmail(email);
        newCustomer.setPhoneNumber(phoneNumber);
        newCustomer.setName(name);
        newCustomer.setCreatedAt(LocalDateTime.now());
        newCustomer.setLatitude(localizationByCep.getResults().get(0).getGeometry().getLocation().getLat().toString());
        newCustomer.setLongitude(localizationByCep.getResults().get(0).getGeometry().getLocation().getLng().toString());
        newCustomer.setFormattedAddress(localizationByCep.getResults().get(0).getFormatted_address());
        return newCustomer;
    }

    private void injectUpdatedFields(UpdateCustomerRequest updateCustomerRequest, Customer customer) {
        if (updateCustomerRequest.getName() != null) customer.setName(updateCustomerRequest.getName());
        if (updateCustomerRequest.getCep() != null) {
            setNewAddress(customer, updateCustomerRequest.getCep());
        }
        if (updateCustomerRequest.getPhoneNumber() != null)
            customer.setPhoneNumber(updateCustomerRequest.getPhoneNumber());
        if (updateCustomerRequest.getEmail() != null) customer.setEmail(updateCustomerRequest.getEmail());
    }


    private Customer convertCustomerRequestToCustomer(CreateCustomerRequest createCustomerRequest) {
        return convertToCustomerByJobRequest(createCustomerRequest.getCep(), createCustomerRequest.getEmail(), createCustomerRequest.getPhoneNumber(), createCustomerRequest.getName());
    }

}
