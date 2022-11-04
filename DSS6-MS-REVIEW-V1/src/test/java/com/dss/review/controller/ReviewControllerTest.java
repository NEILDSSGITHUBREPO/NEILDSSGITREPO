package com.dss.review.controller;

import com.dss.review.dto.ReviewForm;
import com.dss.review.dto.util.PageResult;
import com.dss.review.exception.FieldValidationException;
import com.dss.review.exception.MovieNotFoundException;
import com.dss.review.exception.MovieReviewNotFoundException;
import com.dss.review.service.ReviewService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ReviewController.class)
public class ReviewControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    ReviewService reviewService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void shouldReturnHttpOKAndTrueOnAdd() throws Exception {
        String uid = "afb1dc11-c222-4256-8c6d-ecc798acd303";
        String mvid = "bfb1dc11-c222-4256-8c6d-ecc798acd303";
        Mockito.when(reviewService.createReview(any(ReviewForm.class), eq(uid), eq(mvid)))
                .thenReturn(true);

        mvc.perform(post("/api/v1/review/add")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(new ReviewForm()))
                        .header("uid", uid)
                        .param("mvid", mvid))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    public void shouldReturnHttpNotFoundAdd() throws Exception {
        String uid = "afb1dc11-c222-4256-8c6d-ecc798acd303";
        String mvid = "bfb1dc11-c222-4256-8c6d-ecc798acd303";

        Mockito.when(reviewService.createReview(any(ReviewForm.class), eq(uid), eq(mvid)))
                .thenThrow(MovieNotFoundException.class);

        mvc.perform(post("/api/v1/review/add")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(new ReviewForm()))
                        .header("uid", uid)
                        .param("mvid", mvid))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturnHttpBadRequestAdd() throws Exception {
        String uid = "afb1dc11-c222-4256-8c6d-ecc798acd303";
        String mvid = "bfb1dc11-c222-4256-8c6d-ecc798acd303";

        Mockito.when(reviewService.createReview(any(ReviewForm.class), eq(uid), eq(mvid)))
                .thenThrow(FieldValidationException.class);

        mvc.perform(post("/api/v1/review/add")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(new ReviewForm()))
                        .header("uid", uid)
                        .param("mvid", mvid))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnHTTPOkOnViewAll() throws Exception {
        String mvid = "bfb1dc11-c222-4256-8c6d-ecc798acd303";

        PageResult<Set<ReviewForm>> result = new PageResult<>(1, 1, 10, new HashSet<>());

        when(reviewService.viewMovieReviews(eq(mvid), eq(1), eq(10), any(), any()))
                .thenReturn(result);

        mvc.perform(get("/api/v1/review/view/"+mvid)
                        .param("pg", "1")
                        .param("sz", "10"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(result)));
    }

    @Test
    public void shouldReturnHTTPBadRequestOnViewAll() throws Exception {
        String mvid = "bfb1dc11-c222-4256-8c6d-ecc798acd303";

        PageResult<Set<ReviewForm>> result = new PageResult<>(1, 1, 10, new HashSet<>());

        when(reviewService.viewMovieReviews(eq(mvid), eq(1), eq(10), any(), any()))
                .thenThrow(FieldValidationException.class);

        mvc.perform(get("/api/v1/review/view/"+mvid)
                        .param("pg", "1")
                        .param("sz", "10"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnHTTPNotFoundOnViewAll() throws Exception {
        String mvid = "bfb1dc11-c222-4256-8c6d-ecc798acd303";

        PageResult<Set<ReviewForm>> result = new PageResult<>(1, 1, 10, new HashSet<>());

        when(reviewService.viewMovieReviews(eq(mvid), eq(1), eq(10), any(), any()))
                .thenThrow(MovieNotFoundException.class);

        mvc.perform(get("/api/v1/review/view/"+mvid)
                        .param("pg", "1")
                        .param("sz", "10"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturnHttpOkOnView() throws Exception {
        String acid = "afb1dc11-c222-4256-8c6d-ecc798acd303";
        String mid = "3d1ba0ae-b055-4b31-80c9-31d7dc85a715";

        Mockito.when(reviewService.viewMovieReview(eq(mid), eq(acid)))
                .thenReturn(new ReviewForm());

        mvc.perform(get("/api/v1/review/view/"+mid+"/" + acid)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(new ReviewForm())))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnHttpNotFoundOnView() throws Exception {
        String acid = "afb1dc11-c222-4256-8c6d-ecc798acd303";
        String mid = "3d1ba0ae-b055-4b31-80c9-31d7dc85a715";

        Mockito.when(reviewService.viewMovieReview(eq(mid), eq(acid)))
                .thenThrow(MovieNotFoundException.class);

        mvc.perform(get("/api/v1/review/view/"+mid+"/" + acid)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(new ReviewForm())))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturnHttpNotFoundReviewOnView() throws Exception {
        String acid = "afb1dc11-c222-4256-8c6d-ecc798acd303";
        String mid = "3d1ba0ae-b055-4b31-80c9-31d7dc85a715";

        Mockito.when(reviewService.viewMovieReview(eq(mid), eq(acid)))
                .thenThrow(MovieReviewNotFoundException.class);

        mvc.perform(get("/api/v1/actor/view/"+mid+"/" + acid)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(new ReviewForm())))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturnBadRequestOnView() throws Exception {
        String acid = "afb1dc11-c222-4256-8c6d-ecc798acd303";
        String mid = "3d1ba0ae-b055-4b31-80c9-31d7adc85a715";

        Mockito.when(reviewService.viewMovieReview(eq(mid), eq(acid)))
                .thenThrow(FieldValidationException.class);

        mvc.perform(get("/api/v1/review/view/"+mid+"/" + acid)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(new ReviewForm())))
                .andExpect(status().isBadRequest());
    }
}
