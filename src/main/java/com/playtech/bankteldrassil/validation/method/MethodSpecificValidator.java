package com.playtech.bankteldrassil.validation.method;

import com.playtech.bankteldrassil.model.*;

import java.util.Optional;

public interface MethodSpecificValidator {
    Optional<String> validate(Transaction transaction, User user, AccountInfo accountInfo);
}
