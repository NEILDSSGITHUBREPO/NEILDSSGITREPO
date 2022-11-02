package com.dss.review.controller;

import com.dss.review.dto.ReviewForm;
import com.dss.review.dto.util.PageResult;
import com.dss.review.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * Rest Controller for Movie Reviews
 */
@RestController
@RequestMapping("api/v1/review")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    /**
     * Controller method for adding review to a movie
     *
     * @Param ReviewForm String(userId) String(moviedId)
     * @Return boolean; true if success
     */
    @PostMapping("/add")
    public boolean addReview(@RequestBody ReviewForm reviewForm
            , @RequestHeader("uid") String id
            , @RequestParam("mvid") String mvid) {
        return reviewService.createReview(reviewForm, id, mvid);
    }

    /**
     * Controller method for viewing all movie reviews
     *
     * @Param String(movieId), String(reviewId), int, int, String, String
     * @Return PageResult<Set<ReviewForm>>
     * <p>
     */
    @GetMapping(value = "view/{mvid}")
    public PageResult<Set<ReviewForm>> viewAllMovieReview(@PathVariable(value = "mvid") String mvid
            , @RequestParam(value = "pg") int page
            , @RequestParam(value = "sz") int size
            , @RequestParam(value = "sf", required = false) String sortField
            , @RequestParam(value = "sd", required = false) String sortDirection) {

            return reviewService.viewMovieReviews(mvid
                    , page
                    , size
                    , sortField
                    , sortDirection);
    }

    /**
     * Controller method for viewing movie review
     *
     * @Param String(movieId), String(reviewId)
     * @Return ReviewForm
     * <p>
     */
    @GetMapping(value = "/view/{mvid}/{rid}")
    public ReviewForm viewMovieReview(@PathVariable(value = "mvid") String mvid
            , @PathVariable(value = "rid") String rid) {
        return reviewService.viewMovieReview(mvid, rid);
    }
}
