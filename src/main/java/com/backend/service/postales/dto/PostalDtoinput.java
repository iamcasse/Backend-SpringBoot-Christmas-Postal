package com.backend.service.postales.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PostalDtoinput {

    @NotBlank(message = "Code is mandatory")
    @Size(max = 20, message = "Code must be at most 100 characters")
    private String code;
    @NotBlank(message = "Family is mandatory")
    @Size(max = 50, message = "Family must be at most 50 characters")
    private String family;
    @NotBlank(message = "From Name is mandatory")
    @Size(max = 15, message = "From Name must be at most 100")
    private String fromName;
    @NotBlank(message = "To Name is mandatory")
    @Size(max = 15, message = "To Name must be at most 100")
    private String toName;
    @NotBlank(message = "Dedication is mandatory")
    @Size(max = 250, message = "Dedication must be at most 250 characters")
    private String dedication;
    @NotBlank(message = "Image URL is mandatory")
    @Size(max = 200, message = "Image URL must be at most 200 characters")
    private String imageUrl;

}

