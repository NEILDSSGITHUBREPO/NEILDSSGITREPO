package com.dss.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * DTO to handle user-end REST consumption for Review Entity
 * Target Entity: User
 *
 * */
public class ReviewForm{

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
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
