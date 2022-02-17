package com.luminor.task.ibanvalidation.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.luminor.task.ibanvalidation.api.IbanState;
import com.luminor.task.ibanvalidation.service.IbanValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins="http://localhost:3000")
@RestController
@RequestMapping("api")
public class IbanValidationController {

    private final IbanValidationService ibanValidationService;
    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    public IbanValidationController(IbanValidationService ibanValidationService) {
        this.ibanValidationService = ibanValidationService;
    }

    @GetMapping("validate")
    public List<IbanState> validateIbans(@RequestParam(value = "ibans") String ibanValues) {
        System.out.println("IBANs got from UI: " + ibanValues);
        List<IbanState> ibanStates = ibanValidationService.getIbanStates(ibanValues);
        try {
            System.out.println("Validation results:\n" + mapper.writeValueAsString(ibanStates));
        } catch (JsonProcessingException e) {
            System.out.println("JSON parsing error...");
            e.printStackTrace();
        }
        return ibanStates;
    }
}
