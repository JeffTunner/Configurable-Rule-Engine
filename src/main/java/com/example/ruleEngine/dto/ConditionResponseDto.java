package com.example.ruleEngine.dto;

import com.example.ruleEngine.entity.Rule;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ConditionResponseDto {

    private Long id;
    private String fieldName;
    private String operator;
    private String value;
    private String datatype;
}
