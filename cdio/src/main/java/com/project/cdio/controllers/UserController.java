package com.project.cdio.controllers;

import com.project.cdio.components.LocalizationUtils;
import com.project.cdio.entities.CustomerEntity;
import com.project.cdio.entities.UserEntity;
import com.project.cdio.exceptions.DataNotFoundException;
import com.project.cdio.models.CustomerDTO;
import com.project.cdio.models.RoomAllDTO;
import com.project.cdio.models.UserDTO;
import com.project.cdio.models.UserLoginDTO;
import com.project.cdio.request.StaffCreateRequest;
import com.project.cdio.request.UserUpdateRequest;
import com.project.cdio.responses.*;
import com.project.cdio.services.impl.UserService;
import com.project.cdio.utils.MessageKeys;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("${api.prefix}/users")
@RequiredArgsConstructor

public class UserController {

    public final UserService userService;
    public final LocalizationUtils localizationUtils;

    @PostMapping("/register")
    //can we register an "admin" user ?
    public ResponseEntity<RegisterResponse> createUser(
            @Valid @RequestBody UserDTO userDTO,
            BindingResult result
    ) {
        RegisterResponse registerResponse = new RegisterResponse();
        if (result.hasErrors()) {
            List<String> errorMessages = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();

            registerResponse.setMessage(errorMessages.toString());
            return ResponseEntity.badRequest().body(registerResponse);
        }

        if (!userDTO.getPassword().equals(userDTO.getRetypePassword())) {
            registerResponse.setMessage(localizationUtils.getLocalizedMessage(MessageKeys.PASSWORD_NOT_MATCH));
            return ResponseEntity.badRequest().body(registerResponse);
        }

        try {
            UserEntity user = userService.createUser(userDTO);
            registerResponse.setMessage(localizationUtils.getLocalizedMessage(MessageKeys.REGISTER_SUCCESSFULLY));
            registerResponse.setUser(user);
            return ResponseEntity.ok(registerResponse);
        } catch (Exception e) {
            registerResponse.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(registerResponse);
        }
    }


    @PostMapping("/login")
    public ResponseEntity<LoginResponse> LoginUser(
            @Valid @RequestBody UserLoginDTO userLoginDTO
    ) {
        try {
            String token = userService.login(
                    userLoginDTO.getPhoneNumber(),
                    userLoginDTO.getPassword()
//                    userLoginDTO.getRoleId() == null ? 1 : userLoginDTO.getRoleId()
            );
            // Trả về token trong response
            return ResponseEntity.ok(LoginResponse.builder()
                    .message(localizationUtils.getLocalizedMessage(MessageKeys.LOGIN_SUCCESSFULLY))
                    .token(token)
                    .build());
        } catch (BadCredentialsException | DataNotFoundException e) {
            return ResponseEntity.badRequest().body(
                    LoginResponse.builder()
                            .message(e.getMessage())
                            .build()
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    LoginResponse.builder()
                            .message(localizationUtils.getLocalizedMessage(MessageKeys.LOGIN_FAILED, e.getMessage()))
                            .build()
            );
        }

    }

    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createStaff(
            @Valid @ModelAttribute StaffCreateRequest staffCreateRequest,
            BindingResult result
    ) throws IOException {
        StaffResponse staffResponse = new StaffResponse();
        if(result.hasErrors()){
            List<String> errorMessage = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            staffResponse.setMessage(localizationUtils.getLocalizedMessage(MessageKeys.INSERT_STAFF_FAILED));
            staffResponse.setErrors(errorMessage);
            return ResponseEntity.badRequest().body(staffResponse);
        }
        List<MultipartFile> files = staffCreateRequest.getFiles();
        files = files == null ? new ArrayList<MultipartFile>() : files;
        for (MultipartFile file : files){
            if(file.getSize() == 0){
                continue;
            }
            // Kiểm tra kích thước file và định dạng
            if(file.getSize() > 10 * 1024 * 1024){
                return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE)
                        .body("file is too large! Maximum size is 10MB");
            }
            String contenType = file.getContentType();
            if(contenType == null || !contenType.startsWith("image/")){
                return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                        .body("File must be an image");
            }
            // Lưu file và cập nhật thumbnail trong DTO
            String filename = storeFile(file); // Thay thế hàm này với code của bạn để lưu file
            //lưu vào đối tượng room trong DB => sẽ làm sau
            String imagePath = "/uploads/" + filename;
            staffCreateRequest.setImg(imagePath);
        }
        UserEntity userEntity = userService.createStaff(staffCreateRequest);
        staffResponse.setMessage(localizationUtils.getLocalizedMessage(MessageKeys.INSERT_STAFF_SUCCESSFULLY));
        staffResponse.setUserEntity(userEntity);
        return ResponseEntity.ok(staffResponse);
    }


    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateUser(
            @PathVariable Long id ,
            @ModelAttribute UserUpdateRequest userDTO
    ) throws DataNotFoundException, IOException {
        List<MultipartFile> files = userDTO.getFiles();
        files = files == null ? new ArrayList<MultipartFile>() : files;
        for (MultipartFile file : files){
            if(file.getSize() == 0){
                continue;
            }
            // Kiểm tra kích thước file và định dạng
            if(file.getSize() > 10 * 1024 * 1024){
                return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE)
                        .body("file is too large! Maximum size is 10MB");
            }
            String contenType = file.getContentType();
            if(contenType == null || !contenType.startsWith("image/")){
                return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                        .body("File must be an image");
            }
            // Lưu file và cập nhật thumbnail trong DTO
            String filename = storeFile(file); // Thay thế hàm này với code của bạn để lưu file
            //lưu vào đối tượng room trong DB => sẽ làm sau
            String imagePath = "/uploads/" + filename;
            userDTO.setImg(imagePath);
            //lưu vào bảng room_images
        }
        userService.updateUser(id, userDTO);
        return ResponseEntity.ok("update sucessfully");
    }


    private String storeFile(MultipartFile file) throws IOException {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        // Thêm UUID vào trước tên file để đảm bảo tên file là duy nhất
        String uniqueFilename = UUID.randomUUID().toString() + "_" + filename.toLowerCase();
        // Đường dẫn đến thư mục mà bạn muốn lưu file
        Path uploadDir = Paths.get("uploads");
        // Kiểm tra và tạo thư mục nếu nó không tồn tại
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }
        // Đường dẫn đầy đủ đến file
        Path destination = Paths.get(uploadDir.toString(), uniqueFilename);
        // Sao chép file vào thư mục đích
        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
        return uniqueFilename;
    }

    @GetMapping("/admin/manage-staff")
    public ResponseEntity<List<AllStaffResponse>> getAllStaffs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit
    ) {
        Page<AllStaffResponse> p = userService.getAllStaffs(page, limit);
        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(p.getTotalElements()))
                .header("X-Total-Pages", String.valueOf(p.getTotalPages()))
                .header("X-Page", String.valueOf(page))
                .header("X-Size", String.valueOf(limit))
                .body(p.getContent());              // chỉ trả danh sách
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(
            @PathVariable("id") Long UserId
    ) throws DataNotFoundException {
        try {
            UserDTO existingUser = userService.getUserById(UserId);
            return ResponseEntity.ok(existingUser);
        }catch (DataNotFoundException ex){
            return ResponseEntity.badRequest().body("Not find user with id = "+ UserId);
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(
            @PathVariable Long id
    ){
        userService.deleteUser(id);
        return ResponseEntity.ok("delete sucessfully");
    }


}
