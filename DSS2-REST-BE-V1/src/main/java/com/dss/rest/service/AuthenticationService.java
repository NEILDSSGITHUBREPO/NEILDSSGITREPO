package com.dss.rest.service;

import com.dss.rest.dto.UserForm;
import com.dss.rest.dto.util.DTOTransformer;
import com.dss.rest.dto.util.validator.UserFormValidator;
import com.dss.rest.exception.FieldValidationException;
import com.dss.rest.entity.User;
import com.dss.rest.exception.UserNotFoundException;
import com.dss.rest.repository.UserRepository;
import com.dss.rest.dto.util.validator.ValidationError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class AuthenticationService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public boolean registerUser(UserForm userForm) throws FieldValidationException {
        Map<String, ValidationError> fieldMessage = UserFormValidator.validateRegistrationForm(userForm, userRepository.emailExist(userForm.getEmail()), userRepository.phoneNumberExist(userForm.getPhoneNumber()));

        if (fieldMessage.size() > 0) {
            throw new FieldValidationException(fieldMessage);
        } else {
            User user = DTOTransformer.transformToUser(userForm);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
        }

        return true;
    }

    public boolean loginUser(UserForm userForm) throws FieldValidationException {
        Optional<User> optUser = userRepository.findByEmailOrPhoneNumber(userForm.getEmail());
        Map<String, ValidationError> fieldMessage = UserFormValidator.validateLoginForm(userForm);
        boolean passwordMatch = false;

        if (fieldMessage.size() > 0) {
            throw new FieldValidationException(fieldMessage);
        } else {
            if (optUser.isPresent()) {
                User user = optUser.get();
                if (passwordEncoder.matches(userForm.getPassword(), user.getPassword())) passwordMatch = true;
            } else {
                throw new UserNotFoundException();
            }
        }

        return passwordMatch;
    }
}
