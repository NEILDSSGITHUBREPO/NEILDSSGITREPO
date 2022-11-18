package com.dss.auth.dto.util;

import com.dss.auth.dto.UserForm;
import com.dss.auth.entity.Role;
import com.dss.auth.entity.User;
import com.dss.auth.entity.UserInformation;

/*
 * Utility class to transform a DTO to Entity
 * */
public class DTOTransformer {

    private DTOTransformer(){}
    /*
     * Transforms UserForm to User Entity
     *
     * @Param UserForm
     * @Return User
     * */
    public static User transformToUser(UserForm userForm) {
        UserInformation userInformation = new UserInformation(userForm.getPhoneNumber(), userForm.getName());

        User user = new User(userForm.getEmail(), userForm.getPassword());
        user.setRole(new Role(userForm.getRole()));
        user.setUserInformation(userInformation);
        return user;
    }
}
