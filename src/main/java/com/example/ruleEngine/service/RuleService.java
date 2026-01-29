package com.example.ruleEngine.service;

import com.example.ruleEngine.dto.*;
import com.example.ruleEngine.entity.Rule;
import com.example.ruleEngine.entity.RuleAction;
import com.example.ruleEngine.entity.RuleCondition;
import com.example.ruleEngine.repository.RuleActionRepository;
import com.example.ruleEngine.repository.RuleConditionRepository;
import com.example.ruleEngine.repository.RuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RuleService {

    @Autowired
    RuleRepository ruleRepository;

    @Autowired
    RuleConditionRepository conditionRepository;

    @Autowired
    RuleActionRepository actionRepository;

    @Autowired
    RuleEvaluator evaluator;

    //DTO -> Entity
    private Rule toEntity(RuleRequestDto requestDto) {
        Rule rule = new Rule();
        rule.setName(requestDto.getName());
        return rule;
    }

    private RuleCondition toConditionEntity(ConditionRequestDto conditionRequestDto) {
        RuleCondition ruleCondition = new RuleCondition();
        ruleCondition.setFieldName(conditionRequestDto.getFieldName());
        ruleCondition.setOperator(conditionRequestDto.getOperator());
        ruleCondition.setValue(conditionRequestDto.getValue());
        ruleCondition.setDatatype(conditionRequestDto.getDatatype());
        return ruleCondition;
    }

    private RuleAction toActionEntity(ActionRequestDto actionRequestDto) {
        RuleAction action = new RuleAction();
        action.setOutputField(actionRequestDto.getOutputField());
        action.setOutputValue(actionRequestDto.getOutputValue());
        return action;
    }

    //Entity -> DTO
    private RuleResponseDto toDto(Rule rule) {
        return new RuleResponseDto(rule.getId(), rule.getName(), rule.isActive(), rule.getPriority(), rule.getCreatedAt());
    }

    private ConditionResponseDto toConditionDto(RuleCondition condition) {
        return new ConditionResponseDto(condition.getId(), condition.getFieldName(), condition.getOperator(), condition.getValue(), condition.getDatatype());
    }

    private ActionResponseDto toActionDto(RuleAction action) {
        return new ActionResponseDto(action.getId(), action.getOutputField(), action.getOutputValue());
    }

    public RuleResponseDto createRules(RuleRequestDto requestDto) {
        Rule rule = toEntity(requestDto);
        rule.setCreatedAt(LocalDateTime.now());
        Rule saved = ruleRepository.save(rule);
        return toDto(saved);
    }

    public ConditionResponseDto addCondition(Long id, ConditionRequestDto conditionRequestDto) {
        Rule rule = ruleRepository.findById(id).orElseThrow(() -> new RuntimeException("Rule not found"));
        RuleCondition condition = toConditionEntity(conditionRequestDto);
        condition.setRule(rule);
        RuleCondition saved = conditionRepository.save(condition);
        return toConditionDto(saved);
    }

    public ActionResponseDto addAction(Long id, ActionRequestDto actionRequestDto) {
        Rule rule = ruleRepository.findById(id).orElseThrow(() -> new RuntimeException("Rule not found"));
        RuleAction action = toActionEntity(actionRequestDto);
        action.setRule(rule);
        RuleAction saved = actionRepository.save(action);
        return toActionDto(saved);
    }

    public PayloadResponseDto evaluate(PayloadRequestDto requestDto) {
        Map<String, Object> payload = requestDto.getData();
        Map<String, Object> result = new HashMap<>();

        List<Rule> rules = ruleRepository.findByActiveTrueOrderByPriorityDesc();

        for (Rule rule: rules) {
            boolean matches = evaluator.evaluateRule(rule, payload);

            if(matches) {
                evaluator.applyAction(rule, result);
            }
        }
        return new PayloadResponseDto(result);
    }

}
