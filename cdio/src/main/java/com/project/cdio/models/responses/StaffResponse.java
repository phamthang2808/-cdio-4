package com.project.cdio.models.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.cdio.entities.CustomerEntity;
import com.project.cdio.entities.UserEntity;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class StaffResponse {

    @JsonProperty("message")
    private String message;

    @JsonProperty("errors")
    private List<String> errors;

    @JsonProperty("staff")
    private UserEntity userEntity;

}