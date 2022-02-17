package com.luminor.task.ibanvalidation.service;

import com.luminor.task.ibanvalidation.api.IbanState;
import com.luminor.task.ibanvalidation.api.State;
import org.springframework.stereotype.Service;

import java.util.*;

import static java.lang.Math.min;
import static java.util.Map.entry;

@Service
public class IbanValidationService {

    // Map of letters with their digit value
    // Used in IBAN validation process at the step of
    // converting IBAN with letters to fully-digit number
    public final Map<String, Integer> CHAR_TO_DIGIT =  Map.ofEntries(
            entry("A", 10), entry("B", 11), entry("C", 12), entry("D", 13), entry("E", 14),
            entry("F", 15), entry("G", 16), entry("H", 17), entry("I", 18), entry("J", 19),
            entry("K", 20), entry("L", 21), entry("M", 22), entry("N", 23), entry("O", 24),
            entry("P", 25), entry("Q", 26), entry("R", 27), entry("S", 28), entry("T", 29),
            entry("U", 30), entry("V", 31), entry("W", 32), entry("X", 33), entry("Y", 34),
            entry("Z", 35)
    );

    // Map of country abbreviations with their valid IBAN length
    public final Map<String, Integer> CODE_LENGTHS = Map.ofEntries(
            entry("AD", 24), entry("AE", 23), entry("AT", 20), entry("AZ", 28), entry("BA", 20),
            entry("BE", 16), entry("BG", 22), entry("BH", 22), entry("BR", 29), entry("CH", 21),
            entry("CR", 22), entry("CY", 28), entry("CZ", 24), entry("DE", 22), entry("DK", 18),
            entry("DO", 28), entry("EE", 20), entry("ES", 24), entry("FI", 18), entry("FO", 18),
            entry("FR", 27), entry("GB", 22), entry("GI", 23), entry("GL", 18), entry("GR", 27),
            entry("GT", 28), entry("HR", 21), entry("HU", 28), entry("IE", 22), entry("IL", 23),
            entry("IS", 26), entry("IT", 27), entry("JO", 30), entry("KW", 30), entry("KZ", 20),
            entry("LB", 28), entry("LI", 21), entry("LT", 20), entry("LU", 20), entry("LV", 21),
            entry("MC", 27), entry("MD", 24), entry("ME", 22), entry("MK", 19), entry("MR", 27),
            entry("MT", 31), entry("MU", 30), entry("NL", 18), entry("NO", 15), entry("PK", 24),
            entry("PL", 28), entry("PS", 29), entry("PT", 25), entry("QA", 29), entry("RO", 24),
            entry("RS", 22), entry("SA", 24), entry("SE", 24), entry("SI", 19), entry("SK", 24),
            entry("SM", 27), entry("TN", 24), entry("TR", 26), entry("AL", 28), entry("BY", 28),
            entry("EG", 29), entry("GE", 22), entry("IQ", 23), entry("LC", 32), entry("SC", 31),
            entry("ST", 25), entry("SV", 28), entry("TL", 23), entry("UA", 29), entry("VA", 22),
            entry("VG", 24), entry("XK", 20)
    );

    private int calculateMod97(String integerIban) {
        String dividend = integerIban.substring(0, 9);
        String balance = integerIban.substring(9);
        int modulo = 0;
        while(dividend.length() > 0) {
            modulo = Integer.parseInt(dividend) % 97;
            int balanceLength = balance.length();
            dividend = modulo + balance.substring(0, min(7, balanceLength));

            if(balanceLength > 7) {
                balance = balance.substring(7, balanceLength);
            }
            else if(balanceLength == 0){
                return modulo;
            }
            else {
                balance = "";
            }
        }
        return modulo;
    }

    // Validate one IBAN according to validating algorithm given in
    // https://en.wikipedia.org/wiki/International_Bank_Account_Number#Algorithms
    private State validateIban(String number){

        int validLength;
        // Check if content is not more than 34 char long
        if (number.length() > 34) {
            return State.INVALID;
        } else if(number.length() < 15){
            // TODO: add check minimum length
            return State.INVALID;
        }

        // Check string length according to the country
        if (number.substring(0, 2).matches("[A-Z]{2}")) {
            String countryCode = number.substring(0, 2);
            validLength = CODE_LENGTHS.get(countryCode);
            if (number.length() != validLength) {
                return State.INVALID;
            }
        } else {
            return State.INVALID;
        }

        // Move the four initial characters to the end of the string
        String rearrangedContent = number.substring(4, validLength) + number.substring(0, 4);

        // Replace each letter in the string with two digits
        String integerIban = "";
        for (int i = 0; i < validLength; i++) {
            if (Character.isLetter(rearrangedContent.charAt(i))) {
                char character = rearrangedContent.charAt(i);
                integerIban += String.valueOf(CHAR_TO_DIGIT.get(String.valueOf(character)));
            } else {
                integerIban += rearrangedContent.charAt(i);
            }
        }

        // Calculate mod-97
        if (calculateMod97(integerIban) == 1) {
            return State.VALID;
        } else {
            return State.INVALID;
        }
    }

    // Takes raw String input with unchecked IBANs
    // Returns list of IbanState objects consisting of IBAN and its validation state
    private List<IbanState> processIban(String ibanValues) {
        List<String> listOfIbans;
        List<IbanState> ibanWithState = new ArrayList<>();

        // Separate IBAN from ibanValue with coma and add them to the list
        // If only one IBAN is given list will contain one value
        listOfIbans = Arrays.asList(ibanValues.split(","));


        for (int i = 0; i < listOfIbans.size(); i++) {
            // Delete white spaces if any
            if (listOfIbans.get(i).contains(" ")) {
                listOfIbans.set(i, listOfIbans.get(i).replace(" ", ""));
            }
            // Create new IbanState for each value in listOfIbans
            // consisting of IBAN and its state (VALID / INVALID)
            ibanWithState.add(new IbanState(listOfIbans.get(i), validateIban(listOfIbans.get(i))));
        }
        return ibanWithState;
    }

    public List<IbanState> getIbanStates(String ibanValues){
        return processIban(ibanValues);
    }
}
