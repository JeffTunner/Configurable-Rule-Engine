package com.example.ruleEngine.service;

import com.example.ruleEngine.dto.PayloadRequestDto;
import com.example.ruleEngine.dto.PayloadResponseDto;
import com.example.ruleEngine.entity.Rule;
import com.example.ruleEngine.entity.RuleAction;
import com.example.ruleEngine.entity.RuleCondition;
import com.example.ruleEngine.repository.RuleActionRepository;
import com.example.ruleEngine.repository.RuleConditionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class RuleEvaluator {

    @Autowired
    RuleConditionRepository conditionRepository;

    @Autowired
    RuleActionRepository actionRepository;

    public boolean evaluateCondition(RuleCondition condition, Map<String, Object> payload) {
        Object actualValue = payload.get(condition.getFieldName());
        if(actualValue == null) return false;

        String operator = condition.getOperator();
        String expectedValue = condition.getValue();

        switch (condition.getDatatype().toUpperCase()) {

            case "NUMBER" -> {
                double actual = Double.parseDouble(actualValue.toString());
                double expected = Double.parseDouble(expectedValue);

                return switch (operator) {

                    case ">" -> actual > expected;
                    case "<" -> actual < expected;
                    case ">=" -> actual >= expected;
                    case "<=" -> actual <= expected;
                    case "=" -> actual == expected;
                    default -> false;
                };
            }

            case "STRING" -> {
                return switch (operator) {

                    case "=" -> actualValue.toString().equals(expectedValue);
                    case "!=" -> !actualValue.toString().equals(expectedValue);
                    default -> false;
                };
            }

            default -> {
                return false;
            }
        }
    }

    public boolean evaluateRule(Rule rule, Map<String, Object> payload) {
        List<RuleCondition> conditions = conditionRepository.findByRuleId(rule.getId());

        for (RuleCondition condition: conditions) {
            if (!evaluateCondition(condition, payload)) {
                return false;
            }
        }
        return true;
    }

    public void applyAction(Rule rule, Map<String, Object> result) {
        List<RuleAction> actions = actionRepository.findByRuleId(rule.getId());

        for (RuleAction action: actions) {
            result.put(action.getOutputField(), action.getOutputValue());
        }
    }

}
