package com.playtech.bankteldrassil.service.event;

import com.playtech.bankteldrassil.enums.BinType;
import com.playtech.bankteldrassil.model.Event;
import com.playtech.bankteldrassil.model.User;

import java.math.BigDecimal;
import java.util.List;

public interface EventService {

    void addSuccessEvent(String transactionId);

    void addFailureEvent(String transactionId, String failureMessage);

    List<Event> findAllEvents();

    void addNotUniqueTransactionMessage(String transactionId);

    void addInvalidAmountMessage(String transactionId, BigDecimal amount);

    void addUserNotValidMessage(String transactionId, String userId);

    void addUserFrozenMessage(String transactionId, String userId);

    String addInvalidBalanceForWithdrawalTypeMessage(BigDecimal amount, BigDecimal balance);

    void addTransactionTypeSpecificMessage(String transactionId, String message);

    String addWithdrawAmountInvalid(int outOfLimit, BigDecimal amount, User user);

    String addDepositAmountInvalid(int outOfLimit, BigDecimal amount, User user);

    void addNoPreviousDeposit(String transactionId, String accountNumber);

    void addAccountInUse(String transactionId, String accountNumber);

    void addInvalidCardTypeMessage(String transactionId, BinType binType);
}
