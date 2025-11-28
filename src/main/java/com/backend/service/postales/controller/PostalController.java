package com.backend.service.postales.controller;

import com.backend.service.postales.dto.PostalDtoOut;
import com.backend.service.postales.dto.PostalDtoinput;
import com.backend.service.postales.service.PostalService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("api/v1/postales")
public class PostalController {

    private final PostalService postalService;

    public PostalController(PostalService postalService) {
        this.postalService = postalService;
    }

    @GetMapping
    public ResponseEntity<List<PostalDtoOut>> listPostales() {
        return new ResponseEntity<>(postalService.listPostales(), HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PostalDtoOut> registerPostal(
            @RequestPart("postal") String postalDtoinput,
            @RequestPart("image")MultipartFile image) {
        return new ResponseEntity<>(postalService.registerPostal(postalDtoinput,image), HttpStatus.OK);

    }

    @GetMapping("/code/{code}")
    public ResponseEntity<PostalDtoOut> getPostalByCode(@PathVariable String code) {
        return new ResponseEntity<>(postalService.getPostalByCode(code), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostalDtoOut> findPostal(@PathVariable Long id) {
        return new ResponseEntity<>(postalService.findPostal(id), HttpStatus.OK);

    }

    @PutMapping("/{id}")
    public ResponseEntity<PostalDtoOut> updatePostal(@RequestBody @Valid PostalDtoinput postalDtoinput, @PathVariable Long id) {
        return new ResponseEntity<>(postalService.updatePostal(postalDtoinput, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deletePostal(@PathVariable Long id) {
        postalService.deletePostal(id);
    }


}
