package com.example.ruleEngine.controller;

import com.example.ruleEngine.dto.*;
import com.example.ruleEngine.entity.Rule;
import com.example.ruleEngine.service.RuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RuleController {

    @Autowired
    RuleService ruleService;

    @PostMapping("/rules")
    public RuleResponseDto rule(@RequestBody RuleRequestDto requestDto) {
        return ruleService.createRules(requestDto);
    }

    @PostMapping("/rules/{id}/conditions")
    public ConditionResponseDto condition(@PathVariable Long id, @RequestBody ConditionRequestDto requestDto) {
        return ruleService.addCondition(id, requestDto);
    }

    @PostMapping("/rules/{id}/actions")
    public ActionResponseDto action(@PathVariable Long id, @RequestBody ActionRequestDto requestDto) {
        return ruleService.addAction(id, requestDto);
    }

    @PostMapping("/rules/evaluate")
    public PayloadResponseDto evaluate(@RequestBody PayloadRequestDto requestDto) {
        return ruleService.evaluate(requestDto);
    }

}
