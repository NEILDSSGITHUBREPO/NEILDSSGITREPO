package com.dss.review.dto.util.validator;

import com.dss.review.dto.ReviewForm;

import java.util.HashMap;
import java.util.Map;


/**
 * Review form validator utility class
 */
public class ReviewFormValidator {

    private ReviewFormValidator(){}
    /**
     * Validates the ReviewForm for creating new Review
     *
     * @Params MovieForm
     * @Return Map<String, ValidationError>
     * */
    public static Map<String, ValidationError> validateReviewForm(ReviewForm reviewForm) {
        Map<String, ValidationError> fieldMessage = new HashMap<>();

        if(reviewForm.getDescription() == null){
            fieldMessage.put("description", ValidationError.UNDEFINED_FIELD);
        }

        if(reviewForm.getRating() <= 0 || reviewForm.getRating() >= 5){
            fieldMessage.put("rating", ValidationError.UNSSUPORTED_RANGE);
        }

        return fieldMessage;
    }
}
