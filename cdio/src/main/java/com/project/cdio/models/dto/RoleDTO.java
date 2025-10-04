package com.project.cdio.models.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class RoleDTO {

    private int roleId;

    private String roleName;
}
