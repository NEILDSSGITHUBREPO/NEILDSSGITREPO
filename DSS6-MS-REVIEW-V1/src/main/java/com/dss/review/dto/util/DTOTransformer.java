package com.dss.review.dto.util;

import com.dss.review.dto.ReviewForm;
import com.dss.review.entity.Review;

/*
 * Utility class to transform a DTO to Entity
 * */
public class DTOTransformer {

    /**
     * Transforms ReviewForm to Review
     *
     * @Param ReviewForm
     * @Return Review
     */
    public static Review transformToReview(ReviewForm reviewForm) {
        Review review = new Review();

        review.setDescription(reviewForm.getDescription());
        review.setRating(reviewForm.getRating());

        return review;
    }

    /**
     * Transforms Review to ReviewForm
     *
     * @Param Review
     * @Return ReviewForm
     */
    public static ReviewForm transformToReviewForm(Review review) {
        ReviewForm reviewForm = new ReviewForm();

        reviewForm.setId(review.getId().toString());
        reviewForm.setDescription(review.getDescription());
        reviewForm.setRating(review.getRating());

        return reviewForm;
    }
}
