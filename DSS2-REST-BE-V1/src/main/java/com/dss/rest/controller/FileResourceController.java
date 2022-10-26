package com.dss.rest.controller;

import com.dss.rest.service.FileResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/v1/file")
public class FileResourceController {

    @Autowired
    private FileResourceService fileResourceService;

    @PostMapping(value = "upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public String uploadFile(@RequestPart MultipartFile file) {
        return fileResourceService.saveFile(file);
    }

}
