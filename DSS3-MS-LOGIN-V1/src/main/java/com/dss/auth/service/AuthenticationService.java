package com.dss.auth.service;

import com.dss.auth.dto.TokenForm;
import com.dss.auth.dto.UserForm;
import com.dss.auth.dto.util.DTOTransformer;
import com.dss.auth.dto.util.validator.UserFormValidator;
import com.dss.auth.dto.util.validator.ValidationError;
import com.dss.auth.entity.User;
import com.dss.auth.exception.FieldValidationException;
import com.dss.auth.exception.UserNotFoundException;
import com.dss.auth.repository.UserRepository;
import com.dss.token.JsonWebToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

/**
 * Service for handling authentication. Login and Registration
 */
@Service
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JsonWebToken jsonWebToken;

    /**
     * Service method for registering user
     *
     * @Param UserForm userForm
     * @Return boolean ? true if success else
     * @Throws FieldValidationException when fail
     */
    public boolean registerUser(UserForm userForm) throws FieldValidationException {
        Map<String, ValidationError> fieldMessage = UserFormValidator.validateRegistrationForm(userForm
                , userRepository.emailExist(userForm.getEmail())
                , userRepository.phoneNumberExist(userForm.getPhoneNumber()));

        if (fieldMessage.size() > 0) {
            throw new FieldValidationException(fieldMessage);
        } else {
            User user = DTOTransformer.transformToUser(userForm);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
        }

        return true;
    }

    /**
     * Service method for registering user
     *
     * @Param UserForm userForm
     * @Return boolean ? true if success else false
     * @Throws FieldValidationException when fail
     */
    public TokenForm loginUser(UserForm userForm) throws FieldValidationException {
        Optional<User> optUser = userRepository.findByEmailOrPhoneNumber(userForm.getEmail());
        Map<String, ValidationError> fieldMessage = UserFormValidator.validateLoginForm(userForm);
        boolean passwordMatch = false;

        if (fieldMessage.size() > 0) {
            throw new FieldValidationException(fieldMessage);
        } else {
            if (optUser.isPresent()) {
                User user = optUser.get();
                jsonWebToken.jwt().withClaim("role", user.getRole().getId());
                if (passwordEncoder.matches(userForm.getPassword(), user.getPassword())) passwordMatch = true;
            } else {
                throw new UserNotFoundException();
            }
        }

        return new TokenForm(passwordMatch ? jsonWebToken.generate() : "", passwordMatch);
    }
}
