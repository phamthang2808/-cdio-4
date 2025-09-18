package com.project.cdio.models;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CustomerDTO {

    private String name;

    private String email;

    private String phone;

    private String comment;

}
