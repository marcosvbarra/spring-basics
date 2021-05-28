package com.spring.basics.api.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class UpdateCustomerRequest {

    @Size(min = 1, message = "The field name must not be blank.")
    private String name;
    @Size(min = 10, max = 14, message = "The phone number must be between {min} and {max} digits long.")
    private String phoneNumber;
    @Email(message = "The email ${validatedValue} is invalid.")
    private String email;
    @Size(min = 8, max = 8, message = "The field cep must have 8 digits.")
    private String cep;
}
