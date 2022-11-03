package com.dss.auth.validation;

import com.dss.auth.dto.UserForm;
import com.dss.auth.dto.util.validator.UserFormValidator;
import com.dss.auth.dto.util.validator.ValidationError;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UserFormLoginValidationTest {

    @Test
    public void shouldPassValidation() {
        UserForm userForm = new UserForm("neil@email.com"
                , "P@ssw0rd"
                , "09123456789"
                , "Neil De Guzman");

        Assertions.assertEquals(UserFormValidator.validateLoginForm(userForm).size(), 0);
    }


    @Test
    public void shouldFailEmailUndefinedField() {
        UserForm userForm = new UserForm(null
                , "P@ssw0rd"
                , "09123456789"
                , "Neil De Guzman");

        ValidationError fieldError = UserFormValidator.validateLoginForm(userForm).get("email");
        Assertions.assertEquals(fieldError, ValidationError.UNDEFINED_FIELD);
    }

    @Test
    public void shouldFailPasswordUndefinedField() {
        UserForm userForm = new UserForm("neil@email.com"
                , null
                , "09123456789"
                , "Neil De Guzman");

        ValidationError fieldError = UserFormValidator.validateLoginForm(userForm).get("password");
        Assertions.assertEquals(fieldError, ValidationError.UNDEFINED_FIELD);
    }


}
