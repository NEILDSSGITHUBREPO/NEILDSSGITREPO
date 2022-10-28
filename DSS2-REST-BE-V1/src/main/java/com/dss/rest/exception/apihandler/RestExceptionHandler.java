package com.dss.rest.exception.apihandler;

import com.dss.rest.exception.FieldValidationException;
import com.dss.rest.dto.util.validator.ValidationError;
import com.dss.rest.exception.MovieNotFoundException;
import com.dss.rest.exception.UserNotFoundException;
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
     *  Global Exception handler for all field validation exception
     * @Params FieldValidationException, ServletWebRequest
     * @Return ResponseEntity</?>
     *
     * Http Status = BAD_REQUEST
     *
     * */
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

    /**
     *  Global Exception handler for all not found exception
     * @Params FieldValidationException, ServletWebRequest
     * @Return ResponseEntity</?>
     *
     * Http Status = NOT_FOUND
     *
     * */
    @ExceptionHandler(value = {UserNotFoundException.class, MovieNotFoundException.class})
    public ResponseEntity<?> handleNotFoundException(Exception ex, ServletWebRequest request) {
        return handleExceptionInternal(ex
                , new ApiErrorBody<>(request.getRequest().getRequestURI()
                        , HttpStatus.NOT_FOUND.toString()
                        , ex.getMessage())
                , new HttpHeaders()
                , HttpStatus.NOT_FOUND
                , request);
    }
}
