package com.spring.basics.api.request;

import com.spring.basics.entity.PlanEnum;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class CreateWorkerRequest {

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
    @NotNull(message = "The field cpf is mandatory.")
    @CPF(message = "This cpf is invalid.")
    private String cpf;
    @NotNull(message = "The field actuationArea is mandatory.")
    private Long actuationArea;
    @NotNull(message = "The field plan is mandatory.")
    private PlanEnum plan;
    private String description;
    private byte[] documentPhoto;
}
