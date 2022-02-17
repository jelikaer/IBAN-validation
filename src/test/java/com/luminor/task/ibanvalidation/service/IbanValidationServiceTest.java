package com.luminor.task.ibanvalidation.service;

import com.luminor.task.ibanvalidation.api.IbanState;
import com.luminor.task.ibanvalidation.api.State;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class IbanValidationServiceTest {

    private final IbanValidationService ibanValidationService = new IbanValidationService();
    private List<IbanState> outputForTest;


    @Test
    void testPutOneValidCode() {
        outputForTest = new ArrayList<>();
        outputForTest.add(new IbanState("EE382200221020145685", State.VALID));
        List<IbanState> ibanStates = ibanValidationService.getIbanStates("EE38 2200 2210 2014 5685");
        Assertions.assertEquals(outputForTest.size(), ibanStates.size());
        Assertions.assertEquals(outputForTest.get(0).getIban(), ibanStates.get(0).getIban());
        Assertions.assertEquals(outputForTest.get(0).getState(), ibanStates.get(0).getState());
    }

    @Test
    void testPutTwoValidCodes(){
        outputForTest = new ArrayList<>();
        outputForTest.add(new IbanState("EE382200221020145685", State.VALID));
        outputForTest.add(new IbanState("GB98MIDL07009312345678", State.VALID));
        List<IbanState> ibanStates = ibanValidationService.getIbanStates("EE38 2200 2210 2014 5685, " +
                "GB98 MIDL 0700 9312 3456 78");
        Assertions.assertEquals(outputForTest.size(), ibanStates.size());
        for(int i = 0; i < outputForTest.size(); i++){
            Assertions.assertEquals(outputForTest.get(i).getIban(), ibanStates.get(i).getIban());
            Assertions.assertEquals(outputForTest.get(i).getState(), ibanStates.get(i).getState());
        }
    }

    @Test
    void testPutTwoInvalidCodes(){
        outputForTest = new ArrayList<>();
        outputForTest.add(new IbanState("EE3822002210920145685", State.INVALID));
        outputForTest.add(new IbanState("GB98MIDL000912345678", State.INVALID));
        List<IbanState> ibanStates = ibanValidationService.getIbanStates("EE38 2200 2210 92014 5685, " +
                "GB98 MIDL 000 912 3456 78");
        Assertions.assertEquals(outputForTest.size(), ibanStates.size());
        for(int i = 0; i < outputForTest.size(); i++){
            Assertions.assertEquals(outputForTest.get(i).getIban(), ibanStates.get(i).getIban());
            Assertions.assertEquals(outputForTest.get(i).getState(), ibanStates.get(i).getState());
        }
    }

    @Test
    void testCodeWithWrongLength(){
        outputForTest = new ArrayList<>();
        outputForTest.add(new IbanState("sde36489", State.INVALID));
        outputForTest.add(new IbanState("a", State.INVALID));
        List<IbanState> ibanStates = ibanValidationService.getIbanStates("sde36489, " +
                "a");
        Assertions.assertEquals(outputForTest.size(), ibanStates.size());
        for(int i = 0; i < outputForTest.size(); i++){
            Assertions.assertEquals(outputForTest.get(i).getIban(), ibanStates.get(i).getIban());
            Assertions.assertEquals(outputForTest.get(i).getState(), ibanStates.get(i).getState());
        }
    }

    @Test
    void testCodeWithLowerCase(){
        outputForTest = new ArrayList<>();
        outputForTest.add(new IbanState("ee3822002210920145685", State.INVALID));
        List<IbanState> ibanStates = ibanValidationService.getIbanStates("ee38 2200 2210 9201 45685");
        Assertions.assertEquals(outputForTest.size(), ibanStates.size());
        for(int i = 0; i < outputForTest.size(); i++){
            Assertions.assertEquals(outputForTest.get(i).getIban(), ibanStates.get(i).getIban());
            Assertions.assertEquals(outputForTest.get(i).getState(), ibanStates.get(i).getState());
        }
    }
}