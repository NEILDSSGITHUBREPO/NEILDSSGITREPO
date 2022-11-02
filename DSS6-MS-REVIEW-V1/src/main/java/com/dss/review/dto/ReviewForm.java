package com.dss.review.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * DTO to handle user-end REST consumption for Review Entity
 * Target Entity: Review
 *
 * */
public class ReviewForm{

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String id;

    private String description;

    private short rating;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public short getRating() {
        return rating;
    }

    public void setRating(short rating) {
        this.rating = rating;
    }
}
