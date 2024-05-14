package org.tolking.animeharbor.exception;

public class UserAlreadyExists extends Exception{
    public UserAlreadyExists(String errorMessage) {
        super(errorMessage);
    }
}
