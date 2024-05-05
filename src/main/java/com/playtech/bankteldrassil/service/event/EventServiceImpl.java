package com.playtech.bankteldrassil.service.event;

import com.playtech.bankteldrassil.enums.BinType;
import com.playtech.bankteldrassil.enums.StatusType;
import com.playtech.bankteldrassil.model.Event;
import com.playtech.bankteldrassil.model.User;
import com.playtech.bankteldrassil.common.EventMessages;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class EventServiceImpl implements EventService {

    private final List<Event> events = new ArrayList<>();

    private final DecimalFormat decimalFormat = new DecimalFormat("#0.00");

    @Override
    public void addSuccessEvent(String transactionId) {
        Event event = new Event();
        event.setTransactionId(transactionId);
        event.setStatus(StatusType.APPROVED);
        event.setMessage(EventMessages.TRANSACTION_SUCCESSFUL);
        events.add(event);
    }

    @Override
    public void addFailureEvent(String transactionId, String failureMessage) {
        Event event = new Event();
        event.setTransactionId(transactionId);
        event.setStatus(StatusType.DECLINED);
        event.setMessage(failureMessage);
        events.add(event);
    }

    @Override
    public List<Event> findAllEvents() {
        return events;
    }

    @Override
    public void addNotUniqueTransactionMessage(String transactionId) {
        addFailureEvent(
                transactionId,
                EventMessages.TRANSACTION_FAILED_NOT_UNIQUE.formatted(transactionId)
        );
    }

    @Override
    public void addInvalidAmountMessage(String transactionId, BigDecimal amount) {
        addFailureEvent(
                transactionId,
                EventMessages.TRANSACTION_FAILED_AMOUNT_INVALID.formatted(amount)
        );
    }

    @Override
    public void addUserNotValidMessage(String transactionId, String userId) {
        addFailureEvent(
                transactionId,
                EventMessages.TRANSACTION_FAILED_USER_NOT_FOUND.formatted(userId)
        );
    }

    @Override
    public void addUserFrozenMessage(String transactionId, String userId) {
        addFailureEvent(
                transactionId,
                EventMessages.TRANSACTION_FAILED_USER_IS_FROZEN.formatted(userId)
        );
    }

    @Override
    public String addInvalidBalanceForWithdrawalTypeMessage(BigDecimal amount, BigDecimal balance) {
        return EventMessages.TRANSACTION_FAILED_WITHDRAWAL_BALANCE_VIOLATION.formatted(
                amount, balance
        );
    }

    @Override
    public void addTransactionTypeSpecificMessage(String transactionId, String message) {
        addFailureEvent(
                transactionId,
                message
        );
    }

    @Override
    public String addWithdrawAmountInvalid(int outOfLimit, BigDecimal amount, User user) {
        return outOfLimit > 0
                ? EventMessages.TRANSACTION_AMOUNT_WITHDRAW_OVER_MAX.formatted(decimalFormat.format(amount), decimalFormat.format(user.getWithdrawMax()))
                : EventMessages.TRANSACTION_AMOUNT_WITHDRAW_BELOW_MIN.formatted(decimalFormat.format(amount), decimalFormat.format(user.getWithdrawMin()));
    }

    @Override
    public String addDepositAmountInvalid(int outOfLimit, BigDecimal amount, User user) {
        return outOfLimit > 0
                ? EventMessages.TRANSACTION_AMOUNT_DEPOSIT_OVER_MAX.formatted(decimalFormat.format(amount), decimalFormat.format(user.getDepositMax()))
                : EventMessages.TRANSACTION_AMOUNT_DEPOSIT_BELOW_MIN.formatted(decimalFormat.format(amount), decimalFormat.format(user.getDepositMin())
        );
    }

    @Override
    public void addNoPreviousDeposit(String transactionId, String accountNumber) {
        addFailureEvent(
                transactionId,
                EventMessages.TRANSACTION_FAILED_NEW_ACCOUNT.formatted(accountNumber)
        );
    }

    @Override
    public void addAccountInUse(String transactionId, String accountNumber) {
        addFailureEvent(
                transactionId,
                EventMessages.TRANSACTION_FAILED_ACCOUNT_IN_USE.formatted(accountNumber)
        );
    }

    @Override
    public void addInvalidCardTypeMessage(String transactionId, BinType binType) {
        addFailureEvent(
                transactionId,
                EventMessages.TRANSACTION_FAILED_INVALID_CARD_TYPE.formatted(binType)
        );
    }
}
