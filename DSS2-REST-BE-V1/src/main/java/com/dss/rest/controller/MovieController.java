package com.dss.rest.controller;

import com.dss.rest.dto.MovieForm;
import com.dss.rest.dto.PageResult;
import com.dss.rest.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("api/v1/movie")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @PostMapping("/add")
    public String addMovie(@RequestBody MovieForm movieForm, @RequestHeader("uid") String id) {
        return movieService.createMovie(movieForm, id);
    }

    @GetMapping("/view/{mvid}")
    public MovieForm viewMovie(@PathVariable(name = "mvid") String mvid) {
        return movieService.getMovieById(mvid);
    }

    @GetMapping("/view/all")
    public PageResult<Set<MovieForm>> viewAllMovie(@RequestParam("pg") int page
            , @RequestParam("sz") int size
            , @RequestParam(value = "sf", required = false) String sortField
            , @RequestParam(value = "sd", required = false) String sortDirection) {
        return movieService.getAllMovies(page, size, sortField, sortDirection);
    }
}
