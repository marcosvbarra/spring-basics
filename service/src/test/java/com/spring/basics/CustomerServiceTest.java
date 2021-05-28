package com.spring.basics;

import com.spring.basics.api.request.CreateCustomerRequest;
import com.spring.basics.api.request.FindCustomerRequest;
import com.spring.basics.api.request.UpdateCustomerRequest;
import com.spring.basics.entity.Customer;
import com.spring.basics.exception.CustomerAlreadyRegisteredException;
import com.spring.basics.exception.CustomerNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CustomerServiceTest {

    @InjectMocks
    private CustomerService customerService;

    @Mock
    private CustomerRepository customerRepository;

    @Test
    public void shouldCreateACustomer() {

        CreateCustomerRequest createCustomerRequest = new CreateCustomerRequest();
        createCustomerRequest.setEmail("mar@mar.com");

        when(customerRepository.findByEmail(createCustomerRequest.getEmail())).thenReturn(Optional.empty());

        customerService.createCustomer(createCustomerRequest);

        verify(customerRepository).saveAndFlush(any(Customer.class));
    }

    @Test(expected = CustomerAlreadyRegisteredException.class)
    public void whenCreatingAnExistentCustomerShouldThrowAnException() {

        CreateCustomerRequest createCustomerRequest = new CreateCustomerRequest();
        createCustomerRequest.setEmail("mar@mar.com");

        when(customerRepository.findByEmail(createCustomerRequest.getEmail())).thenReturn(Optional.of(new Customer()));

        customerService.createCustomer(createCustomerRequest);

        verify(customerRepository, times(0)).saveAndFlush(any(Customer.class));
    }

    @Test
    public void shouldReturnACustomerById() {

        Customer customer = new Customer();
        customer.setId(1L);

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        customerService.findCustomerById(1L);

        assertEquals(1L, customer.getId().longValue());
    }

    @Test(expected = CustomerNotFoundException.class)
    public void whenFindingANonExistentCustomerByIdShouldThrowException() {

        when(customerRepository.findById(1L)).thenReturn(Optional.empty());

        customerService.findCustomerById(1L);
    }

    @Test
    public void shouldReturnACustomerByEmail() {

        FindCustomerRequest findCustomerRequest = new FindCustomerRequest();
        findCustomerRequest.setEmail("mar@mar.com");
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setEmail("mar@mar.com");

        when(customerRepository.findByEmail(findCustomerRequest.getEmail())).thenReturn(Optional.of(customer));

        customerService.findCustomer(findCustomerRequest);

        assertEquals(1L, customer.getId().longValue());
        assertEquals(findCustomerRequest.getEmail(), customer.getEmail());
    }

    @Test(expected = CustomerNotFoundException.class)
    public void whenFindingANonExistentCustomerByEmailShouldThrowException() {

        FindCustomerRequest findCustomerRequest = new FindCustomerRequest();
        findCustomerRequest.setEmail("mar@mar.com");

        when(customerRepository.findByEmail(findCustomerRequest.getEmail())).thenReturn(Optional.empty());

        customerService.findCustomer(findCustomerRequest);

    }

    @Test
    public void shouldUpdateACustomer() {

        UpdateCustomerRequest updateCustomerRequest = new UpdateCustomerRequest();
        updateCustomerRequest.setName("Joao");
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setEmail("mar@mar.com");

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        customerService.updateCustomer(1L, updateCustomerRequest);

        verify(customerRepository).saveAndFlush(any(Customer.class));
    }

    @Test(expected = CustomerNotFoundException.class)
    public void whenUpdatingANonExistentCustomerShouldThrowAnException() {

        UpdateCustomerRequest updateCustomerRequest = new UpdateCustomerRequest();
        updateCustomerRequest.setName("Joao");

        when(customerRepository.findById(1L)).thenReturn(Optional.empty());

        customerService.updateCustomer(1L, updateCustomerRequest);

        verify(customerRepository, times(0)).saveAndFlush(any(Customer.class));
    }

    @Test
    public void shouldDeleteACustomer() {

        Customer customer = new Customer();
        customer.setId(1L);
        customer.setEmail("mar@mar.com");

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        customerService.deleteCustomer(1L);

        verify(customerRepository).delete(customer);
    }

    @Test(expected = CustomerNotFoundException.class)
    public void whenTryingToDeleteACustomerThatDoesNotExistsShouldThrowAnException() {

        when(customerRepository.findById(1L)).thenReturn(Optional.empty());

        customerService.deleteCustomer(1L);

        verify(customerRepository, times(0)).delete(any());
    }

}