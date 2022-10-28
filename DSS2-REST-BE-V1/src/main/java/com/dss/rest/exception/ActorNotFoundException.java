package com.dss.rest.exception;

public class ActorNotFoundException extends RuntimeException {

    private static final String MESSAGE = "Actor not found";

    public ActorNotFoundException() {
        super(MESSAGE);
    }
}
