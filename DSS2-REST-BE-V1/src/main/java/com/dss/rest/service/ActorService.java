package com.dss.rest.service;

import com.dss.rest.dto.ActorForm;
import com.dss.rest.dto.util.DTOTransformer;
import com.dss.rest.dto.util.validator.ActorFormValidator;
import com.dss.rest.dto.util.validator.UserFormValidator;
import com.dss.rest.dto.util.validator.ValidationError;
import com.dss.rest.entity.Actor;
import com.dss.rest.exception.ActorNotFoundException;
import com.dss.rest.exception.FieldValidationException;
import com.dss.rest.repository.ActorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * Service class for Actor
 */
@Service
public class ActorService {

    @Autowired
    private ActorRepository actorRepository;

    /**
     * Creates new Actor and save it to database
     *
     * @Param ActorForm
     * @Return String(actorId)
     */
    public String addActor(ActorForm actorForm) {
        Map<String, ValidationError> fieldMessage = ActorFormValidator.validateActorForm(actorForm);
        String actorId = "";

        if (fieldMessage.size() > 0) {
            throw new FieldValidationException(fieldMessage);
        } else {
            Actor actor = DTOTransformer.transformToActor(actorForm);
            actor = actorRepository.save(actor);
            actorId = actor.getId().toString();
        }

        return actorId;
    }

    /**
     * Get specific actor
     * @Param String acid(actor ID)
     * @Return ActorForm
     * */
    public ActorForm getActor(String acid) {
        try {
            Optional<Actor> optActor = actorRepository.findById(UUID.fromString(acid));

            if (optActor.isPresent()) {
                return DTOTransformer.transformToActorForm(optActor.get());
            } else {
                throw new ActorNotFoundException();
            }

        } catch (IllegalArgumentException iae) {
            Map<String, ValidationError> fieldMessage = new HashMap<>();
            fieldMessage.put("acid", ValidationError.FORMAT_MISMATCH);
            throw new FieldValidationException(fieldMessage);
        }
    }
}
