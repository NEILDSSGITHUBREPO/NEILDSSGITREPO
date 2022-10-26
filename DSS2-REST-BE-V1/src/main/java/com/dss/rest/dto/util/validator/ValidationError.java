package com.dss.rest.dto.util.validator;

/**
 * Validation Error Codes for DTO validation
 */
public enum ValidationError {

    SPECIAL_CHARACTER("VE01", "Special characters not allowed"),
    DUPLICATE_DATA("VE02", "Already in use"),
    FORMAT_MISMATCH("VE03", "Invalid Format"),

    UNDEFINED_FIELD("VE04", "Undefined Value");
    private final String code;
    private final String description;

    ValidationError(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}