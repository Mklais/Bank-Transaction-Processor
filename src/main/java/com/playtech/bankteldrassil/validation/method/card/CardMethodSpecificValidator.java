package com.playtech.bankteldrassil.validation.method.card;

import com.playtech.bankteldrassil.model.*;
import com.playtech.bankteldrassil.validation.method.MethodSpecificValidator;
import com.playtech.bankteldrassil.validation.CountryCodeValidator;
import com.playtech.bankteldrassil.common.EventMessages;

import java.util.Locale;
import java.util.Optional;

public class CardMethodSpecificValidator implements MethodSpecificValidator {

    private final CountryCodeValidator countryCodeValidator = new CountryCodeValidator();

    @Override
    public Optional<String> validate(Transaction transaction, User user, AccountInfo accountInfo) {
        String convertedAccountCountryCode = countryCodeValidator.convertIso3ToIso2(accountInfo.getCountry());

        if (!convertedAccountCountryCode.equalsIgnoreCase(user.getCountry())) {
            return Optional.of(
                    EventMessages.TRANSACTION_FAILED_INVALID_CARD_COUNTRY.formatted(accountInfo.getCountry(), user.getCountry(), Locale.of("", user.getCountry()).getISO3Country())
            );
        }

        return Optional.empty();
    }

}
