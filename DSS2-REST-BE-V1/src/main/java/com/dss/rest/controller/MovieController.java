package com.dss.rest.controller;

import com.dss.rest.dto.MovieForm;
import com.dss.rest.dto.util.PageResult;
import com.dss.rest.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * Rest Controller for Movie
 * */
@RestController
@RequestMapping("api/v1/movie")
public class MovieController {

    @Autowired
    private MovieService movieService;

    /**
     * controller method for adding a movie
     * @Param MovieForm, String
     * @Return String (MovieId)
     * */
    @PostMapping("/add")
    public String addMovie(@RequestBody MovieForm movieForm, @RequestHeader("uid") String id) {
        return movieService.createMovie(movieForm, id);
    }

    /**
     * controller method to view a specific movie
     * @Param String
     * @Return MovieForm
     * */
    @GetMapping("/view/{mvid}")
    public MovieForm viewMovie(@PathVariable(name = "mvid") String mvid) {
        return movieService.getMovieById(mvid);
    }

    /**
     * controller method for viewing the movies in pages
     * @Param int, int, String, String
     * @Return PageResult<Set<MovieForm>>
     * */
    @GetMapping("/view/all")
    public PageResult<Set<MovieForm>> viewAllMovie(@RequestParam("pg") int page
            , @RequestParam("sz") int size
            , @RequestParam(value = "sf", required = false) String sortField
            , @RequestParam(value = "sd", required = false) String sortDirection) {
        return movieService.getAllMovies(page, size, sortField, sortDirection);
    }

    /**
     * controller method to update a specific movie
     * @Param String
     * @Return boolean; true if success
     * */
    @PutMapping("update/{mvid}")
    public boolean updateMovie(@PathVariable(name = "mvid") String mvid, @RequestBody MovieForm movieForm) {
        return movieService.updateMovie(mvid, movieForm);
    }

    /**
     * controller method to delete a specific movie
     * @Param String
     * @Return boolean; true if success
     * */
    @DeleteMapping("delete")
    public boolean updateMovie(@RequestParam(name = "mvid") String mvid) {
        return movieService.deleteMovie(mvid);
    }
}
