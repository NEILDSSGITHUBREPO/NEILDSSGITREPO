package com.dss.movie.controller;

import com.dss.movie.dto.MovieForm;
import com.dss.movie.dto.util.PageResult;
import com.dss.movie.exception.FieldValidationException;
import com.dss.movie.exception.MovieNotFoundException;
import com.dss.movie.service.MovieService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(MovieController.class)
public class MovieControllerTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private MovieService movieService;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void shouldReturnHTTPOKOnAdd() throws Exception {
        MovieForm movieForm = new MovieForm();
        String uid = "3d1ba0ae-b055-4b31-80c9-31d7dc86a717";
        String mid = "3d1ba0ae-b055-4b31-80c9-31d7dc85a715";

        when(movieService.createMovie(any(), eq(uid)))
                .thenReturn(mid);

        mvc.perform(post("/api/v1/movie/add")
                        .header("uid", uid)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(movieForm)))
                .andExpect(status().isOk())
                .andExpect(content().string(mid));
    }

    @Test
    public void shouldReturnHTTPBadRequestOnAdd() throws Exception {
        MovieForm movieForm = new MovieForm();
        String uid = "3d1ba0ae-b055-4b31-80c9-31d7dc86a717";

        when(movieService.createMovie(any(), eq(uid)))
                .thenThrow(FieldValidationException.class);

        mvc.perform(post("/api/v1/movie/add")
                        .header("uid", uid)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(movieForm)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnHTTPOkAndMovieFormOnView() throws Exception {
        MovieForm movieForm = new MovieForm();
        String mid = "3d1ba0ae-b055-4b31-80c9-31d7dc85a715";

        when(movieService.getMovieById(eq(mid)))
                .thenReturn(movieForm);

        mvc.perform(get("/api/v1/movie/view/" + mid))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(movieForm)));
    }

    @Test
    public void shouldReturnHTTPBadRequestOnView() throws Exception {
        String mid = "3d1ba0ae-b055-4b31-80c9-31d7dc85a715";

        when(movieService.getMovieById(eq(mid)))
                .thenThrow(FieldValidationException.class);

        mvc.perform(get("/api/v1/movie/view/" + mid))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnHTTPNotFoundOnView() throws Exception {
        String mid = "3d1ba0ae-b055-4b31-80c9-31d7dc85a715";

        when(movieService.getMovieById(eq(mid)))
                .thenThrow(MovieNotFoundException.class);

        mvc.perform(get("/api/v1/movie/view/" + mid))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturnHTTPNotFoundOnUpdate() throws Exception {
        MovieForm movieForm = new MovieForm();
        String mid = "3d1ba0ae-b055-4b31-80c9-31d7dc85a715";

        when(movieService.updateMovie(eq(mid), any()))
                .thenThrow(MovieNotFoundException.class);

        mvc.perform(put("/api/v1/movie/update/" + mid)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(movieForm)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturnHTTPBadRequestOnUpdate() throws Exception {
        MovieForm movieForm = new MovieForm();
        String mid = "3d1ba0ae-b055-4b31-80c9-31d7dc85a715";

        when(movieService.updateMovie(eq(mid), any()))
                .thenThrow(FieldValidationException.class);

        mvc.perform(put("/api/v1/movie/update/" + mid)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(movieForm)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnHTTPOkOnUpdate() throws Exception {
        MovieForm movieForm = new MovieForm();
        String mid = "3d1ba0ae-b055-4b31-80c9-31d7dc85a715";

        when(movieService.updateMovie(eq(mid), any()))
                .thenReturn(true);

        mvc.perform(put("/api/v1/movie/update/" + mid)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(movieForm)))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnHTTPOkOnDelete() throws Exception {
        String mid = "3d1ba0ae-b055-4b31-80c9-31d7dc85a715";

        when(movieService.deleteMovie(eq(mid)))
                .thenReturn(true);

        mvc.perform(delete("/api/v1/movie/delete/")
                        .param("mvid", mid))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    public void shouldReturnHTTPNotFoundOnDelete() throws Exception {
        String mid = "3d1ba0ae-b055-4b31-80c9-31d7dc85a715";

        when(movieService.deleteMovie(eq(mid)))
                .thenThrow(MovieNotFoundException.class);

        mvc.perform(delete("/api/v1/movie/delete/")
                        .param("mvid", mid))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturnHTTPBadRequestOnDelete() throws Exception {
        String mid = "3d1ba0ae-b055-4b31-80c9-31d7dc85a715";

        when(movieService.deleteMovie(eq(mid)))
                .thenThrow(FieldValidationException.class);

        mvc.perform(delete("/api/v1/movie/delete/")
                        .param("mvid", mid))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnHTTPBadRequestOnViewAll() throws Exception {
        when(movieService.getAllMovies(eq(1), eq(10), any(), any()))
                .thenThrow(FieldValidationException.class);

        mvc.perform(get("/api/v1/movie/view/all")
                        .param("pg", "1")
                        .param("sz", "10"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnHTTPOkOnViewAll() throws Exception {
        PageResult<Set<MovieForm>> result = new PageResult<>(1, 1, 10, new HashSet<>());

        when(movieService.getAllMovies(eq(1), eq(10), any(), any()))
                .thenReturn(result);

        mvc.perform(get("/api/v1/movie/view/all")
                        .param("pg", "1")
                        .param("sz", "10"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(result)));
    }
}
