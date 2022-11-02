package com.dss.actor.dto.util;

import com.dss.actor.dto.ActorForm;
import com.dss.actor.entity.Actor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/*
 * Utility class to transform a DTO to Entity
 * */
public class DTOTransformer {

    /**
     * Transforms ActorForm to Actor
     *
     * @Param ActorReview
     * @Return Actor
     */
    public static Actor transformToActor(ActorForm actorForm) {
        Actor actor = new Actor();

        if(actorForm.getId() != null) {
            actor.setId(UUID.fromString(actorForm.getId()));
        }else {
            actor.setFirstName(actorForm.getFirstName());
            actor.setLastName(actorForm.getLastName());
            actor.setGender(actorForm.getGender());
            actor.setAge(actorForm.getAge());
        }

        return actor;
    }

    /**
     * Transforms Actor to ActorForm
     */
    public static ActorForm transformToActorForm(Actor actor) {
        ActorForm actorForm = new ActorForm();

        actorForm.setId(actor.getId().toString());
        actorForm.setFirstName(actor.getFirstName());
        actorForm.setLastName(actor.getLastName());
        actorForm.setGender(actor.getGender());
        actorForm.setAge(actor.getAge());

        return actorForm;
    }
}
