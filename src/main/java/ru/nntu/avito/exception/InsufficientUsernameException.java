package ru.nntu.avito.exception;

public class InsufficientUsernameException extends RuntimeException {
    public InsufficientUsernameException(String message) {
        super(message);
    }
}
