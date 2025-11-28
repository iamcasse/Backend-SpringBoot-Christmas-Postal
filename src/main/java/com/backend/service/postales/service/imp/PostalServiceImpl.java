package com.backend.service.postales.service.imp;

import com.backend.service.postales.dto.PostalDtoOut;
import com.backend.service.postales.dto.PostalDtoinput;
import com.backend.service.postales.entity.Postal;
import com.backend.service.postales.exception.DuplicatePostalException;
import com.backend.service.postales.exception.ResourceNotFoundException;
import com.backend.service.postales.repository.PostalRepository;
import com.backend.service.postales.service.PostalService;
import com.backend.service.postales.service.S3Service;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import tools.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostalServiceImpl implements PostalService {


    private final PostalRepository postalRepository;
    private final ModelMapper modelMapper;
    private final S3Service s3Service;
    private final ObjectMapper objectMapper;

    public PostalServiceImpl(PostalRepository postalRepository, ModelMapper modelMapper, S3Service s3Service, ObjectMapper objectMapper) {
        this.postalRepository = postalRepository;
        this.modelMapper = modelMapper;
        this.s3Service = s3Service;
        this.objectMapper = objectMapper;
    }


    @Override
    @Transactional(readOnly = true)
    public List<PostalDtoOut> listPostales() {
        return postalRepository.findAll().stream()
                .map(postal -> modelMapper.map(postal, PostalDtoOut.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public PostalDtoOut registerPostal(String postalDtoInputJson, MultipartFile image) {

        PostalDtoinput postalDtoInput = objectMapper.readValue(postalDtoInputJson, PostalDtoinput.class);

        postalRepository.findByCode(postalDtoInput.getCode()).ifPresent(p -> {
            throw new DuplicatePostalException("Postal code already exists: " + postalDtoInput.getCode());
        });

        String imageUrl = s3Service.putObject(image);
        postalDtoInput.setImageUrl(imageUrl);
        Postal postalARegistrar = modelMapper.map(postalDtoInput, Postal.class);
        return modelMapper.map(postalRepository.save(postalARegistrar), PostalDtoOut.class);
    }

    @Override
    @Transactional(readOnly = true)
    public PostalDtoOut getPostalByCode(String code) {

        return postalRepository.findByCode(code)
                .map(postal -> modelMapper.map(postal, PostalDtoOut.class))
                .orElseThrow(() -> new ResourceNotFoundException("Postal code not found: " + code));


    }

    @Override
    @Transactional(readOnly = true)
    public PostalDtoOut findPostal(Long id) {
        return postalRepository.findById(id)
                .map(postal -> modelMapper.map(postal, PostalDtoOut.class))
                .orElseThrow(() -> new ResourceNotFoundException("Postal not found with id: " + id));
    }

    @Override
    @Transactional
    public PostalDtoOut updatePostal(PostalDtoinput postalDtoinput, Long id) {

        postalRepository.findByCode(postalDtoinput.getCode()).ifPresent(p -> {
            if (!p.getId().equals(id)) {
                throw new DuplicatePostalException("Postal code already exists: " + postalDtoinput.getCode());
            }
        });


        return postalRepository.findById(id)
                .map(postal -> {
                    postal.setCode(postalDtoinput.getCode());
                    postal.setDedication(postalDtoinput.getDedication());
                    postal.setFamily(postalDtoinput.getFamily());
                    postal.setImageUrl(postalDtoinput.getImageUrl());
                    return modelMapper.map(postalRepository.save(postal), PostalDtoOut.class);
                }).orElseThrow(() -> new ResourceNotFoundException("Postal not found with id: " + id));


    }

    @Override
    @Transactional
    public void deletePostal(Long id) {
        if (!postalRepository.existsById(id))
            throw new ResourceNotFoundException("Postal not found with id: " + id);
        postalRepository.deleteById(id);


    }
}
