package com.dss.rest.service;

import com.dss.rest.dto.MovieForm;
import com.dss.rest.dto.util.DTOTransformer;
import com.dss.rest.entity.User;
import com.dss.rest.entity.Movie;
import com.dss.rest.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class MovieService {

    @Autowired
    MovieRepository movieRepository;

    public String createMovie(MovieForm movieForm, String id) {
        Movie movie = DTOTransformer.transformToMovie(movieForm);
        movie.setImageLink("unset");
        movie.setTrailerLink("unset");
        movie.setAddedBy(new User(UUID.fromString(id)));

        Movie savedMovie = movieRepository.save(movie);
        return savedMovie.getId().toString();
    }
}
