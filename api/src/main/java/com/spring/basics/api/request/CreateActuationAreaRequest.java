package com.spring.basics.api.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class CreateActuationAreaRequest {

    @NotNull(message = "The field name is mandatory.")
    @NotBlank(message = "The field name must not be blank.")
    private String name;
    @NotNull(message = "The field description is mandatory.")
    @NotBlank(message = "The field description must not be blank.")
    private String description;
}
