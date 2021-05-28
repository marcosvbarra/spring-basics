package com.spring.basics.api.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class FindCustomerRequest {

    @NotNull(message = "The field email is mandatory.")
    @Email(message = "The email ${validatedValue} is invalid.")
    private String email;
}
