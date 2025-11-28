package com.backend.service.postales.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PostalDtoOut {


    private Long id;
    private String code;
    private String family;
    private String fromName;
    private String toName;
    private String dedication;
    private String imageUrl;
}
