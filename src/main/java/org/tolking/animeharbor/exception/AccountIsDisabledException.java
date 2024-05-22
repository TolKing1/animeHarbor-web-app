package org.tolking.animeharbor.exception;

public class AccountIsDisabledException extends UserException {
    public AccountIsDisabledException(String errorMessage) {
        super(errorMessage);
    }
}
