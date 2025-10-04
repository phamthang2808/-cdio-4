package com.project.cdio.services;

import com.project.cdio.entities.UserEntity;
import com.project.cdio.exceptions.DataNotFoundException;
import com.project.cdio.models.dto.UserDTO;
import com.project.cdio.models.request.StaffCreateRequest;
import com.project.cdio.models.request.UserUpdateRequest;
import com.project.cdio.models.responses.AllStaffResponse;
import com.project.cdio.models.responses.AllUserResponse;
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
    Page<AllUserResponse> getAllUsers(int page, int limit);
}
