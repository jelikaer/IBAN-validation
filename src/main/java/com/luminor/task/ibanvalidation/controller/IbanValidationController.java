package com.luminor.task.ibanvalidation.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.luminor.task.ibanvalidation.api.IbanState;
import com.luminor.task.ibanvalidation.api.Store;
import com.luminor.task.ibanvalidation.service.IbanValidationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins="http://localhost:3000")
@RestController
@RequestMapping(path="api")
public class IbanValidationController {

    private final IbanValidationService ibanValidationService;
    private final ObjectMapper mapper = new ObjectMapper();
    private final Store store;

    @Autowired
    public IbanValidationController(IbanValidationService ibanValidationService, Store store) {
        this.ibanValidationService = ibanValidationService;
        this.store = store;
    }

    @GetMapping("name")
    public String getName(){
        return store.getName();
    }

    @Operation(summary = "Get a book by its id")
    @GetMapping("validate")
    //@RequestMapping(value = "validate", method = RequestMethod.GET)
    public List<IbanState> validateIbans(
            @Parameter(description = "id of book to be searched", example="EE382200221020145685")
            @RequestParam(value = "ibans") String ibanValues) {
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
