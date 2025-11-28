package com.backend.service.postales.service;

import com.backend.service.postales.dto.PostalDtoOut;
import com.backend.service.postales.dto.PostalDtoinput;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostalService {

    List<PostalDtoOut> listPostales();

    PostalDtoOut registerPostal(String postalDtoOut, MultipartFile image);

    PostalDtoOut getPostalByCode(String code);

    PostalDtoOut findPostal(Long id);

    PostalDtoOut updatePostal(PostalDtoinput postalDtoinput, Long id);

    void deletePostal(Long id);

}
