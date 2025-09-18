package com.project.cdio.services;

import com.project.cdio.entities.UserEntity;
import com.project.cdio.models.UserDTO;

public interface IUserService {
    UserEntity createUser(UserDTO userDTO) throws Exception;
    String login(String phoneNumber, String password, Long roleId) throws Exception;
    UserEntity getUserDetailsFromToken(String token) throws Exception;
//    UserEntity updateUser(Long userId, UpdateUserDTO updateUserDTO) throws Exception;
}
