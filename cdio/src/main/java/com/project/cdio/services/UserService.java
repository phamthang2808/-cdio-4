package com.project.cdio.services;

import com.project.cdio.components.LocalizationUtils;
import com.project.cdio.convert.UserConvert;
import com.project.cdio.entities.RoleEntity;
import com.project.cdio.entities.UserEntity;
import com.project.cdio.exceptions.PermissionDenyException;
import com.project.cdio.models.UserDTO;
import com.project.cdio.repositories.UserRepository;
import com.project.cdio.components.JwtTokenUtils;
import com.project.cdio.exceptions.DataNotFoundException;
import com.project.cdio.models.*;
import com.project.cdio.repositories.RoleRepository;
import com.project.cdio.utils.MessageKeys;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService implements IUserService{
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtils jwtTokenUtil;
    private final AuthenticationManager authenticationManager;
    private final LocalizationUtils localizationUtils;
    private final UserConvert userConvert;
    @Override
    @Transactional
    public UserEntity createUser(UserDTO userDTO) throws Exception {

        String phoneNumber = userDTO.getPhoneNumber();



        if(userRepository.existsByPhoneNumber(phoneNumber)) {
            throw new DataIntegrityViolationException("Phone number already exists");
        }

        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new DataIntegrityViolationException("Email already exists");
        }

        RoleEntity role =roleRepository.findById(userDTO.getRoleId())
                .orElseThrow(() -> new DataNotFoundException(
                        localizationUtils.getLocalizedMessage(MessageKeys.ROLE_DOES_NOT_EXISTS)));

        if(role.getRoleName().toUpperCase().equals(RoleEntity.ADMIN)) {
            throw new PermissionDenyException("You cannot register an admin account");
        }

        UserEntity newUser = userConvert.convertToEntity(userDTO);
        newUser.setRole(role);
        newUser.setActive(true);


        // Kiểm tra nếu có accountId, không yêu cầu password
        if (userDTO.getFacebookAccountId() == 0 && userDTO.getGoogleAccountId() == 0) {
            String password = userDTO.getPassword();
            String encodedPassword = passwordEncoder.encode(password);
            newUser.setPassword(encodedPassword);
        }
        newUser.setActive(true);
        return userRepository.save(newUser);
    }

    @Override
    public String login(
            String phoneNumber,
            String password,
            Long roleId
    ) throws Exception {

        Optional<UserEntity> optionalUser = userRepository.findByPhoneNumber(phoneNumber);

        if(optionalUser.isEmpty()) {
            throw new DataNotFoundException(localizationUtils.getLocalizedMessage(MessageKeys.WRONG_PHONE_PASSWORD));
        }
//        return optionalUser.get();//muốn trả JWT token ?
        UserEntity existingUser = optionalUser.get();
        //check password
        if (existingUser.getFacebookAccountId() == 0
                && existingUser.getGoogleAccountId() == 0) {
            if(!passwordEncoder.matches(password, existingUser.getPassword())) {
                throw new BadCredentialsException(localizationUtils.getLocalizedMessage(MessageKeys.WRONG_PHONE_PASSWORD));
            }
        }
        Optional<RoleEntity> optionalRole = roleRepository.findById(roleId);
        if(optionalRole.isEmpty() || !roleId.equals(existingUser.getRole().getRoleId())) {
            throw new DataNotFoundException(localizationUtils.getLocalizedMessage(MessageKeys.ROLE_DOES_NOT_EXISTS));
        }
//        if(!optionalUser.get().isActive()) {
          if(!existingUser.isActive()){
            throw new DataNotFoundException(localizationUtils.getLocalizedMessage(MessageKeys.USER_IS_LOCKED));
        }
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                phoneNumber, password,
                existingUser.getAuthorities()
        );

        //authenticate with Java Spring security
        authenticationManager.authenticate(authenticationToken);
        return jwtTokenUtil.generateToken(existingUser);
    }
//    @Transactional
//    @Override
//    public UserEntity updateUser(Long userId, UpdateUserDTO updatedUserDTO) throws Exception {
//        // Find the existing user by userId
//        User existingUser = userRepository.findById(userId)
//                .orElseThrow(() -> new DataNotFoundException("User not found"));
//
//        // Check if the phone number is being changed and if it already exists for another user
//        String newPhoneNumber = updatedUserDTO.getPhoneNumber();
//        if (!existingUser.getPhoneNumber().equals(newPhoneNumber) &&
//                userRepository.existsByPhoneNumber(newPhoneNumber)) {
//            throw new DataIntegrityViolationException("Phone number already exists");
//        }
//
//        // Update user information based on the DTO
//        if (updatedUserDTO.getFullName() != null) {
//            existingUser.setFullName(updatedUserDTO.getFullName());
//        }
//        if (newPhoneNumber != null) {
//            existingUser.setPhoneNumber(newPhoneNumber);
//        }
//        if (updatedUserDTO.getAddress() != null) {
//            existingUser.setAddress(updatedUserDTO.getAddress());
//        }
//        if (updatedUserDTO.getDateOfBirth() != null) {
//            existingUser.setDateOfBirth(updatedUserDTO.getDateOfBirth());
//        }
//        if (updatedUserDTO.getFacebookAccountId() > 0) {
//            existingUser.setFacebookAccountId(updatedUserDTO.getFacebookAccountId());
//        }
//        if (updatedUserDTO.getGoogleAccountId() > 0) {
//            existingUser.setGoogleAccountId(updatedUserDTO.getGoogleAccountId());
//        }
//
//        // Update the password if it is provided in the DTO
//        if (updatedUserDTO.getPassword() != null
//                && !updatedUserDTO.getPassword().isEmpty()) {
//            if(!updatedUserDTO.getPassword().equals(updatedUserDTO.getRetypePassword())) {
//                throw new DataNotFoundException("Password and retype password not the same");
//            }
//            String newPassword = updatedUserDTO.getPassword();
//            String encodedPassword = passwordEncoder.encode(newPassword);
//            existingUser.setPassword(encodedPassword);
//        }
//        //existingUser.setRole(updatedRole);
//        // Save the updated user
//        return userRepository.save(existingUser);
//    }

    @Override
    public UserEntity getUserDetailsFromToken(String token) throws Exception {
        if(jwtTokenUtil.isTokenExpired(token)) {
            throw new Exception("Token is expired");
        }
        String phoneNumber = jwtTokenUtil.extractPhoneNumber(token);
        Optional<UserEntity> user = userRepository.findByPhoneNumber(phoneNumber);

        if (user.isPresent()) {
            return user.get();
        } else {
            throw new Exception("User not found");
        }
    }
}








