package com.project.cdio.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class RoomResponse {

    @JsonProperty("message")
    private String message;

    @JsonProperty("errors")
    private List<String> errors;

    @JsonProperty("room")
    private RoomEntity roomDTO;

}
