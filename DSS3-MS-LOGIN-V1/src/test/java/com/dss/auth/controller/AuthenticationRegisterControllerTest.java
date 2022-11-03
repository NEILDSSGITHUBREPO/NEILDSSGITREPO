package com.dss.auth.controller;

import com.dss.auth.dto.UserForm;
import com.dss.auth.exception.FieldValidationException;
import com.dss.auth.service.AuthenticationService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Written in JUnit-4
 * Running in Junit-5-vintage-engine
 */
@RunWith(SpringRunner.class)
@WebMvcTest(AuthenticationController.class)
public class AuthenticationRegisterControllerTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private AuthenticationService authenticationService;

    private ObjectMapper objectMapper = new ObjectMapper();


    @Test
    public void shouldReturnHTTPOKAndContentTrueLogin() throws Exception {
        Mockito.when(authenticationService.registerUser(any(UserForm.class))).thenReturn(Boolean.TRUE);

        mvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper
                                .writeValueAsString(new UserForm("neil@gmail.com"
                                        , "P@ssw0rd"
                                        , "09123456789"
                                        , null))))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    public void shouldReturnHTTPOKAndContentFalseLogin() throws Exception {

        Mockito.when(authenticationService.registerUser(any())).thenReturn(Boolean.FALSE);

        mvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper
                                .writeValueAsString(new UserForm("neil@gmail.com"
                                        , "P@ssw0rd"
                                        , "09123456789"
                                        , null))))
                .andExpect(status().isOk())
                .andExpect(content().string("false"));
    }


    @Test
    public void shouldReturnHTTPBadRequest() throws Exception {

        Mockito.when(authenticationService.registerUser(any(UserForm.class)))
                .thenThrow(FieldValidationException.class);

        mvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper
                                .writeValueAsString(new UserForm("neil@gmail.com"
                                        , null
                                        , null
                                        , null))))
                .andExpect(status().isBadRequest());
    }
}
