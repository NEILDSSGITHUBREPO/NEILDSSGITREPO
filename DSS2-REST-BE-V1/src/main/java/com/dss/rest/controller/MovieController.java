package com.dss.rest.controller;

import com.dss.rest.dto.MovieForm;
import com.dss.rest.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/movie")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @PostMapping("/add")
    public String addMovie(@RequestBody MovieForm movieForm, @RequestHeader("uid") String id){
        return movieService.createMovie(movieForm, id);
    }
}
