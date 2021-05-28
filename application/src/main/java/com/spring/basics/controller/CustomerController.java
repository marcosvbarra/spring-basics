package com.spring.basics.controller;

import com.spring.basics.CustomerService;
import com.spring.basics.api.CustomerApi;
import com.spring.basics.api.request.CreateCustomerRequest;
import com.spring.basics.api.request.FindCustomerRequest;
import com.spring.basics.api.request.RequestJobRequest;
import com.spring.basics.api.request.UpdateCustomerRequest;
import com.spring.basics.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
public class CustomerController implements CustomerApi {

    @Autowired
    private CustomerService customerService;

    @Override
    public Customer saveCustomer(@Valid @RequestBody CreateCustomerRequest customer) {

        return customerService.createCustomer(customer);
    }

    @Override
    public Customer findCustomerById(Long customerId) {
        return customerService.findCustomerById(customerId);
    }

    @Override
    public Customer findCustomer(@Valid @RequestBody FindCustomerRequest findCustomerRequest) {
        return customerService.findCustomer(findCustomerRequest);
    }

    @Override
    public Customer updateCustomer(@NotNull(message = "The ID is required") @PathVariable Long customerId,
                                   @Valid @RequestBody UpdateCustomerRequest updateCustomerRequest) {

        return customerService.updateCustomer(customerId, updateCustomerRequest);
    }

    @Override
    public void deleteCustomer(Long customerId) {
        customerService.deleteCustomer(customerId);
    }

    @Override
    public void requestJob(@RequestBody @Valid RequestJobRequest requestJobRequest) {
        customerService.requestJob(requestJobRequest);
    }


}
