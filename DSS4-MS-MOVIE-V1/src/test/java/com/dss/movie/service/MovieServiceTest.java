package com.dss.movie.service;

import com.dss.movie.dto.ActorForm;
import com.dss.movie.dto.MovieForm;
import com.dss.movie.dto.util.PageResult;
import com.dss.movie.entity.Movie;
import com.dss.movie.entity.types.MaturityRating;
import com.dss.movie.exception.FieldValidationException;
import com.dss.movie.exception.MovieNotFoundException;
import com.dss.movie.repository.MovieRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@RunWith(SpringRunner.class)
public class MovieServiceTest {

    @MockBean
    MovieRepository movieRepository;

    @Autowired
    MovieService movieService;

    @Test
    public void shouldSuccessFullyCreateMovie() {
        String uid = "3d1ba0ae-b055-4b31-80c9-31d7dc86a717";

        ActorForm actorForm = new ActorForm();
        actorForm.setId("963cdc74-a651-4eb8-aad2-4964161ab97d");
        Set<ActorForm> actors = new HashSet<>();
        actors.add(actorForm);

        Movie movie = new Movie();
        movie.setId(UUID.fromString("6cae80b7-3275-4b1e-8c67-6e2e60ba225a"));

        MovieForm movieForm = new MovieForm();
        movieForm.setTitle("movie");
        movieForm.setBudget(123.23);
        movieForm.setCoverPath("C:\\Users\\Collabera\\OneDrive\\Documents\\fileupload\\02_11_2022_12_36_34_95.png");
        movieForm.setReleaseDate("10/13/2022");
        movieForm.setMaturityRating("R");
        movieForm.setActors(actors);
        movieForm.setCategories(new HashSet<>());

        Mockito.when(movieRepository.save(any(Movie.class))).thenReturn(movie);

        Assert.assertEquals(movieService.createMovie(movieForm, uid)
                , "6cae80b7-3275-4b1e-8c67-6e2e60ba225a");
    }

    @Test
    public void shouldThrowFieldValidationExceptionOnCreateMovie() {
        Movie movie = new Movie();
        MovieForm movieForm = new MovieForm();
        String uid = "3d1ba0ae-b055-4b31-80c9-31d7dc86a717";
        Mockito.when(movieRepository.save(movie)).thenReturn(movie);

        Assert.assertThrows(FieldValidationException.class, () -> movieService.createMovie(movieForm, uid));
    }

    @Test
    public void shouldGetMovie() {
        String mid = "6cae80b7-3275-4b1e-8c67-6e2e60ba225a";
        Movie movie = new Movie();
        movie.setTitle("Movie");
        movie.setId(UUID.fromString(mid));
        movie.setBudget(123.23);
        movie.setImageLink("C:\\Users\\Collabera\\OneDrive\\Documents\\fileupload\\02_11_2022_12_36_34_95.png");
        movie.setReleaseDate(LocalDate.parse("2022-11-13"));
        movie.setMaturityRating(MaturityRating.R);
        movie.setCategories(new HashSet<>());
        movie.setActors(new HashSet<>());

        Mockito.when(movieRepository
                        .findById(movie.getId()))
                .thenReturn(Optional.of(movie));

        Assert.assertEquals(movieService.getMovieById(mid).getTitle(), "Movie");
    }

    @Test
    public void shouldThrowMovieNotFoundExceptionOnGet() {
        String mid = "6cae80b7-3275-4b1e-8c67-6e2e60ba225a";
        String wrongMid = "6cae80b7-3275-4b1e-8c67-6e2e60ba225b";
        Movie movie = new Movie();
        movie.setTitle("Movie");
        movie.setId(UUID.fromString(mid));
        movie.setBudget(123.23);
        movie.setImageLink("C:\\Users\\Collabera\\OneDrive\\Documents\\fileupload\\02_11_2022_12_36_34_95.png");
        movie.setReleaseDate(LocalDate.parse("2022-11-13"));
        movie.setMaturityRating(MaturityRating.R);
        movie.setCategories(new HashSet<>());
        movie.setActors(new HashSet<>());

        Mockito.when(movieRepository
                        .findById(movie.getId()))
                .thenReturn(Optional.of(movie));

        Assert.assertThrows(MovieNotFoundException.class, () -> movieService.getMovieById(wrongMid));
    }

    @Test
    public void shouldThrowFieldValidationExceptionFoundOnGet() {
        String mid = "6cae80b7-3275-4b1e-8c67-6e2e60ba225aa";
        MovieForm movieForm = new MovieForm();
        movieForm.setTitle("Movie");

        Mockito.when(movieRepository
                        .findById(UUID.randomUUID()))
                .thenReturn(Optional.ofNullable(null));

        Assert.assertThrows(FieldValidationException.class, () -> movieService.getMovieById(mid));
    }

    @Test
    public void shouldThrowFieldValidationExceptionFoundOnGetAll() {
        Assert.assertThrows(FieldValidationException.class
                , () -> movieService.getAllMovies(0, 0, null, null));
    }

    @Test
    public void shouldReturnPageResultOnGetAll() {
        Mockito.when(movieRepository
                .findAll(any(PageRequest.class)))
                .thenReturn(new PageImpl<>(new ArrayList<>()
                        , PageRequest.of(1, 10), 1));

        PageResult result = movieService.getAllMovies(1, 10, null, null);

        Assert.assertEquals(result.getPage(),1);
        Assert.assertEquals(result.getSize(),0);
        Assert.assertEquals(result.getTotalPage(), 1);
        Assert.assertEquals(result.getContent().size(), 0);
    }

    @Test
    public void shouldSuccessOnDelete() {
        String mid = "6cae80b7-3275-4b1e-8c67-6e2e60ba225a";
        Movie movie = new Movie(UUID.fromString(mid));
        movie.setReleaseDate(LocalDate.MIN);
        Mockito.when(movieRepository.findById(UUID.fromString(mid)))
                .thenReturn(Optional.of(movie));

        Assert.assertEquals(movieService.deleteMovie(mid), true);
    }

    @Test
    public void shouldThrowFieldValidationExceptionOnDelete() {
        String mid = "6cae80b7-3275-4b1e-8c67-6e2e60ba225aa";

        Assert.assertThrows(FieldValidationException.class, ()-> movieService.deleteMovie(mid));
    }

    @Test
    public void shouldThrowMovieNotFoundExceptionOnDelete() {
        String mid = "6cae80b7-3275-4b1e-8c67-6e2e60ba225a";
        String wrong_mid = "7cae80b7-3275-4b1e-8c67-6e2e60ba225a";
        Movie movie = new Movie(UUID.fromString(mid));
        Mockito.when(movieRepository.findById(UUID.fromString(mid)))
                .thenReturn(Optional.of(movie));

        Assert.assertThrows(MovieNotFoundException.class, ()-> movieService.deleteMovie(wrong_mid));
    }

    @Test
    public void shouldSuccessOnUpdate() {
        String mid = "6cae80b7-3275-4b1e-8c67-6e2e60ba225a";
        MovieForm movieForm = new MovieForm();
        movieForm.setBudget(123.23);


        Movie movie = new Movie(UUID.fromString(mid));
        Mockito.when(movieRepository.findById(UUID.fromString(mid)))
                .thenReturn(Optional.of(movie));

        Assert.assertTrue(movieService.updateMovie(mid, movieForm));
    }

    @Test
    public void shouldThrowFieldValidationExceptionUpdate() {
        String mid = "6cae80b7-3275-4b1e-8c67-6e2e60ba225a";
        MovieForm movieForm = new MovieForm();
        movieForm.setTitle("unsuported field");
        movieForm.setBudget(123.23);


        Movie movie = new Movie(UUID.fromString(mid));
        Mockito.when(movieRepository.findById(UUID.fromString(mid)))
                .thenReturn(Optional.of(movie));

        Assert.assertThrows(FieldValidationException.class
                , () -> movieService.updateMovie(mid, movieForm));
    }
    @TestConfiguration
    static class TestConfig {
        @Bean
        public MovieService movieService() {
            return new MovieService();
        }
    }
}
