package com.dss.review.service;

import com.dss.review.dto.ReviewForm;
import com.dss.review.dto.util.DTOTransformer;
import com.dss.review.dto.util.PageResult;
import com.dss.review.dto.util.validator.ReviewFormValidator;
import com.dss.review.dto.util.validator.ValidationError;
import com.dss.review.entity.Movie;
import com.dss.review.entity.Review;
import com.dss.review.entity.User;
import com.dss.review.exception.FieldValidationException;
import com.dss.review.exception.MovieNotFoundException;
import com.dss.review.repository.MovieRepository;
import com.dss.review.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service class for Movie Review
 */
@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private MovieRepository movieRepository;

    /**
     * Service method for creating a movie review
     *
     * @Param ReviewForm, String, String
     * @Return boolean: true if success
     * @Throws FieldValidationException, MovieNotFoundException
     */
    public boolean createReview(ReviewForm reviewForm, String uid, String mvid) {
        Map<String, ValidationError> fieldMessage = ReviewFormValidator.validateReviewForm(reviewForm);

        if (fieldMessage.size() > 0) {
            throw new FieldValidationException(fieldMessage);
        } else {
            try {
                Optional<Movie> optMovie = movieRepository.findById(UUID.fromString(mvid));
                    saveReview(reviewForm, uid, fieldMessage, optMovie);
            } catch (IllegalArgumentException iae) {
                fieldMessage.put("mvid", ValidationError.FORMAT_MISMATCH);
                throw new FieldValidationException(fieldMessage);
            }
        }

        return true;
    }

    private void saveReview(ReviewForm reviewForm, String uid, Map<String, ValidationError> fieldMessage, Optional<Movie> optMovie) {
        try {
            if(optMovie.isPresent()) {
                Review review = DTOTransformer.transformToReview(reviewForm);
                review.setAddedBy(new User(UUID.fromString(uid)));
                review.setReviewedMovie(optMovie.get());
                review.setDatePosted(LocalDate.now());
                reviewRepository.save(review);
            }else{
                throw new MovieNotFoundException();
            }
        } catch (IllegalArgumentException iae) {
            fieldMessage.put("uid", ValidationError.FORMAT_MISMATCH);
            throw new FieldValidationException(fieldMessage);
        }
    }

    /**
     * Service method for getting a specific review for a movie
     *
     * @Param String(movieId), String(reviewId)
     * @Return ReviewForm
     */
    public ReviewForm viewMovieReview(String mvid, String rid) {
        Map<String, ValidationError> fieldMessage = new HashMap<>();
        ReviewForm reviewForm;

        try {
            Optional<Movie> movie = movieRepository.findById(UUID.fromString(mvid));
            if (movie.isPresent()) {
                reviewForm = getReviewForm(rid, fieldMessage);
            } else {
                throw new MovieNotFoundException();
            }
        } catch (IllegalArgumentException iae) {
            fieldMessage.put("mvid", ValidationError.FORMAT_MISMATCH);
            throw new FieldValidationException(fieldMessage);
        }

        return reviewForm;
    }

    private ReviewForm getReviewForm(String rid, Map<String, ValidationError> fieldMessage) {
        ReviewForm reviewForm;
        try {
            Optional<Review> optReview = reviewRepository.findById(UUID.fromString(rid));
            if (optReview.isPresent()) {
                reviewForm = DTOTransformer.transformToReviewForm(optReview.get());
            } else {
                throw new MovieNotFoundException();
            }
        } catch (IllegalArgumentException iae) {
            fieldMessage.put("rid", ValidationError.FORMAT_MISMATCH);
            throw new FieldValidationException(fieldMessage);
        }
        return reviewForm;
    }

    public PageResult<Set<ReviewForm>> viewMovieReviews(String mvid, int page, int size
            , String sortField, String sortDirection) {
        Page<Review> reviewPage = null;

        Map<String, ValidationError> fieldMessage = new HashMap<>();
        if (sortDirection == null || sortDirection.isEmpty()) sortDirection = "ASC";
        if (sortField == null || sortField.isEmpty()) sortField = "id";

        if (page <= 0) fieldMessage.put("page", ValidationError.UNSSUPORTED_RANGE);
        if (size <= 0) fieldMessage.put("size", ValidationError.UNSSUPORTED_RANGE);

        if (fieldMessage.size() > 0) {
            throw new FieldValidationException(fieldMessage);
        } else {
            try {
                if (movieRepository.existsById(UUID.fromString(mvid))) {
                    reviewPage = reviewRepository.findAllByReviewedMovieId(UUID.fromString(mvid)
                            , PageRequest.of(page - 1, size)
                                    .withSort(Sort.by(Sort.Direction.fromString(sortDirection), sortField)));
                } else {
                    throw new MovieNotFoundException();
                }
            } catch (IllegalArgumentException iae) {
                fieldMessage.put("mvid", ValidationError.FORMAT_MISMATCH);
                throw new FieldValidationException(fieldMessage);
            }
        }

        List<Review> reviews = reviewPage.getContent();
        Set<ReviewForm> reviewForms = reviews.parallelStream()
                .map(DTOTransformer::transformToReviewForm)
                .collect(Collectors.toSet());

        return new PageResult<>(page, reviewPage.getTotalPages(), reviewForms.size(), reviewForms);
    }
}
