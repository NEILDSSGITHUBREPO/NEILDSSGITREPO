package com.dss.actor.service;

import com.dss.actor.dto.ActorForm;
import com.dss.actor.dto.util.DTOTransformer;
import com.dss.actor.dto.util.PageResult;
import com.dss.actor.dto.util.validator.ActorFormValidator;
import com.dss.actor.dto.util.validator.ValidationError;
import com.dss.actor.entity.Actor;
import com.dss.actor.exception.ActorNotFoundException;
import com.dss.actor.exception.DataEntanglementException;
import com.dss.actor.exception.FieldValidationException;
import com.dss.actor.repository.ActorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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
        Page<Actor> actorPage = null;

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
        Page<Actor> actorPage = null;

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
            if (optActor.isPresent()) {
                Actor actor = optActor.get();
                if (actor.getMovies().isEmpty()) {
                    actorRepository.delete(actor);
                    successDelete = true;
                } else {
                    throw new DataEntanglementException("Can't DELETE Actor. Reason: Actor have assigned movies");
                }
            } else {
                throw new ActorNotFoundException();
            }
        } catch (IllegalArgumentException iae) {
            fieldMessage.put("acid", ValidationError.FORMAT_MISMATCH);
            throw new FieldValidationException(fieldMessage);
        }

        return successDelete;
    }

    public boolean updateActor(String acid, ActorForm actorForm) {
        Map<String, ValidationError> fieldMessage = new HashMap<>();
        boolean successUpdate;

        try {
            if (fieldMessage.size() > 0) {
                throw new FieldValidationException(fieldMessage);
            } else {
                Optional<Actor> optActor = actorRepository.findById(UUID.fromString(acid));
                if (optActor.isPresent()) {
                    Actor actor = optActor.get();
                    actor.setFirstName(Optional.ofNullable(actorForm.getFirstName()).orElse(actor.getFirstName()));
                    actor.setLastName(Optional.ofNullable(actorForm.getLastName()).orElse(actor.getLastName()));
                    actor.setAge(Optional.ofNullable(actorForm.getAge()).orElse(actor.getAge()));
                    actor.setGender(Optional.ofNullable(actorForm.getGender()).orElse(actor.getGender()));
                    actorRepository.save(actor);
                    successUpdate = true;
                } else {
                    throw new ActorNotFoundException();
                }
            }
        } catch (IllegalArgumentException iae) {
            fieldMessage.put("acid", ValidationError.FORMAT_MISMATCH);
            throw new FieldValidationException(fieldMessage);
        }

        return successUpdate;
    }
}
