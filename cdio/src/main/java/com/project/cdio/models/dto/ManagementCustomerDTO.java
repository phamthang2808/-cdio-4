package com.project.cdio.models.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ManagementCustomerDTO {

    private UserDTO staffId;

    private CustomerDTO customerId;
}
