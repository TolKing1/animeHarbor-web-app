package org.tolking.animeharbor.exception;

public abstract class UserException extends Exception {
    public UserException(String errorMessage) {
        super(errorMessage);
    }
}
