package com.project.cdio.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AllUserResponse {

    private Long userId;

//    @JsonProperty("fullname")
    private String fullName;

    //    @NotBlank(message = "Email is required")
    private String email;

    @JsonProperty("phone_number")
//    @NotBlank(message = "Phone number is required")
    private String phoneNumber;

    //    @JsonProperty("password")
//    @NotBlank(message = "Password is required")
//    private String password;


    private String img;

}
