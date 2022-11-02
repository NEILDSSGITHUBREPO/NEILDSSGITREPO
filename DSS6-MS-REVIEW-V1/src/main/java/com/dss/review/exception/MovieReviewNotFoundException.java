package com.dss.review.exception;

public class MovieReviewNotFoundException extends RuntimeException {

    private static final String MESSAGE = "Movie Review not found";

    public MovieReviewNotFoundException() {
        super(MESSAGE);
    }
}
