package com.project.cdio.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.cdio.entities.UserEntity;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterResponse {
    @JsonProperty("message")
    private String message;

    @JsonProperty("user")
    private UserEntity user;
}
