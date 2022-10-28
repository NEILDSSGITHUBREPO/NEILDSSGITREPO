package com.dss.rest.service;

import com.dss.rest.dto.MovieForm;
import com.dss.rest.dto.PageResult;
import com.dss.rest.dto.util.DTOTransformer;
import com.dss.rest.dto.util.validator.MovieFormValidator;
import com.dss.rest.dto.util.validator.ValidationError;
import com.dss.rest.entity.User;
import com.dss.rest.entity.Movie;
import com.dss.rest.exception.FieldValidationException;
import com.dss.rest.exception.MovieNotFoundException;
import com.dss.rest.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

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

    public PageResult<Set<MovieForm>> getAllMovies(int page, int size, String sortField, String sortDirection) {
        Page moviePage = null;

        Map<String, ValidationError> fieldMessage = new HashMap<>();
        if (sortDirection == null || sortDirection.isEmpty()) sortDirection = "ASC";
        if (sortField == null || sortField.isEmpty()) sortField = "id";

        if (page <= 0) fieldMessage.put("page", ValidationError.UNSSUPORTED_RANGE);
        if (size <= 0) fieldMessage.put("size", ValidationError.UNSSUPORTED_RANGE);

        if (fieldMessage.size() > 0) {
            throw new FieldValidationException(fieldMessage);
        } else {
            moviePage = movieRepository.findAll(PageRequest.of(page - 1
                    , size
                    , Sort.Direction.fromString(sortDirection.toUpperCase())
                    , sortField));

            if (moviePage.getTotalPages() < page) {
                fieldMessage.put("page", ValidationError.UNSSUPORTED_RANGE);
                throw new FieldValidationException(fieldMessage);
            }
        }

        List<Movie> movies = moviePage.getContent();
        Set<MovieForm> movieForms = movies.parallelStream()
                .map(DTOTransformer::transformToMovieForm)
                .collect(Collectors.toSet());

        return new PageResult<>(page, moviePage.getTotalPages(), movieForms.size(), movieForms);
    }

    public boolean updateMovie(String mvid, MovieForm movieForm) {
        Map<String, ValidationError> fieldMessage = MovieFormValidator.validateMovieFormUpdate(movieForm);
        boolean successUpdate;

        try {
            if (fieldMessage.size() > 0) {
                throw new FieldValidationException(fieldMessage);
            } else {
                Optional<Movie> optMovie = movieRepository.findById(UUID.fromString(mvid));
                if(optMovie.isPresent()){
                    Movie movie = optMovie.get();
                    movie.setBudget(Optional.ofNullable(movieForm.getBudget()).orElse(movie.getBudget()));
                    movie.setImageLink(Optional.ofNullable(movieForm.getCoverPath()).orElse(movie.getImageLink()));
                    movieRepository.save(movie);
                    successUpdate = true;
                }else{
                    throw new MovieNotFoundException();
                }
            }
        } catch (IllegalArgumentException iae) {
            fieldMessage.put("mvid", ValidationError.FORMAT_MISMATCH);
            throw new FieldValidationException(fieldMessage);
        }

        return successUpdate;
    }

}
