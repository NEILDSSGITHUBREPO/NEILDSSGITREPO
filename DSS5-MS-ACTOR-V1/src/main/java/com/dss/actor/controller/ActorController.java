package com.dss.actor.controller;

import com.dss.actor.dto.ActorForm;
import com.dss.actor.dto.util.PageResult;
import com.dss.actor.service.ActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

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
     *
     * @Param ActorForm
     * @Return String(actorId)
     */
    @PostMapping("/add")
    public String addActor(@RequestBody ActorForm actorForm) {
        return actorService.addActor(actorForm);
    }

    /**
     * Controller method to view a specific Actor
     *
     * @Param String(actorId)
     * @Return ActorForm
     */
    @GetMapping("view/{acid}")
    public ActorForm viewActor(@PathVariable(name = "acid") String acid) {
        return actorService.getActor(acid);
    }

    @GetMapping("view/all")
    public PageResult<Set<ActorForm>> viewAllActor(@RequestParam("pg") int page
            , @RequestParam("sz") int size
            , @RequestParam(value = "sf", required = false) String sortField
            , @RequestParam(value = "sd", required = false) String sortDirection) {
        return actorService.getAllActor(page, size, sortField, sortDirection);
    }

    @GetMapping("view")
    public PageResult<Set<ActorForm>> viewAllActorOfMovie(@RequestParam("mvid") String mvid
            , @RequestParam("pg") int page
            , @RequestParam("sz") int size
            , @RequestParam(value = "sf", required = false) String sortField
            , @RequestParam(value = "sd", required = false) String sortDirection) {
        return actorService.getAllActorOfMovie(mvid, page, size, sortField, sortDirection);
    }

    /**
     * controller method to update a specific actor
     *
     * @Param String
     * @Return boolean; true if success
     */
    @PutMapping("update/{acid}")
    public boolean updateActor(@PathVariable(name = "acid") String acid, @RequestBody ActorForm actorForm) {
        return actorService.updateActor(acid, actorForm);
    }

    @DeleteMapping("delete")
    public boolean deleteActor(@RequestParam("acid") String acid) {
        return actorService.deleteActor(acid);
    }
}
