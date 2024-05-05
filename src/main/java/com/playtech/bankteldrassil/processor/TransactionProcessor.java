package com.playtech.bankteldrassil.processor;

import com.playtech.bankteldrassil.enums.BinType;
import com.playtech.bankteldrassil.enums.TransactionMethodType;
import com.playtech.bankteldrassil.enums.TransactionType;
import com.playtech.bankteldrassil.model.AccountInfo;
import com.playtech.bankteldrassil.model.Transaction;
import com.playtech.bankteldrassil.model.User;
import com.playtech.bankteldrassil.service.bins.BinMappingService;
import com.playtech.bankteldrassil.service.event.EventService;
import com.playtech.bankteldrassil.service.transaction.TransactionService;
import com.playtech.bankteldrassil.validation.identification.IdentificationProcessor;
import com.playtech.bankteldrassil.validation.method.MethodValidationDispatcher;
import com.playtech.bankteldrassil.validation.method.MethodValidator;
import com.playtech.bankteldrassil.validation.type.TypeValidationDispatcher;
import com.playtech.bankteldrassil.validation.type.TypeValidator;
import com.playtech.bankteldrassil.validation.AmountValidator;
import com.playtech.bankteldrassil.service.user.UserService;

import java.util.List;
import java.util.Optional;

public class TransactionProcessor {

    private final UserService userService;

    private final TransactionService transactionService;

    private final EventService eventService;

    private final BinMappingService binMappingService;

    private final TypeValidationDispatcher typeValidationDispatcher;

    private final MethodValidationDispatcher methodValidationDispatcher;

    public TransactionProcessor(UserService userService, TransactionService transactionService, EventService eventService, BinMappingService binMappingService) {
        this.userService = userService;
        this.transactionService = transactionService;
        this.eventService = eventService;
        this.binMappingService = binMappingService;
        this.typeValidationDispatcher = new TypeValidationDispatcher(userService);
        this.methodValidationDispatcher = new MethodValidationDispatcher();
    }

    public void processTransactions() {
        List<Transaction> transactions = transactionService.getTransactions();
        for (Transaction transaction : transactions) {
            processTransaction(transaction);
        }
    }

    private void processTransaction(Transaction transaction) {
        if (checkTransactionProcessed(transaction)) return;

        Optional<User> optionalUser = userService.findUserById(transaction.getUserId());
        if (optionalUser.isEmpty()) {
            eventService.addUserNotValidMessage(transaction.getTransactionId(), transaction.getUserId());
            transaction.setFailed();
            return;
        }

        User user = optionalUser.get();
        TransactionMethodType transactionMethod = TransactionMethodType.valueOf(transaction.getMethod());
        AccountInfo accountInfo = binMappingService.getAccountInfo(transaction.getAccountNumber(), transactionMethod, transaction.getType());

        if (user.isFrozen()) {
            eventService.addUserFrozenMessage(transaction.getTransactionId(), transaction.getUserId());
            transaction.setFailed();
            return;
        }

        Optional<String> methodValidationError = MethodValidator.validateMethod(transaction);
        if (methodValidationError.isPresent()) {
            eventService.addFailureEvent(transaction.getTransactionId(), methodValidationError.get());
            transaction.setFailed();
            return;
        }

        Optional<String> typeSpecificError = typeValidationDispatcher.validateTypeSpecificTransaction(transaction, user);
        if (typeSpecificError.isPresent()) {
            eventService.addTransactionTypeSpecificMessage(transaction.getTransactionId(), typeSpecificError.get());
            transaction.setFailed();
            return;
        }

        Optional<String> paymentPropertiesError = methodValidationDispatcher.validateMethodSpecificTransaction(transaction, user, accountInfo);
        if (paymentPropertiesError.isPresent()) {
            eventService.addFailureEvent(transaction.getTransactionId(), paymentPropertiesError.get());
            transaction.setFailed();
            return;
        }

        if (!binMappingService.userCanUseAccount(user, transaction.getAccountNumber(), transactionMethod, transaction.getType())) {
            eventService.addAccountInUse(transaction.getTransactionId(), transaction.getAccountNumber());
            transaction.setFailed();
            return;
        }

        if (transaction.getType().equals(TransactionType.WITHDRAW) && !userService.accountHasUsedBeforeForDeposit(user, transaction.getAccountNumber())) {
            eventService.addNoPreviousDeposit(transaction.getTransactionId(), transaction.getAccountNumber());
            transaction.setFailed();
            return;
        }

        if (transactionMethod == TransactionMethodType.CARD && !accountInfo.getBinType().equals(BinType.DC)) {
            eventService.addInvalidCardTypeMessage(transaction.getTransactionId(), accountInfo.getBinType());
            transaction.setFailed();
            return;
        }

        if (!AmountValidator.isAmountValid(transaction.getAmount())) {
            eventService.addInvalidAmountMessage(transaction.getTransactionId(), transaction.getAmount());
            transaction.setFailed();
            return;
        }

        Optional<String> transactionTypeValidationError = TypeValidator.validateType(transaction);
        if (transactionTypeValidationError.isPresent()) {
            eventService.addFailureEvent(transaction.getTransactionId(), transactionTypeValidationError.get());
            transaction.setFailed();
            return;
        }

        finalizeTransaction(transaction, user);
    }

    private boolean checkTransactionProcessed(Transaction transaction) {
        if (IdentificationProcessor.isTransactionProcessed(transaction.getTransactionId())) {
            eventService.addNotUniqueTransactionMessage(transaction.getTransactionId());
            transaction.setFailed();
            return true;
        }

        IdentificationProcessor.markTransactionAsProcessed(transaction.getTransactionId());
        return false;
    }

    private void finalizeTransaction(Transaction transaction, User user) {
        if (transaction.isSuccessful()) {
            if (transaction.getType().equals(TransactionType.DEPOSIT)) {
                binMappingService.updateApprovedDeposit(transaction.getAccountNumber());
            }
            eventService.addSuccessEvent(transaction.getTransactionId());
            userService.updateUserBalance(transaction, user);
        }
    }
}
