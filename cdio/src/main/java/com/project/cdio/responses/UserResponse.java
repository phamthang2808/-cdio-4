package com.project.cdio.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.cdio.entities.RoleEntity;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {
    private Long id;

    private String email;

    //    @JsonProperty("phone_number")
    @NotBlank(message = "Phone number is required")
    private String phone;

    @JsonProperty("is_active")
    private boolean active;

    private RoleEntity role;
//    public static UserResponse fromUser(UserEntity user){
//        return UserResponse.builder()
//                .id(user.getId())
//                .fullName(user.getFullName())
//                .phoneNumber(user.getPhoneNumber())
//                .address(user.getAddress())
//                .active(user.isActive())
//                .dateOfBirth(user.getDateOfBirth())
//                .facebookAccountId(user.getFacebookAccountId())
//                .googleAccountId(user.getGoogleAccountId())
//                .role(user.getRole())
//                .build();
//    }
}
