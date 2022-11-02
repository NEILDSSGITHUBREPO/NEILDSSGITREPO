package com.dss.actor.entity.types;

public enum MaturityRating {

    G("G", "General Audiences"),
    PG("PG", "Parental Guidance"),
    PG_13("PG_13", "Parents Strongly Cautioned"),
    R("R", "Restricted"),
    NC_17("NC_17", "Clearly Adult");

    private String description;
    private String code;

    MaturityRating(String code, String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
