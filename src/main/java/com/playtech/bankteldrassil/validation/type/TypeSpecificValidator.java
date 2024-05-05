package com.playtech.bankteldrassil.validation.type;

import com.playtech.bankteldrassil.model.Transaction;
import com.playtech.bankteldrassil.model.User;

public interface TypeSpecificValidator {
    int validate(Transaction transaction, User user);
}
