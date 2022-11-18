package com.dss.auth.exception;

public class RoleNotFoundException extends RuntimeException {

    private static final String MESSAGE = "ROLE not found";

    public RoleNotFoundException() {
        super(MESSAGE);
    }
}
