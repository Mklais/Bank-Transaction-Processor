package com.playtech.bankteldrassil.service.bins;

import com.playtech.bankteldrassil.enums.TransactionMethodType;
import com.playtech.bankteldrassil.enums.TransactionType;
import com.playtech.bankteldrassil.model.AccountInfo;
import com.playtech.bankteldrassil.model.BinMapping;
import com.playtech.bankteldrassil.model.User;
import com.playtech.bankteldrassil.csv.BinCsvParser;
import com.playtech.bankteldrassil.csv.CsvReader;

import java.nio.file.Path;
import java.util.*;

public class BinMappingServiceImpl implements BinMappingService {

    private static final String EXPECTED_CSV_HEADER = "NAME,RANGE_FROM,RANGE_TO,TYPE,COUNTRY";

    private final List<BinMapping> binMappings;

    private final Map<String, User> assignedAccountNumbers = new HashMap<>();

    public BinMappingServiceImpl(Path filePath) {
        CsvReader<BinMapping> binMappingReader = new CsvReader<>(new BinCsvParser());
        this.binMappings = binMappingReader.read(filePath, EXPECTED_CSV_HEADER);
    }

    @Override
    public AccountInfo getAccountInfo(String accountNumber, TransactionMethodType transactionMethodType, TransactionType transactionType) {
        AccountInfo accountInfo = new AccountInfo();

        if (transactionMethodType.equals(TransactionMethodType.CARD)) {
            BinMapping binTypeForCard = getBinTypeForCard(accountNumber);
            if (binTypeForCard != null) {
                accountInfo.setBinType(binTypeForCard.getType());
                accountInfo.setCountry(binTypeForCard.getCountry());
            }
        } else {
            accountInfo.setCountry(accountNumber.substring(0, 2));
        }

        accountInfo.setTransactionMethodType(transactionMethodType);
        accountInfo.setTransactionType(transactionType);
        return accountInfo;
    }

    @Override
    public BinMapping getBinTypeForCard(String accountNumber) {
        long firstTenDigits = Long.parseLong(accountNumber.substring(0, 10));
        for (BinMapping binMapping : binMappings) {
            if (firstTenDigits >= binMapping.getRangeFrom() && firstTenDigits <= binMapping.getRangeTo()) {
                return binMapping;
            }
        }
        return null;
    }

    @Override
    public boolean userCanUseAccount(User user, String accountNumber, TransactionMethodType transactionMethodType, TransactionType transactionType) {
        User assignedUser = assignedAccountNumbers.get(accountNumber);
        if (assignedUser == null) {
            updateUserAccount(user, accountNumber, transactionMethodType, transactionType);
            assignedAccountNumbers.put(accountNumber, user);
            return true;
        }

        if (assignedUser.getUserId().equalsIgnoreCase(user.getUserId())) {
            updateUserAccount(user, accountNumber, transactionMethodType, transactionType);
            return true;
        }
        return false;
    }

    @Override
    public void updateApprovedDeposit(String accountNumber) {
        User user = assignedAccountNumbers.get(accountNumber);
        AccountInfo accountInfo = user.getUserAccounts().get(accountNumber);
        accountInfo.setPreviousDepositSuccessful(true);
        user.getUserAccounts().put(accountNumber, accountInfo);
        assignedAccountNumbers.put(accountNumber, user);
    }

    private void updateUserAccount(User user, String accountNumber, TransactionMethodType transactionMethodType, TransactionType transactionType) {
        Map<String, AccountInfo> userAccounts = user.getUserAccounts();

        userAccounts.computeIfAbsent(accountNumber, n -> getAccountInfo(n, transactionMethodType, transactionType));
    }
}
