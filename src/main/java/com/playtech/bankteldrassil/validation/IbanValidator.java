package com.playtech.bankteldrassil.validation;

import java.math.BigInteger;

public class IbanValidator {

    private static final int IBAN_MAX_LENGTH = 34;

    private static final int IBAN_MIN_LENGTH = 15;

    private static final String IBAN_MODULUS = "97";

    private IbanValidator() {
    }

    /**
     * Validation rules are done according to
     * <a href="https://en.wikipedia.org/wiki/International_Bank_Account_Number">IBAN</a>
     */
    public static boolean validate(String iban) {
        // Depends on country, but right now we will just check Min (Norway) and Max (according to Wiki up to) length between
        iban = iban.replace(" ", "").trim();
        if (iban.length() < IBAN_MIN_LENGTH || iban.length() > IBAN_MAX_LENGTH) {
            return false;
        }

        String rearrangedIban = iban.substring(4) + iban.substring(0, 4);
        StringBuilder recalculatedIban = new StringBuilder();
        for (char character : rearrangedIban.toCharArray()) {
            int charNumeric = Character.getNumericValue(character);
            // 0 - 9 + A - Z
            if (charNumeric < 0 || 35 < charNumeric) {
                return false;
            }

            recalculatedIban.append(charNumeric);
        }

        BigInteger ibanNumber = new BigInteger(recalculatedIban.toString());
        return ibanNumber.mod(new BigInteger(IBAN_MODULUS)).equals(BigInteger.ONE);
    }
}
