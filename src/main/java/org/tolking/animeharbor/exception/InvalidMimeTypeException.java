package org.tolking.animeharbor.exception;

import java.io.IOException;

public class InvalidMimeTypeException extends IOException {
    public InvalidMimeTypeException(String errorMessage) {
        super(errorMessage);
    }
}
