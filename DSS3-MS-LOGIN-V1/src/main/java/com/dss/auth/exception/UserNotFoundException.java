package com.dss.auth.exception;

/**
 * Runtime exception for user not found exception
 * */
public class UserNotFoundException extends RuntimeException{
    private static final String MESSAGE = "User not found";
    public UserNotFoundException() {
        super(MESSAGE);
    }
}
