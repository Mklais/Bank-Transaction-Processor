package com.playtech.bankteldrassil.service.user;

import com.playtech.bankteldrassil.model.Transaction;
import com.playtech.bankteldrassil.model.User;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<User> findUserById(String userId);

    List<User> findAllUsers();

    int amountInWithinLimits(BigDecimal amount, BigDecimal minLimit, BigDecimal maxLimit);

    boolean isSufficientBalanceForWithdrawal(Transaction transaction, User user);

    boolean accountHasUsedBeforeForDeposit(User user, String accountNumber);

    void updateUserBalance(Transaction transaction, User user);
}
