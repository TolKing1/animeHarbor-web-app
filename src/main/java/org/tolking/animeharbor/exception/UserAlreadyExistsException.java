package org.tolking.animeharbor.exception;

public class UserAlreadyExistsException extends UserException {
    public UserAlreadyExistsException(String errorMessage) {
        super(errorMessage);
    }
}
