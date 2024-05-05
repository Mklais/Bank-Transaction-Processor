package com.playtech.bankteldrassil.csv;

import com.playtech.bankteldrassil.model.User;

import java.math.BigDecimal;
import java.util.Optional;

public class UserCsvParser implements CsvLineParser<User> {
    @Override
    public Optional<User> parseCsvLine(String line, String expectedCsvHeader) {
        try {
            String[] parts = line.split(",");

            User user = new User();
            user.setUserId(parts[0]);
            user.setUsername(parts[1]);
            user.setBalance(new BigDecimal(parts[2]));
            user.setCountry(parts[3]);
            user.setFrozen("1".equals(parts[4]));
            user.setDepositMin(new BigDecimal(parts[5]));
            user.setDepositMax(new BigDecimal(parts[6]));
            user.setWithdrawMin(new BigDecimal(parts[7]));
            user.setWithdrawMax(new BigDecimal(parts[8]));

            return Optional.of(user);
        } catch (RuntimeException e) {
            System.out.println("Failed to parse line: " + line);
            return Optional.empty();
        }
    }
}
