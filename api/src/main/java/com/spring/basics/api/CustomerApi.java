package com.spring.basics.api;


import com.spring.basics.api.request.CreateCustomerRequest;
import com.spring.basics.api.request.FindCustomerRequest;
import com.spring.basics.api.request.RequestJobRequest;
import com.spring.basics.api.request.UpdateCustomerRequest;
import com.spring.basics.entity.Customer;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/customers")
public interface CustomerApi {

    
    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    Customer saveCustomer(@RequestBody CreateCustomerRequest customer);

    @GetMapping(value = "/{customerId}")
    Customer findCustomerById(@PathVariable Long customerId);

    @GetMapping
    Customer findCustomer(@RequestBody FindCustomerRequest findCustomerRequest);

    @PutMapping(value= "/{customerId}")
    Customer updateCustomer(@PathVariable Long customerId, @RequestBody UpdateCustomerRequest updateCustomerRequest);

    @DeleteMapping(value= "/{customerId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    void deleteCustomer(@PathVariable Long customerId);

    @PostMapping("/requestJob")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    void requestJob(@RequestBody RequestJobRequest requestJobRequest);

}
