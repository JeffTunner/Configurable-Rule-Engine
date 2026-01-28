package com.example.ruleEngine.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ConditionRequestDto {

    @NotBlank(message = "Field Name shouldnt be blank")
    private String fieldName;

    @NotBlank(message = "Operator shouldnt be blank")
    private String operator;

    @NotBlank(message = "Value shouldnt be blank")
    private String value;

    @NotBlank(message = "Datatype shouldnt be blank")
    private String datatype;
}
