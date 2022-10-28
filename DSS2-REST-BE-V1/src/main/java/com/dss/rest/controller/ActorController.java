package com.dss.rest.controller;

import com.dss.rest.dto.ActorForm;
import com.dss.rest.service.ActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Rest Controller for Actor
 */
@RestController
@RequestMapping("api/v1/actor")
public class ActorController {

    @Autowired
    private ActorService actorService;

    /**
     * Controller method to create new actor
     * @Param ActorForm
     * @Return String(actorId)
     * */
    @PostMapping("/add")
    public String addActor(@RequestBody ActorForm actorForm){
        return actorService.addActor(actorForm);
    }

    /**
     * Controller method to view a specific Actor
     * @Param String(actorId)
     * @Return ActorForm
     * */
    @GetMapping("view/{acid}")
    public ActorForm viewActor(@PathVariable(name = "acid") String acid){
        return actorService.getActor(acid);
    }

}
