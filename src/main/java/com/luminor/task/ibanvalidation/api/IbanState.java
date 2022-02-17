package com.luminor.task.ibanvalidation.api;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.*;

import static java.lang.Math.min;
import static java.util.Map.entry;


public class IbanState {
   private String iban;
   private State state;

   public IbanState(String iban, State state) {
      this.iban = iban;
      this.state = state;
   }

   public String getIban() {
      return iban;
   }

   public State getState() {
      return state;
   }
}
