package com.dss.rest.exception;

import com.dss.rest.dto.util.validator.ValidationError;

import java.util.Map;

public class FieldValidationException extends RuntimeException{

    private static final String MESSAGE = "Field Validation Exception";
    private Map<String, ValidationError> fieldMessage;
    public FieldValidationException(Map<String, ValidationError> fieldMessage){
        super(MESSAGE);
        this.fieldMessage = fieldMessage;
    }


    public Map<String, ValidationError> getFieldMessage() {
        return fieldMessage;
    }

    public void setFieldMessage(Map<String, ValidationError> fieldMessage) {
        this.fieldMessage = fieldMessage;
    }
}
