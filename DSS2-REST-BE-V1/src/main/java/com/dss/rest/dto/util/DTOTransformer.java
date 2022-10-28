package com.dss.rest.dto.util;

import com.dss.rest.dto.MovieForm;
import com.dss.rest.dto.ReviewForm;
import com.dss.rest.dto.UserForm;
import com.dss.rest.entity.Review;
import com.dss.rest.entity.User;
import com.dss.rest.entity.UserInformation;
import com.dss.rest.entity.types.Category;
import com.dss.rest.entity.Movie;
import com.dss.rest.entity.types.MaturityRating;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

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

    /**
     * Transforms MovieForm to Movie
     *
     * @Param MovieForm
     * @Return Movie
     */
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


    /**
     * Transforms Movie to MovieForm
     *
     * @Param Movie
     * @Return MovieForm
     */
    public static MovieForm transformToMovieForm(Movie movie) {
        MovieForm movieForm = new MovieForm();

        movieForm.setId(movie.getId().toString());
        movieForm.setBudget(movie.getBudget());
        movieForm.setTitle(movie.getTitle());
        movieForm.setCoverPath(movie.getImageLink());
        movieForm.setTrailerPath(movie.getTrailerLink());
        movieForm.setMaturityRating(movie.getMaturityRating().name());
        movieForm.setReleaseDate(movie.getReleaseDate().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));

        Set<String> categories = movie.getCategories().parallelStream().map(Enum::name).collect(Collectors.toSet());
        movieForm.setCategories(categories);

        return movieForm;
    }

    /**
     * Transforms ReviewForm to Review
     *
     * @Param ReviewForm
     * @Return Review
     */
    public static Review transformToReview(ReviewForm reviewForm) {
        Review review = new Review();

        review.setDescription(reviewForm.getDescription());
        review.setRating(reviewForm.getRating());

        return review;
    }

    /**
     * Transforms Review to ReviewForm
     *
     * @Param Review
     * @Return ReviewForm
     */
    public static ReviewForm transformToReviewForm(Review review) {
        ReviewForm reviewForm = new ReviewForm();

        reviewForm.setId(review.getId().toString());
        reviewForm.setDescription(review.getDescription());
        reviewForm.setRating(review.getRating());

        return reviewForm;
    }
}
