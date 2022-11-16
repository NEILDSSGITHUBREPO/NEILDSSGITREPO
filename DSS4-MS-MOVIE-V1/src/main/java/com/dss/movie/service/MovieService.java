package com.dss.movie.service;

import com.dss.movie.dto.MovieForm;
import com.dss.movie.dto.util.DTOTransformer;
import com.dss.movie.dto.util.PageResult;
import com.dss.movie.dto.util.validator.MovieFormValidator;
import com.dss.movie.dto.util.validator.ValidationError;
import com.dss.movie.entity.Movie;
import com.dss.movie.entity.User;
import com.dss.movie.exception.FieldValidationException;
import com.dss.movie.exception.MovieNotFoundException;
import com.dss.movie.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * Service class for movies
 */
@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    /**
     * Service method for creating a movie
     *
     * @Param MovieForm, String
     * @Returns String(movieId)
     */
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

    /**
     * Service method for getting a specific movie
     *
     * @Param String
     * @Returns MovieForm
     */
    public MovieForm getMovieById(String mvid) {
        AtomicReference<MovieForm> movieForm = new AtomicReference<>(new MovieForm());

        try {
            Optional<Movie> optMovie = movieRepository.findById(UUID.fromString(mvid));
            if (optMovie.isPresent()) {
                Movie movie = optMovie.get();
                movieForm.set(DTOTransformer.transformToMovieForm(movie));
            } else {
                throw new MovieNotFoundException();
            }

        } catch (IllegalArgumentException iae) {
            Map<String, ValidationError> fieldMessage = new HashMap<>();
            fieldMessage.put("mvid", ValidationError.FORMAT_MISMATCH);
            throw new FieldValidationException(fieldMessage);
        } catch (NullPointerException npe) {
            throw new MovieNotFoundException();
        }

        return movieForm.get();
    }

    /**
     * Service method for getting all movie in pages
     *
     * @Param int, int, String, String
     * @Returns PageResult<Set < MovieForm>>
     */
    public PageResult<Set<MovieForm>> getAllMovies(int page, int size, String sortField, String sortDirection) {
        Page<Movie> moviePage = null;

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

    /**
     * Service method for updating movie
     *
     * @Param String, MovieForm
     * @Returns boolean; true if success
     */
    public boolean updateMovie(String mvid, MovieForm movieForm) {
        Map<String, ValidationError> fieldMessage = MovieFormValidator.validateMovieFormUpdate(movieForm);
        boolean successUpdate;

        try {
            if (fieldMessage.size() > 0) {
                throw new FieldValidationException(fieldMessage);
            } else {
                Optional<Movie> optMovie = movieRepository.findById(UUID.fromString(mvid));
                if (optMovie.isPresent()) {
                    Movie movie = optMovie.get();
                    movie.setBudget(Optional.ofNullable(movieForm.getBudget()).orElse(movie.getBudget()));
                    movie.setImageLink(Optional.ofNullable(movieForm.getCoverPath()).orElse(movie.getImageLink()));
                    movieRepository.save(movie);
                    successUpdate = true;
                } else {
                    throw new MovieNotFoundException();
                }
            }
        } catch (IllegalArgumentException iae) {
            fieldMessage.put("mvid", ValidationError.FORMAT_MISMATCH);
            throw new FieldValidationException(fieldMessage);
        }

        return successUpdate;
    }

    /**
     * Service method for deleting movie
     *
     * @Param String
     * @Returns boolean; true if success
     */
    public boolean deleteMovie(String mvid) {
        Map<String, ValidationError> fieldMessage = new HashMap<>();
        boolean successDelete = false;

        try {
            Optional<Movie> optMovie = movieRepository.findById(UUID.fromString(mvid));
            if (optMovie.isPresent()) {
                Movie movie = optMovie.get();
                if ((Duration.between(LocalDateTime.of(movie.getReleaseDate(), LocalTime.MIN)
                        , LocalDateTime.now()).toDays() / 365) >= 1) {
                    successDelete = true;
                    movieRepository.delete(movie);
                } else {
                    fieldMessage.put("releaseDate", ValidationError.UNSSUPORTED_RANGE);
                }
            } else {
                throw new MovieNotFoundException();
            }
        } catch (IllegalArgumentException iae) {
            fieldMessage.put("mvid", ValidationError.FORMAT_MISMATCH);
            throw new FieldValidationException(fieldMessage);
        }

        return successDelete;
    }
}
