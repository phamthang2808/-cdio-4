package com.project.cdio.controllers;

import com.project.cdio.components.LocalizationUtils;
import com.project.cdio.entities.UserEntity;
import com.project.cdio.request.StaffCreateRequest;
import com.project.cdio.responses.AllStaffResponse;
import com.project.cdio.responses.StaffResponse;
import com.project.cdio.services.impl.UserService;
import com.project.cdio.utils.MessageKeys;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("${api.prefix}/admins")
@RequiredArgsConstructor
public class AdminController {

    public final UserService userService;
    public final LocalizationUtils localizationUtils;

    @GetMapping("/users/all-staff")
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

    @PostMapping(value = "/users", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
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


}
