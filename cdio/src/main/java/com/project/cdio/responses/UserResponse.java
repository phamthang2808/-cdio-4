package com.project.cdio.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.cdio.entities.RoleEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UserResponse{

    @JsonProperty("fullname")
    private String fullName;

//    @NotBlank(message = "Email is required")
    private String email;

    @JsonProperty("phone_number")
//    @NotBlank(message = "Phone number is required")
    private String phoneNumber;

//    @JsonProperty("password")
//    @NotBlank(message = "Password is required")
    private String password;


    private String img;


}
