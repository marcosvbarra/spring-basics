package com.spring.basics.api.request;

import com.spring.basics.entity.PriorityEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class RequestJobRequest {

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
    @NotBlank(message = "The field jobInformation must not be blank.")
    private String jobInformation;
    @NotNull(message = "The field actuationArea must not be null.")
    private Long actuationArea;
    private PriorityEnum priority;
    private LocalDate startDate;
}
