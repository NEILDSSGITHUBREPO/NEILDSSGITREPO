package com.dss.rest.dto.util;

import com.dss.rest.dto.UserForm;
import com.dss.rest.entity.User;
import com.dss.rest.entity.UserInformation;

/*
* Utility class to transform a DTO to Entity
* */
public class DTOTransformer {

    /*
    * Transforms UserForm to User Entity
    *
    * @Param UserForm
    * @Return User
    * */
    public static User transformToUser(UserForm userForm) {
        UserInformation userInformation = new UserInformation(userForm.getPhoneNumber(), userForm.getName());

        User user = new User(userForm.getEmail(), userForm.getPassword());
        user.setUserInformation(userInformation);
        return user;
    }
}
