package com.backend.service.postales.service;

import org.springframework.web.multipart.MultipartFile;

public interface S3Service {

    String putObject(MultipartFile multipartFile);

}
