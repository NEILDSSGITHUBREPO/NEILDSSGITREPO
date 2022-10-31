package com.dss.rest.service;

import com.dss.rest.dto.ActorForm;
import com.dss.rest.dto.util.DTOTransformer;
import com.dss.rest.dto.util.PageResult;
import com.dss.rest.dto.util.validator.ActorFormValidator;
import com.dss.rest.dto.util.validator.ValidationError;
import com.dss.rest.entity.Actor;
import com.dss.rest.exception.ActorNotFoundException;
import com.dss.rest.exception.DataEntanglementException;
import com.dss.rest.exception.FieldValidationException;
import com.dss.rest.repository.ActorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

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
     *
     * @Param String acid(actor ID)
     * @Return ActorForm
     */
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

    /**
     * Service method for getting all Actors in pages
     *
     * @Param int, int, String, String
     * @Returns PageResult<Set < ActorForm>>
     */
    public PageResult<Set<ActorForm>> getAllActor(int page, int size, String sortField, String sortDirection) {
        Page actorPage = null;

        Map<String, ValidationError> fieldMessage = new HashMap<>();
        if (sortDirection == null || sortDirection.isEmpty()) sortDirection = "ASC";
        if (sortField == null || sortField.isEmpty()) sortField = "id";

        if (page <= 0) fieldMessage.put("page", ValidationError.UNSSUPORTED_RANGE);
        if (size <= 0) fieldMessage.put("size", ValidationError.UNSSUPORTED_RANGE);

        if (fieldMessage.size() > 0) {
            throw new FieldValidationException(fieldMessage);
        } else {
            actorPage = actorRepository.findAll(PageRequest.of(page - 1, size)
                    .withSort(Sort.by(Sort.Direction.fromString(sortDirection), sortField)));

            if (actorPage.getTotalPages() < page) {
                fieldMessage.put("page", ValidationError.UNSSUPORTED_RANGE);
                throw new FieldValidationException(fieldMessage);
            }
        }

        List<Actor> actors = actorPage.getContent();
        Set<ActorForm> actorForms = actors.parallelStream()
                .map(DTOTransformer::transformToActorForm)
                .collect(Collectors.toSet());

        return new PageResult<>(page, actorPage.getTotalPages(), actorForms.size(), actorForms);
    }

    public PageResult<Set<ActorForm>> getAllActorOfMovie(String mvid, int page, int size
            , String sortField, String sortDirection) {
        Page actorPage = null;

        Map<String, ValidationError> fieldMessage = new HashMap<>();
        if (sortDirection == null || sortDirection.isEmpty()) sortDirection = "ASC";
        if (sortField == null || sortField.isEmpty()) sortField = "id";

        if (page <= 0) fieldMessage.put("page", ValidationError.UNSSUPORTED_RANGE);
        if (size <= 0) fieldMessage.put("size", ValidationError.UNSSUPORTED_RANGE);

        if (fieldMessage.size() > 0) {
            throw new FieldValidationException(fieldMessage);
        } else {
            actorPage = actorRepository.findAllByMoviesId(UUID.fromString(mvid), PageRequest.of(page - 1, size)
                    .withSort(Sort.by(Sort.Direction.fromString(sortDirection), sortField)));

            if (actorPage.getTotalPages() < page) {
                fieldMessage.put("page", ValidationError.UNSSUPORTED_RANGE);
                throw new FieldValidationException(fieldMessage);
            }
        }

        List<Actor> actors = actorPage.getContent();
        Set<ActorForm> actorForms = actors.parallelStream()
                .map(DTOTransformer::transformToActorForm)
                .collect(Collectors.toSet());

        return new PageResult<>(page, actorPage.getTotalPages(), actorForms.size(), actorForms);
    }

    public boolean deleteActor(String acid) {
        Map<String, ValidationError> fieldMessage = new HashMap<>();
        boolean successDelete = false;

        try {
            Optional<Actor> optActor = actorRepository.findById(UUID.fromString(acid));
            if(optActor.isPresent()){
                Actor actor = optActor.get();
                if(actor.getMovies().size() <= 0){
                    actorRepository.delete(actor);
                    successDelete = true;
                }else{
                    throw new DataEntanglementException("Can't DELETE Actor. Reason: Actor have assigned movies");
                }
            }else{
                throw new ActorNotFoundException();
            }
        } catch (IllegalArgumentException iae) {
            fieldMessage.put("acid", ValidationError.FORMAT_MISMATCH);
            throw new FieldValidationException(fieldMessage);
        }

        return successDelete;
    }
}
