package com.playtech.bankteldrassil.csv;

import com.playtech.bankteldrassil.common.exceptions.CsvReaderException;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class CsvReader<T> {

    private final CsvLineParser<T> lineParser;

    public CsvReader(CsvLineParser<T> lineParser) {
        this.lineParser = lineParser;
    }

    public List<T> read(final Path filePath, String expectedCsvHeader) {
        List<T> results = new ArrayList<>();

        try (BufferedReader fileReader = Files.newBufferedReader(filePath)) {
            var lines = fileReader.lines();
            lines.filter(line -> !line.isEmpty() && !line.equalsIgnoreCase(expectedCsvHeader))
                    .forEach(line -> lineParser.parseCsvLine(line, expectedCsvHeader).ifPresent(results::add));
        } catch (Exception e) {
            throw new CsvReaderException(filePath.getFileName().toString(), e.toString());
        }
        return results;
    }
}
