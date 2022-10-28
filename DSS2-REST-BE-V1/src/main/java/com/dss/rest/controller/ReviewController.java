package com.dss.rest.controller;

import com.dss.rest.dto.ReviewForm;
import com.dss.rest.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Rest Controller for Movie Reviews
 * */
@RestController
@RequestMapping("api/v1/review")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    /**
     * Controller method for adding review to a movie
     * @Param ReviewForm String(userId) String(moviedId)
     * @Return boolean; true if success
     * */
    @PostMapping("/add")
    public boolean addReview(@RequestBody ReviewForm reviewForm
            , @RequestHeader("uid") String id
            , @RequestParam("mvid") String mvid) {
        return reviewService.createReview(reviewForm, id, mvid);
    }
}
