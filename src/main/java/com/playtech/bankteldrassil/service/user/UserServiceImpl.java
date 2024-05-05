package com.playtech.bankteldrassil.service.user;

import com.playtech.bankteldrassil.model.AccountInfo;
import com.playtech.bankteldrassil.model.Transaction;
import com.playtech.bankteldrassil.model.User;
import com.playtech.bankteldrassil.csv.CsvReader;
import com.playtech.bankteldrassil.csv.UserCsvParser;

import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.playtech.bankteldrassil.enums.TransactionType.DEPOSIT;
import static com.playtech.bankteldrassil.enums.TransactionType.WITHDRAW;

public class UserServiceImpl implements UserService {

    private static final String EXPECTED_CSV_HEADER = "USER_ID,USERNAME,BALANCE,COUNTRY,FROZEN,DEPOSIT_MIN,DEPOSIT_MAX,WITHDRAW_MIN,WITHDRAW_MAX";
    private final List<User> users;

    public UserServiceImpl(Path filePath) {
        CsvReader<User> userReader = new CsvReader<>(new UserCsvParser());
        this.users = userReader.read(filePath, EXPECTED_CSV_HEADER);
    }

    @Override
    public Optional<User> findUserById(String userId) {
        return users.stream().filter(user -> user.getUserId().equalsIgnoreCase(userId)).findFirst();
    }

    @Override
    public List<User> findAllUsers() {
        return users;
    }

    public int amountInWithinLimits(BigDecimal amount, BigDecimal minLimit, BigDecimal maxLimit) {
        if (amount.compareTo(minLimit) < 0) {
            return -1;
        } else if (amount.compareTo(maxLimit) > 0) {
            return 1;
        }
        return 0;
    }

    @Override
    public boolean isSufficientBalanceForWithdrawal(Transaction transaction, User user) {
        return user.getBalance().compareTo(transaction.getAmount()) > 0;
    }

    /**
     * Checks if the given account number has been used for a successful deposit before.
     *
     * @param user The user whose accounts are to be checked.
     * @param accountNumber The account number to check.
     * @return true if the account has been used for a successful deposit before; false otherwise
     */
    @Override
    public boolean accountHasUsedBeforeForDeposit(User user, String accountNumber) {
        Map<String, AccountInfo> previouslyApprovedDeposits = user.getUserAccounts().entrySet().stream()
                .filter(entry ->
                        entry.getKey().equalsIgnoreCase(accountNumber) && entry.getValue().isPreviousDepositSuccessful()
                )
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        return !previouslyApprovedDeposits.isEmpty();
    }

    @Override
    public void updateUserBalance(Transaction transaction, User user) {
        if (transaction.getType().equals(DEPOSIT)) {
            user.setBalance(
                    user.getBalance().add(transaction.getAmount())
            );
        } else if (transaction.getType().equals(WITHDRAW)) {
            user.setBalance(
                    user.getBalance().subtract(transaction.getAmount())
            );
        }
    }
}
