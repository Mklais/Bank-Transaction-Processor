package com.playtech.bankteldrassil.service.bins;

import com.playtech.bankteldrassil.enums.TransactionMethodType;
import com.playtech.bankteldrassil.enums.TransactionType;
import com.playtech.bankteldrassil.model.AccountInfo;
import com.playtech.bankteldrassil.model.BinMapping;
import com.playtech.bankteldrassil.model.User;

public interface BinMappingService {

    AccountInfo getAccountInfo(String accountNumber, TransactionMethodType transactionMethodType, TransactionType transactionType);

    BinMapping getBinTypeForCard(String accountNumber);

    boolean userCanUseAccount(User user, String accountNumber, TransactionMethodType transactionMethodType, TransactionType transactionType);

    void updateApprovedDeposit(String accountNumber);
}
