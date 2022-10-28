package com.dss.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.*;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Service for handling file upload
 * */
@Service
public class FileResourceService {

    @Autowired
    private ServletContext context;

    @Value("${app.file.upload.location}")
    private String uploadLocation;

    /**
     * Service method for saving file
     * @Param MultipartFile
     * @Returns String(path location)
     * */
    public String saveFile(MultipartFile multipartFile) {
        String filePath = String.format("%s/%s.%s", uploadLocation
                , LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd_MM_yyyy_HH_mm_ss_SS"))
                , StringUtils.getFilenameExtension(multipartFile.getOriginalFilename()));
        Path uploadPath = Paths.get(filePath);
        try {
            Files.createDirectories(uploadPath);//create if not existing
            Files.copy(multipartFile.getInputStream(), uploadPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return uploadPath.toString();
    }

}
