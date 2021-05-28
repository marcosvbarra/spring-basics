package com.spring.basics.api.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class CreateCustomerRequest {

    @NotNull(message = "The field name is mandatory.")
    @NotBlank(message = "The field name must not be blank.")
    private String name;
    @NotNull(message = "The field phoneNumber is mandatory.")
    @Size(min = 10, max = 14, message = "The phone number must be between {min} and {max} digits long.")
    private String phoneNumber;
    @NotNull(message = "The field email is mandatory.")
    @Email(message = "The email ${validatedValue} is invalid.")
    private String email;
    @NotNull(message = "The field cep is mandatory.")
    @Size(min = 8, max = 8, message = "The field cep must have 8 digits.")
    private String cep;
}
