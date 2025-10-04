package com.project.cdio.models.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateRoomResponse {

    @JsonProperty("message")
    private String message;
}
