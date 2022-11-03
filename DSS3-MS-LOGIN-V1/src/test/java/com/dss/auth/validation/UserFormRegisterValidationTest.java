package com.dss.auth.validation;

import com.dss.auth.dto.UserForm;
import com.dss.auth.dto.util.validator.UserFormValidator;
import com.dss.auth.dto.util.validator.ValidationError;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UserFormRegisterValidationTest {

    @Test
    public void shouldFailPasswordFormatValidation() {
        UserForm userForm = new UserForm("neil@email.com"
                , "Passw0rd"
                , "09123456789"
                , "Neil De Guzman");

        ValidationError fieldError = UserFormValidator
                .validateRegistrationForm(userForm
                , false
                , false).get("password");
        Assertions.assertEquals(fieldError, ValidationError.FORMAT_MISMATCH);
    }

    @Test
    public void shouldFailNameFormatValidation() {
        UserForm userForm = new UserForm("neil@email.com"
                , "P@ssw0rd"
                , "09123456789"
                , "Neil@a123 De Guzman");

        ValidationError fieldError = UserFormValidator
                .validateRegistrationForm(userForm
                , false
                , false)
                .get("name");
        Assertions.assertEquals(fieldError, ValidationError.SPECIAL_CHARACTER);
    }

    @Test
    public void shouldFailPhoneNumberUndefinedField() {
        UserForm userForm = new UserForm("neil@email.com"
                , "P@ssw0rd"
                , null
                , "Neil De Guzman");

        ValidationError fieldError = UserFormValidator.validateRegistrationForm(userForm
                , false
                , false).get("phoneNumber");
        Assertions.assertEquals(fieldError, ValidationError.UNDEFINED_FIELD);
    }

    @Test
    public void shouldFailNameUndefinedField() {
        UserForm userForm = new UserForm(null
                , "P@ssw0rd"
                , "09123456789"
                , null);

        ValidationError fieldError = UserFormValidator.validateRegistrationForm(userForm
                , false
                , false).get("name");
        Assertions.assertEquals(fieldError, ValidationError.UNDEFINED_FIELD);
    }

    @Test
    public void shouldFailEmailUndefinedField() {
        UserForm userForm = new UserForm(null
                , "P@ssw0rd"
                , "09123456789"
                , "Neil De Guzman");

        ValidationError fieldError = UserFormValidator.validateRegistrationForm(userForm
                , false
                , false).get("email");
        Assertions.assertEquals(fieldError, ValidationError.UNDEFINED_FIELD);
    }

    @Test
    public void shouldFailPasswordUndefinedField() {
        UserForm userForm = new UserForm("neil@email.com"
                , null
                , "09123456789"
                , "Neil De Guzman");

        ValidationError fieldError = UserFormValidator.validateRegistrationForm(userForm
                , false
                , false).get("password");
        Assertions.assertEquals(fieldError, ValidationError.UNDEFINED_FIELD);
    }

}
