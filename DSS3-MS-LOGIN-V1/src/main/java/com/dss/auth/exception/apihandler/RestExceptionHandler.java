package com.dss.auth.exception.apihandler;

import com.dss.auth.dto.util.validator.ValidationError;
import com.dss.auth.exception.FieldValidationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Global Exception handler for all field validation exception
     *
     * @Params FieldValidationException, ServletWebRequest
     * @Return ResponseEntity</ ?>
     * <p>
     * Http Status = BAD_REQUEST
     */
    @ExceptionHandler(value = {FieldValidationException.class})
    public ResponseEntity<?> handleFieldValidationException(FieldValidationException fex, ServletWebRequest request) {

        return handleExceptionInternal(fex
                , new ApiErrorBody<Map<String, ValidationError>>(request.getRequest().getRequestURI()
                        , HttpStatus.BAD_REQUEST.toString()
                        , fex.getFieldMessage())
                , new HttpHeaders()
                , HttpStatus.BAD_REQUEST
                , request);
    }

}
