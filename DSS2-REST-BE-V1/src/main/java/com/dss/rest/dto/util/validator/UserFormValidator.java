package com.dss.rest.dto.util.validator;

import com.dss.rest.dto.UserForm;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserFormValidator {

    public static Map<String, ValidationError> validateRegistrationForm(UserForm userForm, boolean emailExist, boolean phoneNumberExist) {
        Map<String, ValidationError> fieldMessage = new HashMap<>();

        if (userForm.getName() != null) {
            Pattern specialCharPattern = Pattern.compile("[^a-zA-Z ]");
            Matcher firstNameMatcher = specialCharPattern.matcher(userForm.getName());
            if (firstNameMatcher.find()) fieldMessage.put("firstName", ValidationError.SPECIAL_CHARACTER);
        } else {
            fieldMessage.put("name", ValidationError.UNDEFINED_FIELD);
        }

        if (userForm.getEmail() != null) {
            if (emailExist) fieldMessage.put("email", ValidationError.DUPLICATE_DATA);
        } else {
            fieldMessage.put("email", ValidationError.UNDEFINED_FIELD);
        }

        if (userForm.getPhoneNumber() != null) {
            if (phoneNumberExist) fieldMessage.put("phoneNumber", ValidationError.DUPLICATE_DATA);
        } else {
            fieldMessage.put("phoneNumber", ValidationError.UNDEFINED_FIELD);
        }

        if (userForm.getPassword() != null) {
            String[] passwordRegexes = {"\\w", "\\d", "\\W"};
            for (String regex : passwordRegexes) {
                Pattern passwordPattern = Pattern.compile(regex);
                if (!passwordPattern.matcher(userForm.getPassword()).find())
                    fieldMessage.put("password", ValidationError.FORMAT_MISMATCH);
            }
        } else {
            fieldMessage.put("password", ValidationError.UNDEFINED_FIELD);
        }

        return fieldMessage;
    }
}
