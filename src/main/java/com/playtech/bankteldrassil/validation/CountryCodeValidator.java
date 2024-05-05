package com.playtech.bankteldrassil.validation;

import com.playtech.bankteldrassil.model.AccountInfo;
import com.playtech.bankteldrassil.model.User;

import java.util.*;

/**
 * Class for handling operations related to country codes,
 * including conversions between ISO 3166-1 alpha-2 and alpha-3 country codes.
 */
public class CountryCodeValidator {

    private Map<String, Locale> iso3ToIso2LocaleMap;

    public CountryCodeValidator() {
        initCountryCodeMapping();
    }

    private void initCountryCodeMapping() {
        String[] isoCountries = Locale.getISOCountries();
        iso3ToIso2LocaleMap = HashMap.newHashMap(isoCountries.length);
        for (String country : isoCountries) {
            Locale locale = Locale.of("", country);
            iso3ToIso2LocaleMap.put(locale.getISO3Country().toUpperCase(), locale);
        }
    }

    public String convertIso3ToIso2(String iso3CountryCode) {
        Locale locale = iso3ToIso2LocaleMap.get(iso3CountryCode.toUpperCase());
        return locale != null ? locale.getCountry() : null;
    }

    public static boolean transferCountryValid(User user, AccountInfo accountInfo) {
        return user.getCountry().equalsIgnoreCase(accountInfo.getCountry());
    }
}
