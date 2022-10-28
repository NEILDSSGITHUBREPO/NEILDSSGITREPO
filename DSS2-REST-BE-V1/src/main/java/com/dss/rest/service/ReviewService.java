package com.dss.rest.service;

import com.dss.rest.dto.ReviewForm;
import com.dss.rest.dto.util.DTOTransformer;
import com.dss.rest.dto.util.validator.ReviewFormValidator;
import com.dss.rest.dto.util.validator.ValidationError;
import com.dss.rest.entity.Movie;
import com.dss.rest.entity.Review;
import com.dss.rest.entity.User;
import com.dss.rest.exception.FieldValidationException;
import com.dss.rest.exception.MovieNotFoundException;
import com.dss.rest.repository.MovieRepository;
import com.dss.rest.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * Service class for Movie Review
 * */
@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private MovieRepository movieRepository;

    /**
     * Service method for creating a movie review
     * @Param ReviewForm, String, String
     * @Return boolean: true if success
     * @Throws FieldValidationException, MovieNotFoundException
     * */
    public boolean createReview(ReviewForm reviewForm, String uid, String mvid) {
        Map<String, ValidationError> fieldMessage = ReviewFormValidator.validateReviewForm(reviewForm);

        if (fieldMessage.size() > 0) {
            throw new FieldValidationException(fieldMessage);
        } else {
            Optional<Movie> optMovie = movieRepository.findById(UUID.fromString(mvid));
            if (optMovie.isPresent()) {
                Review review = DTOTransformer.transformToReview(reviewForm);
                review.setAddedBy(new User(UUID.fromString(uid)));
                review.setReviewedMovie(optMovie.get());
                review.setDatePosted(LocalDate.now());
                reviewRepository.save(review);
            } else {
                throw new MovieNotFoundException();
            }
        }

        return true;
    }

}
