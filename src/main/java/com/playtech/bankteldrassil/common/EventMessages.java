package com.playtech.bankteldrassil.common;

public class EventMessages {

    private EventMessages() {
    }

    public static final String TRANSACTION_SUCCESSFUL = "OK";

    public static final String TRANSACTION_FAILED_NOT_UNIQUE = "Transaction %s already processed (id non-unique)";

    public static final String TRANSACTION_FAILED_AMOUNT_INVALID = "Transaction amount must be larger than 0; Received %s";

    public static final String TRANSACTION_FAILED_USER_NOT_FOUND = "User %s not found in Users";

    public static final String TRANSACTION_FAILED_USER_IS_FROZEN = "User %s is frozen";

    public static final String TRANSACTION_FAILED_INVALID_TYPE = "%s is invalid transaction type";

    public static final String TRANSACTION_FAILED_MISSING_OR_EMPTY_TRANSACTION_TYPE = "Transaction type is null or empty";

    public static final String TRANSACTION_FAILED_WITHDRAWAL_BALANCE_VIOLATION = "Not enough balance to withdraw %s - balance is too low at %s";

    public static final String TRANSACTION_AMOUNT_WITHDRAW_BELOW_MIN = "Amount %s is under the withdraw limit of %s";

    public static final String TRANSACTION_AMOUNT_WITHDRAW_OVER_MAX = "Amount %s is over the withdraw limit of %s";

    public static final String TRANSACTION_AMOUNT_DEPOSIT_BELOW_MIN = "Amount %s is under the deposit limit of %s";

    public static final String TRANSACTION_AMOUNT_DEPOSIT_OVER_MAX = "Amount %s is over the deposit limit of %s";

    public static final String TRANSACTION_FAILED_MISSING_PAYMENT_METHOD = "Payment method is missing";

    public static final String TRANSACTION_FAILED_UNKNOWN_PAYMENT_METHOD = "Payment method is unknown";

    public static final String TRANSACTION_FAILED_INVALID_CARD_TYPE = "Only DC cards allowed; got %s";

    public static final String TRANSACTION_FAILED_NEW_ACCOUNT = "Cannot withdraw with a new account %s";

    public static final String TRANSACTION_FAILED_ACCOUNT_IN_USE = "Account %s is in use by other user";

    public static final String TRANSACTION_FAILED_IBAN_INVALID = "Invalid iban %s";

    public static final String TRANSACTION_FAILED_INVALID_CARD_COUNTRY = "Invalid country %s; expected %s (%s)";

    public static final String TRANSACTION_FAILED_INVALID_ACCOUNT_COUNTRY = "Invalid account country %s; expected %s";
}
