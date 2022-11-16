package com.dss.actor.service;

import com.dss.actor.dto.ActorForm;
import com.dss.actor.dto.util.PageResult;
import com.dss.actor.entity.Actor;
import com.dss.actor.exception.ActorNotFoundException;
import com.dss.actor.exception.FieldValidationException;
import com.dss.actor.repository.ActorRepository;
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

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@RunWith(SpringRunner.class)
public class ActorServiceTest {

    @MockBean
    ActorRepository actorRepository;

    @Autowired
    ActorService actorService;

    @Test
    public void shouldSuccessFullyCreateActor() {
        String acid = "3d1ba0ae-b055-4b31-80c9-31d7dc86a717";

        ActorForm actorForm = new ActorForm();
        actorForm.setAge((short) 11);
        actorForm.setGender("M");
        actorForm.setFirstName("Neil");
        actorForm.setLastName("De Guman");

        Mockito.when(actorRepository.save(any(Actor.class)))
                .thenReturn(new Actor(UUID.fromString(acid)));

        Assert.assertEquals(acid, actorService.addActor(actorForm));
    }

    @Test
    public void shouldThrowFieldValidationExceptionOnCreateActor() {
        String acid = "3d1ba0ae-b055-4b31-80c9-31d7dc86a717";

        ActorForm actorForm = new ActorForm();
        actorForm.setAge((short) 11);
        actorForm.setGender("M");
        actorForm.setFirstName("Neil");

        Mockito.when(actorRepository.save(any(Actor.class)))
                .thenReturn(new Actor(UUID.fromString(acid)));

        Assert.assertThrows(FieldValidationException.class
                , () -> actorService.addActor(actorForm));
    }

    @Test
    public void shouldThrowFieldValidationExceptionOnGetActor() {
        String acid = "3d1ba0ae-b055-4b31-80c9-31d7dc86a717";

        Mockito.when(actorRepository.findById(any()))
                .thenReturn(Optional.of(new Actor(UUID.fromString(acid))));

        Assert.assertThrows(FieldValidationException.class
                , () -> actorService.getActor(acid + "a"));
    }

    @Test
    public void shouldThrowActorNotFoundExceptionOnGetActor() {
        String acid = "3d1ba0ae-b055-4b31-80c9-31d7dc86a717";
        String wrong_acid = "3d1ba0ae-b055-4b31-80c9-31d7dc86a718";

        Mockito.when(actorRepository.findById(UUID.fromString(acid)))
                .thenReturn(Optional.of(new Actor(UUID.fromString(acid))));

        Assert.assertThrows(ActorNotFoundException.class
                , () -> actorService.getActor(wrong_acid));
    }

    @Test
    public void shouldSuccessFullyGetActor() {
        String acid = "3d1ba0ae-b055-4b31-80c9-31d7dc86a717";

        Mockito.when(actorRepository.findById(UUID.fromString(acid)))
                .thenReturn(Optional.of(new Actor(UUID.fromString(acid))));

        Assert.assertEquals(actorService.getActor(acid).getId(), acid);
    }

    @Test
    public void shouldGetAllActor() {
        Mockito.when(actorRepository
                        .findAll(any(PageRequest.class)))
                .thenReturn(new PageImpl<>(new ArrayList<>()
                        , PageRequest.of(1, 10), 1));

        PageResult result = actorService.getAllActor(1, 10, null, null);

        Assert.assertEquals(1, result.getPage());
        Assert.assertEquals(0, result.getSize());
        Assert.assertEquals(1, result.getTotalPage());
        Assert.assertEquals(0, result.getContent().size());
    }

    @Test
    public void shouldThrowFieldValidationExceptionFoundOnGetAllActor() {
        Assert.assertThrows(FieldValidationException.class
                , () -> actorService.getAllActor(0, 0, null, null));
    }

    @Test
    public void shouldGetAllActorOfMovie() {
        String mvid = "3d1ba0ae-b055-4b31-80c9-31d7dc86a717";

        Mockito.when(actorRepository.findAllByMoviesId(eq(UUID.fromString(mvid)), any(Pageable.class)))
                .thenReturn(new PageImpl<>(new ArrayList<>()
                        , PageRequest.of(1, 10), 1));

        PageResult result = actorService.getAllActorOfMovie(mvid, 1, 10, null, null);

        Assert.assertEquals(1,result.getPage());
        Assert.assertEquals(0, result.getSize());
        Assert.assertEquals(1, result.getTotalPage());
        Assert.assertEquals(0, result.getContent().size());
    }

    @Test
    public void shouldThrowFieldValidationExceptionFoundOnGetAllActorOfMovie() {
        String mvid = "3d1ba0ae-b055-4b31-80c9-31d7dc86a717";

        Mockito.when(actorRepository.findAllByMoviesId(eq(UUID.fromString(mvid)), any(Pageable.class)))
                .thenReturn(new PageImpl<>(new ArrayList<>()
                        , PageRequest.of(1, 10), 1));

        Assert.assertThrows(FieldValidationException.class
                , () -> actorService.getAllActorOfMovie(mvid, 10, 0, null, null));
    }

    @Test
    public void shouldThrowMovieNotFoundOnGetAllActorOfMovie() {
        String mvid = "3d1ba0ae-b055-4b31-80c9-31d7dc86a717";

        Mockito.when(actorRepository.findAllByMoviesId(eq(UUID.fromString(mvid)), any(Pageable.class)))
                .thenReturn(new PageImpl<>(new ArrayList<>()
                        , PageRequest.of(1, 10), 1));

        Assert.assertThrows(FieldValidationException.class
                , () -> actorService.getAllActorOfMovie(mvid, 0, 0, null, null));
    }

    @Test
    public void shouldSuccessOnDelete() {
        String acid = "3d1ba0ae-b055-4b31-80c9-31d7dc86a717";
        Actor actor = new Actor(UUID.fromString(acid));
        actor.setMovies(new HashSet<>());

        Mockito.when(actorRepository.findById(UUID.fromString(acid)))
                .thenReturn(Optional.of(actor));

        Assert.assertTrue(actorService.deleteActor(acid));
    }

    @Test
    public void shouldThrowFieldValidationExceptionOnDelete() {
        String acid = "3d1ba0ae-b055-4b31-80c9-31d7dc86a7172";
        Assert.assertThrows(FieldValidationException.class, () -> actorService.deleteActor(acid));
    }

    @Test
    public void shouldThrowActorNotFoundExceptionOnDelete() {
        String acid = "3d1ba0ae-b055-4b31-80c9-31d7dc86a717";
        String wrong_acid = "4d1ba0ae-b055-4b31-80c9-31d7dc86a717";
        Actor actor = new Actor(UUID.fromString(acid));
        actor.setMovies(new HashSet<>());

        Mockito.when(actorRepository.findById(UUID.fromString(acid)))
                .thenReturn(Optional.of(actor));

        Assert.assertThrows(ActorNotFoundException.class, () -> actorService.deleteActor(wrong_acid));
    }

    @Test
    public void shouldSuccessOnUpdate() {
        String acid = "3d1ba0ae-b055-4b31-80c9-31d7dc86a717";
        Actor actor = new Actor(UUID.fromString(acid));

        ActorForm actorForm = new ActorForm();
        actorForm.setAge((short)1);

        Mockito.when(actorRepository.findById(UUID.fromString(acid)))
                .thenReturn(Optional.of(actor));

        Assert.assertTrue(actorService.updateActor(acid, actorForm));
    }

    @Test
    public void shouldThrowActorNotFoundExceptionOnUpdate() {
        String acid = "3d1ba0ae-b055-4b31-80c9-31d7dc86a717";
        String wrong_acid = "4d1ba0ae-b055-4b31-80c9-31d7dc86a717";
        Actor actor = new Actor(UUID.fromString(acid));

        ActorForm actorForm = new ActorForm();
        actorForm.setAge((short)1);

        Mockito.when(actorRepository.findById(UUID.fromString(acid)))
                .thenReturn(Optional.of(actor));

        Assert.assertThrows(ActorNotFoundException.class
                , () -> actorService.updateActor(wrong_acid, actorForm));
    }

    @Test
    public void shouldThrowFieldValidationExceptionOnUpdate() {
        String acid = "3d1ba0ae-b055-4b31-80c9-31d7dc86a717";
        String wrong_acid = "a4d1ba0ae-b055-4b31-80c9-31d7dc86a717";
        Actor actor = new Actor(UUID.fromString(acid));

        ActorForm actorForm = new ActorForm();
        actorForm.setAge((short)1);

        Mockito.when(actorRepository.findById(UUID.fromString(acid)))
                .thenReturn(Optional.of(actor));

        Assert.assertThrows(FieldValidationException.class
                , () -> actorService.updateActor(wrong_acid, actorForm));
    }
    @TestConfiguration
    static class TestConfig {
        @Bean
        public ActorService actorService() {
            return new ActorService();
        }
    }
}
