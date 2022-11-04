package com.dss.review.service;

import com.dss.review.dto.ReviewForm;
import com.dss.review.dto.util.PageResult;
import com.dss.review.entity.Movie;
import com.dss.review.entity.Review;
import com.dss.review.exception.MovieNotFoundException;
import com.dss.review.repository.MovieRepository;
import com.dss.review.repository.ReviewRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@RunWith(SpringRunner.class)
public class ReviewServiceTest {

    @MockBean
    ReviewRepository reviewRepository;

    @MockBean
    MovieRepository movieRepository;

    @Autowired
    ReviewService reviewService;

    @Test
    public void shouldSuccessFullyCreateReview(){
        String mvid = "3d1ba0ae-b055-4b31-80c9-31d7dc86a717";
        String rid = "5d1ba0ae-b055-4b31-80c9-31d7dc86a717";
        String uid = "afb1dc11-c222-4256-8c6d-ecc798acd303";

        Review review = new Review();
        review.setId(UUID.fromString(rid));

        Mockito.when(movieRepository.findById(UUID.fromString(mvid)))
                .thenReturn(Optional.of(new Movie()));

        ReviewForm reviewForm = new ReviewForm();
        reviewForm.setDescription("This is a review from test");
        reviewForm.setRating((short) 2);

        Assert.assertTrue(reviewService.createReview(reviewForm, uid, mvid));
    }

    @Test
    public void shouldGetMovieReview(){
        String mvid = "3d1ba0ae-b055-4b31-80c9-31d7dc86a717";
        String rid = "5d1ba0ae-b055-4b31-80c9-31d7dc86a717";

        Review review = new Review();
        review.setId(UUID.fromString(rid));

        Movie movie = new Movie();
        movie.setId(UUID.fromString(mvid));

        Mockito.when(movieRepository.findById(any(UUID.class)))
                .thenReturn(Optional.of(movie));

        Mockito.when(reviewRepository.findById(UUID.fromString(rid)))
                .thenReturn(Optional.of(review));

        Assert.assertNotNull(reviewService.viewMovieReview(mvid, rid));
    }

    @Test
    public void shouldGetAllReviewOfMovie() {
        String mvid = "3d1ba0ae-b055-4b31-80c9-31d7dc86a717";

        Mockito.when(movieRepository.existsById(any(UUID.class)))
                .thenReturn(Boolean.TRUE);

        Mockito.when(reviewRepository.findAllByReviewedMovieId(eq(UUID.fromString(mvid)), any(Pageable.class)))
                .thenReturn(new PageImpl<>(new ArrayList<>()
                        , PageRequest.of(1, 10), 1));

        PageResult result = reviewService.viewMovieReviews(mvid, 1, 10, null, null);

        Assert.assertEquals(result.getPage(), 1);
        Assert.assertEquals(result.getSize(), 0);
        Assert.assertEquals(result.getTotalPage(), 1);
        Assert.assertEquals(result.getContent().size(), 0);
    }

    @Test
    public void shouldThrowMovieNotFoundWhenGetAllReviewOfMovie() {
        String mvid = "3d1ba0ae-b055-4b31-80c9-31d7dc86a717";

        Mockito.when(movieRepository.existsById(any(UUID.class)))
                .thenReturn(Boolean.FALSE);

       Assert.assertThrows(MovieNotFoundException.class
               , () -> reviewService.viewMovieReviews(mvid, 1, 10, null, null));

    }
    @TestConfiguration
    static class TestConfig {
        @Bean
        public ReviewService reviewService() {
            return new ReviewService();
        }
    }
}
