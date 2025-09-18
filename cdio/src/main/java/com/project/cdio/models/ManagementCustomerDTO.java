package com.project.cdio.models;

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
