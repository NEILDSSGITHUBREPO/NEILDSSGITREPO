package com.dss.movie.controller;

import com.dss.movie.service.FileResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * Rest Controller for file resources
 * */
@RestController
@RequestMapping("api/v1/file")
public class FileResourceController {

    @Autowired
    private FileResourceService fileResourceService;

    /**
     * Controller method for uploading file
     * consumes MULTIPART_FORM_DATA
     * @Param MultipartFile
     * @Return String (upload Path)
     * */
    @PostMapping(value = "upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public String uploadFile(@RequestPart MultipartFile file) {
        return fileResourceService.saveFile(file);
    }

}
