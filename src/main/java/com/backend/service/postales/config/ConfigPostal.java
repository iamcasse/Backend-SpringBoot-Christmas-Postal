package com.backend.service.postales.config;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigPostal {

    @Value("${aws.s3.region}")
    private String region;

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }


    @Bean
    public AmazonS3 amazonS3Client(){
        return AmazonS3ClientBuilder.standard()
                .withRegion(region)
                .build();
    }


}
