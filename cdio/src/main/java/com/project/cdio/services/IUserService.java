package com.project.cdio.services;

import com.project.cdio.entities.UserEntity;
import com.project.cdio.exceptions.DataNotFoundException;
import com.project.cdio.models.CustomerDTO;
import com.project.cdio.models.UpdateUserDTO;
import com.project.cdio.models.UserDTO;
import com.project.cdio.request.StaffCreateRequest;
import com.project.cdio.request.UserUpdateRequest;
import com.project.cdio.responses.AllStaffResponse;
import com.project.cdio.responses.UserResponse;
import org.springframework.data.domain.Page;

public interface IUserService {
    UserEntity createUser(UserDTO userDTO) throws Exception;
//    String login(String phoneNumber, String password, Long roleId) throws Exception;
    String login(String phoneNumber, String password) throws Exception;
    UserEntity getUserDetailsFromToken(String token) throws Exception;
    UserDTO getUserById(Long userId) throws DataNotFoundException;
    void updateUser(Long userId, UserUpdateRequest userDTO) throws DataNotFoundException;
    Page<AllStaffResponse> getAllStaffs(int page, int limit);
    UserEntity createStaff(StaffCreateRequest staffCreateRequest);
    void deleteUser(long id);
}
