package com.example.singularitymanagement.exception;

public class UserNameOrEmailExistsException extends Exception {
    public UserNameOrEmailExistsException(String message) {
        super(message);
    }
}
