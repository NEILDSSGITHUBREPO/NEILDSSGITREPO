package com.dss.rest.dto.util;

import com.dss.rest.dto.MovieForm;
import com.dss.rest.dto.UserForm;
import com.dss.rest.entity.User;
import com.dss.rest.entity.UserInformation;
import com.dss.rest.entity.types.Category;
import com.dss.rest.entity.Movie;
import com.dss.rest.entity.types.MaturityRating;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

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

    public static Movie transformToMovie(MovieForm movieForm) {
        Movie movie;

        movie = new Movie(movieForm.getTitle()
                , movieForm.getBudget()
                , LocalDate.parse(movieForm.getReleaseDate()
                , DateTimeFormatter.ofPattern("MM/dd/yyyy")));

        Set<Category> categories = new HashSet<>();

        movieForm.getCategories().forEach(mfc -> categories.add(Category.valueOf(mfc.toUpperCase())));
        movie.setImageLink(movieForm.getCoverPath());
        movie.setTrailerLink(movieForm.getTrailerPath());
        movie.setCategories(categories);
        movie.setMaturityRating(MaturityRating.valueOf(movieForm.getMaturityRating()));

        return movie;
    }
}
