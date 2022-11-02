package com.dss.review.exception;

public class MovieNotFoundException extends RuntimeException{

    private static final String MESSAGE = "Movie not found";
    public MovieNotFoundException() {
        super(MESSAGE);
    }
}
