package com.dss.movie.controller;

import com.dss.movie.service.FileResourceService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(FileResourceController.class)
public class FileResourceControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    FileResourceService fileResourceService;

    @Test
    public void shouldReturnHttpOkAndPath() throws Exception {
        String path = "C:\\Users\\Collabera\\OneDrive\\Documents\\fileupload\\02_11_2022_12_36_34_95.png";

        MockMultipartFile file
                = new MockMultipartFile(
                "file",
                "test.jpg",
                MediaType.TEXT_PLAIN_VALUE,
                "test to bytes".getBytes()
        );

        when(fileResourceService.saveFile(any(MultipartFile.class)))
                .thenReturn(path);

        mvc.perform(multipart("/api/v1/file/upload")
                        .file(file))
                .andExpect(status().isOk())
                .andExpect(content().string(path));
    }
}
