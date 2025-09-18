package com.project.cdio.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "roles")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ManagementCustomerEntity extends BaseEntity  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne @JoinColumn(name = "staff_id", nullable = false)
    private UserEntity staff;

    @ManyToOne @JoinColumn(name = "customer_id", nullable = false)
    private CustomerEntity customer;

}
