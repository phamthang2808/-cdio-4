package com.project.cdio.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.cdio.entities.CustomerEntity;
import com.project.cdio.entities.RoomEntity;
import com.project.cdio.models.RoomDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CustomerResponse {

    @JsonProperty("message")
    private String message;

    @JsonProperty("errors")
    private List<String> errors;

    @JsonProperty("customer")
    private CustomerEntity customerEntity;

}