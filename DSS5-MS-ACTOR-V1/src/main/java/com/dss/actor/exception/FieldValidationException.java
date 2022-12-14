package com.dss.actor.exception;

import com.dss.actor.dto.util.validator.ValidationError;

import java.util.Map;

/**
 * Runtime exception for field validation exception
 *
 * Map<String, ValidationError> fieldsMessage contains the fields and types of error
 *
 * */
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
