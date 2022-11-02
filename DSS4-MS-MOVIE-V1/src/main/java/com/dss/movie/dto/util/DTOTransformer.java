package com.dss.movie.dto.util;

import com.dss.movie.dto.ActorForm;
import com.dss.movie.dto.MovieForm;
import com.dss.movie.entity.Actor;
import com.dss.movie.entity.Movie;
import com.dss.movie.entity.types.Category;
import com.dss.movie.entity.types.MaturityRating;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/*
 * Utility class to transform a DTO to Entity
 * */
public class DTOTransformer {

    /**
     * Transforms MovieForm to Movie
     *
     * @Param MovieForm
     * @Return Movie
     */
    public static Movie transformToMovie(MovieForm movieForm) {
        Movie movie;

        movie = new Movie(movieForm.getTitle(), movieForm.getBudget(), LocalDate.parse(movieForm.getReleaseDate(), DateTimeFormatter.ofPattern("MM/dd/yyyy")));

        Set<Category> categories = new HashSet<>();

        movieForm.getCategories().forEach(mfc -> categories.add(Category.valueOf(mfc.toUpperCase())));
        movie.setImageLink(movieForm.getCoverPath());
        movie.setTrailerLink(movieForm.getTrailerPath());
        movie.setCategories(categories);
        movie.setActors(movieForm.getActors()
                .parallelStream()
                .map(actorForm -> transformToActor(actorForm))
                .collect(Collectors.toSet()));
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

        movieForm.setActors(movie.getActors()
                .parallelStream()
                .map(actor -> transformToActorForm(actor))
                .collect(Collectors.toSet()));

        return movieForm;
    }

    /**
     * Transforms ActorForm to Actor
     *
     * @Param ActorReview
     * @Return Actor
     */
    public static Actor transformToActor(ActorForm actorForm) {
        Actor actor = new Actor();

        if(actorForm.getId() != null) {
            actor.setId(UUID.fromString(actorForm.getId()));
        }

        return actor;
    }

    /**
     * Transforms Actor to ActorForm
     */
    public static ActorForm transformToActorForm(Actor actor) {
        ActorForm actorForm = new ActorForm();

        actorForm.setId(actor.getId().toString());
        actorForm.setFirstName(actor.getFirstName());
        actorForm.setLastName(actor.getLastName());
        actorForm.setGender(actor.getGender());
        actorForm.setAge(actor.getAge());

        return actorForm;
    }
}
