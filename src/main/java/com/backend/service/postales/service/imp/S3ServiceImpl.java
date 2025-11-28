package com.backend.service.postales.service.imp;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.backend.service.postales.service.S3Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class S3ServiceImpl implements S3Service {

    private final String BUCKET;

    private final AmazonS3 amazonS3;

    public S3ServiceImpl(@Value("${aws.s3.bucket-name}") String bucket,
                         AmazonS3 amazonS3) {
        BUCKET = bucket;
        this.amazonS3 = amazonS3;
    }


    @Override
    public String putObject(MultipartFile multipartFile) {

        String extension = StringUtils.getFilenameExtension(multipartFile
                .getOriginalFilename());
        String contentType = multipartFile.getContentType();

        if ((!isImagenContentType(contentType) || !isValidImgExtension(extension)))
            throw new RuntimeException("Formato de imagen no soportado, solo se permiten png,jpg,jpeg,gif");


        String key = String.format("%s.%s", UUID.randomUUID(), extension);

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(contentType);
        objectMetadata.setContentLength(multipartFile.getSize());

        try {
            PutObjectRequest putObjectRequest = new PutObjectRequest(
                    BUCKET,
                    key,
                    multipartFile.getInputStream(), objectMetadata);
            amazonS3.putObject(putObjectRequest);

            return amazonS3.getUrl(BUCKET, key).toString();

        } catch (IOException e) {
            throw new RuntimeException("Error al subir la imagen a S3", e);
        }

    }

    private boolean isValidImgExtension(String extension) {
        return "png".equalsIgnoreCase(extension) ||
                "jpg".equalsIgnoreCase(extension) ||
                "jpeg".equalsIgnoreCase(extension) ||
                "gif".equalsIgnoreCase(extension);
    }

    private boolean isImagenContentType(String contentType) {
        return "image/png".equalsIgnoreCase(contentType) ||
                "image/jpg".equalsIgnoreCase(contentType) ||
                "image/jpeg".equalsIgnoreCase(contentType) ||
                "image/gif".equalsIgnoreCase(contentType);
    }
}
