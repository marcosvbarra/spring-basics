package com.spring.basics.api.request;

import com.spring.basics.entity.PlanEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@NoArgsConstructor
public class UpdateWorkerRequest {

    @Size(min = 1, message = "The field name must not be blank.")
    private String name;
    @Size(min = 10, max = 14, message = "The phone number must be between {min} and {max} digits long.")
    private String phoneNumber;
    @Email(message = "The email ${validatedValue} is invalid.")
    private String email;
    @Size(min = 8, max = 8, message = "The field cep must have 8 digits.")
    private String cep;
    private String description;
    @DecimalMin(value = "0", message = "The rating must be higher than {value}")
    @DecimalMax(value = "5", message = "The rating must be lower than {value}")
    private Double rating;
    private byte[] documentPhoto;
    private Boolean isActive;
    private PlanEnum plan;
}
