package com.dss.rest.service;

import com.dss.rest.dto.MovieForm;
import com.dss.rest.dto.util.DTOTransformer;
import com.dss.rest.dto.util.validator.MovieFormValidator;
import com.dss.rest.dto.util.validator.ValidationError;
import com.dss.rest.entity.User;
import com.dss.rest.entity.Movie;
import com.dss.rest.exception.FieldValidationException;
import com.dss.rest.exception.MovieNotFoundException;
import com.dss.rest.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    public String createMovie(MovieForm movieForm, String id) {
        Map<String, ValidationError> fieldMessage = MovieFormValidator.validateMovieForm(movieForm);
        String movieId;

        if (fieldMessage.size() > 0) {
            throw new FieldValidationException(fieldMessage);
        } else {
            Movie movie = DTOTransformer.transformToMovie(movieForm);
            movie.setAddedBy(new User(UUID.fromString(id)));

            Movie savedMovie = movieRepository.save(movie);
            movieId = savedMovie.getId().toString();
        }

        return movieId;
    }

    public MovieForm getMovieById(String mvid) {
        AtomicReference<MovieForm> movieForm = new AtomicReference<>(new MovieForm());

        try {
            Optional<Movie> optMovie = movieRepository.findById(UUID.fromString(mvid));
            optMovie.ifPresent(movie -> movieForm.set(DTOTransformer.transformToMovieForm(movie)));
        } catch (IllegalArgumentException iae) {
            Map<String, ValidationError> fieldMessage = new HashMap<>();
            fieldMessage.put("mvid", ValidationError.FORMAT_MISMATCH);
            throw new FieldValidationException(fieldMessage);
        } catch (NullPointerException npe) {
            throw new MovieNotFoundException();
        }

        return movieForm.get();
    }
}
