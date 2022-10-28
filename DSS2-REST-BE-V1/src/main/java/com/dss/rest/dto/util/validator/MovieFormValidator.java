package com.dss.rest.dto.util.validator;

import com.dss.rest.dto.MovieForm;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

/**
 * Movie form validator utility class
 */
public class MovieFormValidator {

    public static Map<String, ValidationError> validateMovieForm(MovieForm movieForm) {
        Map<String, ValidationError> fieldMessage = new HashMap<>();

        if (movieForm.getTitle().isEmpty() || movieForm.getTitle() == null) {
            fieldMessage.put("title", ValidationError.UNDEFINED_FIELD);
        }

        if (movieForm.getCoverPath().isEmpty() || movieForm.getCoverPath() == null) {
            fieldMessage.put("coverPath", ValidationError.UNDEFINED_FIELD);
        }

        if (movieForm.getBudget() == null) {
            fieldMessage.put("budget", ValidationError.UNDEFINED_FIELD);
        }

        if (movieForm.getReleaseDate() == null) {
            fieldMessage.put("releaseDate", ValidationError.UNDEFINED_FIELD);
        } else {
            try {
                LocalDate.parse(movieForm.getReleaseDate()
                        , DateTimeFormatter.ofPattern("MM/dd/yyyy"));
            } catch (DateTimeParseException dtpe) {
                fieldMessage.put("releaseDate", ValidationError.FORMAT_MISMATCH);
            }
        }

        return fieldMessage;
    }

    public static Map<String, ValidationError> validateMovieFormUpdate(MovieForm movieForm) {
        Map<String, ValidationError> fieldMessage = new HashMap<>();

        if (movieForm.getReleaseDate() != null) {
            fieldMessage.put("releaseDate", ValidationError.UNSSUPORTED_FIELD);
        }

        if (movieForm.getTitle() != null) {
            fieldMessage.put("title", ValidationError.UNSSUPORTED_FIELD);
        }

        if (movieForm.getCategories() != null) {
            fieldMessage.put("categories", ValidationError.UNSSUPORTED_FIELD);
        }

        if (movieForm.getMaturityRating() != null) {
            fieldMessage.put("maturityRating", ValidationError.UNSSUPORTED_FIELD);
        }

        if (movieForm.getTrailerPath() != null) {
            fieldMessage.put("trailerPath", ValidationError.UNSSUPORTED_FIELD);
        }

        return fieldMessage;
    }
}
