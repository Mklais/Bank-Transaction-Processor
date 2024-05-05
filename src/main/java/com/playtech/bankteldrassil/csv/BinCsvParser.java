package com.playtech.bankteldrassil.csv;

import com.playtech.bankteldrassil.enums.BinType;
import com.playtech.bankteldrassil.model.BinMapping;

import java.util.Optional;

public class BinCsvParser implements CsvLineParser<BinMapping> {
    @Override
    public Optional<BinMapping> parseCsvLine(String line, String expectedCsvHeader) {
        try {
            String[] parts = line.split(",");

            BinMapping bin = new BinMapping();
            bin.setName(parts[0]);
            bin.setRangeFrom(Long.parseLong(parts[1]));
            bin.setRangeTo(Long.parseLong(parts[2]));
            bin.setType(BinType.valueOf(parts[3].toUpperCase()));
            bin.setCountry(parts[4]);

            return Optional.of(bin);
        } catch (RuntimeException e) {
            System.out.println("Failed to parse line: " + line + " reason: " + e.getMessage());
            return Optional.empty();
        }
    }
}
