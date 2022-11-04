package com.dss.actor.controller;

import com.dss.actor.dto.ActorForm;
import com.dss.actor.dto.util.PageResult;
import com.dss.actor.exception.ActorNotFoundException;
import com.dss.actor.exception.FieldValidationException;
import com.dss.actor.service.ActorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ActorController.class)
public class ActorControllerTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private ActorService actorService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void shouldReturnHTTPOkAndStringOnAdd() throws Exception {
        String acid = "afb1dc11-c222-4256-8c6d-ecc798acd303";

        Mockito.when(actorService.addActor(any()))
                .thenReturn(acid);

        mvc.perform(post("/api/v1/actor/add")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(new ActorForm())))
                .andExpect(status().isOk())
                .andExpect(content().string(acid));
    }

    @Test
    public void shouldThrowBadRequestOnAdd() throws Exception {
        String acid = "afb1dc11-c222-4256-8c6d-ecc798acd303";

        Mockito.when(actorService.addActor(any()))
                .thenThrow(FieldValidationException.class);

        mvc.perform(post("/api/v1/actor/add")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(new ActorForm())))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldThrowBadRequestOnView() throws Exception {
        String acid = "afb1dc11-c222-4256-8c6d-ecc798acd303";

        Mockito.when(actorService.getActor(any()))
                .thenThrow(FieldValidationException.class);

        mvc.perform(get("/api/v1/actor/view/" + acid)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(new ActorForm())))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnHttpOkOnView() throws Exception {
        String acid = "afb1dc11-c222-4256-8c6d-ecc798acd303";

        Mockito.when(actorService.getActor(any()))
                .thenReturn(new ActorForm());

        mvc.perform(get("/api/v1/actor/view/" + acid)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(new ActorForm())))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(new ActorForm())));
    }

    @Test
    public void shouldReturnHttpNotFoundOnView() throws Exception {
        String acid = "afb1dc11-c222-4256-8c6d-ecc798acd303";

        Mockito.when(actorService.getActor(any()))
                .thenThrow(ActorNotFoundException.class);

        mvc.perform(get("/api/v1/actor/view/" + acid)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(new ActorForm())))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturnHTTPBadRequestOnViewAll() throws Exception {
        when(actorService.getAllActor(eq(1), eq(10), any(), any()))
                .thenThrow(FieldValidationException.class);

        mvc.perform(get("/api/v1/actor/view/all")
                        .param("pg", "1")
                        .param("sz", "10"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnHTTPOkOnViewAll() throws Exception {
        PageResult<Set<ActorForm>> result = new PageResult<>(1, 1, 10, new HashSet<>());

        when(actorService.getAllActor(eq(1), eq(10), any(), any()))
                .thenReturn(result);

        mvc.perform(get("/api/v1/actor/view/all")
                        .param("pg", "1")
                        .param("sz", "10"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(result)));
    }


    @Test
    public void shouldReturnHTTPOkOnViewAllActorOfMovie() throws Exception {
        String mid = "3d1ba0ae-b055-4b31-80c9-31d7dc85a715";
        PageResult<Set<ActorForm>> result = new PageResult<>(1, 1, 10, new HashSet<>());

        when(actorService.getAllActorOfMovie(eq(mid), eq(1), eq(10), any(), any()))
                .thenReturn(result);

        mvc.perform(get("/api/v1/actor/view")
                        .param("mvid", mid)
                        .param("pg", "1")
                        .param("sz", "10"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(result)));
    }

    @Test
    public void shouldReturnHTTPBadRequestOnViewAllActorOfMovie() throws Exception {
        String mid = "3d1ba0ae-b055-4b31-80c9-31d7dc85a715";
        when(actorService.getAllActorOfMovie(eq(mid), eq(1), eq(10), any(), any()))
                .thenThrow(FieldValidationException.class);

        mvc.perform(get("/api/v1/actor/view")
                        .param("mvid", mid)
                        .param("pg", "1")
                        .param("sz", "10"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnHTTPOkOnUpdate() throws Exception {
        ActorForm actorForm = new ActorForm();
        String acid = "3d1ba0ae-b055-4b31-80c9-31d7dc85a715";

        when(actorService.updateActor(eq(acid), any()))
                .thenReturn(true);

        mvc.perform(put("/api/v1/actor/update/" + acid)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(actorForm)))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnHTTPBadRequestOnUpdate() throws Exception {
        ActorForm actorForm = new ActorForm();
        String acid = "3d1ba0ae-b055-4b31-80c9-31d7dc85a715";

        when(actorService.updateActor(eq(acid), any()))
                .thenThrow(FieldValidationException.class);

        mvc.perform(put("/api/v1/actor/update/" + acid)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(actorForm)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnHTTPNotFoundOnUpdate() throws Exception {
        ActorForm actorForm = new ActorForm();
        String acid = "3d1ba0ae-b055-4b31-80c9-31d7dc85a715";

        when(actorService.updateActor(eq(acid), any()))
                .thenThrow(ActorNotFoundException.class);

        mvc.perform(put("/api/v1/actor/update/" + acid)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(actorForm)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturnHTTPOkOnDelete() throws Exception {
        String acid = "3d1ba0ae-b055-4b31-80c9-31d7dc85a715";

        when(actorService.deleteActor(eq(acid)))
                .thenReturn(true);

        mvc.perform(delete("/api/v1/actor/delete/")
                        .param("acid", acid))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    public void shouldReturnHTTPNotFoundOnDelete() throws Exception {
        String acid = "3d1ba0ae-b055-4b31-80c9-31d7dc85a715";

        when(actorService.deleteActor(eq(acid)))
                .thenThrow(ActorNotFoundException.class);

        mvc.perform(delete("/api/v1/actor/delete/")
                        .param("acid", acid))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturnHTTPBadRequestOnDelete() throws Exception {
        String acid = "3d1ba0ae-b055-4b31-80c9-31d7dc85a715";

        when(actorService.deleteActor(eq(acid)))
                .thenThrow(FieldValidationException.class);

        mvc.perform(delete("/api/v1/actor/delete/")
                        .param("acid", acid))
                .andExpect(status().isBadRequest());
    }
}
