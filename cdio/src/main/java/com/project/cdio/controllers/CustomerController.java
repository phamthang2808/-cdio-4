package com.project.cdio.controllers;

import com.project.cdio.components.LocalizationUtils;
import com.project.cdio.entities.CustomerEntity;
import com.project.cdio.entities.RoomEntity;
import com.project.cdio.entities.UserEntity;
import com.project.cdio.exceptions.DataNotFoundException;
import com.project.cdio.models.CustomerDTO;
import com.project.cdio.models.RoomDTO;
import com.project.cdio.responses.CustomerResponse;
import com.project.cdio.responses.RoomDetailResponse;
import com.project.cdio.responses.UpdateCustomerResponse;
import com.project.cdio.responses.UpdateRoomResponse;
import com.project.cdio.services.impl.CustomerService;
import com.project.cdio.utils.MessageKeys;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
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
@RequestMapping("${api.prefix}/customers")
@RequiredArgsConstructor
public class CustomerController {
ádfasdfasdefdsdffed
    private final LocalizationUtils localizationUtils;
    private final CustomerService customerService;

    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createCustomer(
            @Valid @ModelAttribute CustomerDTO customerDTO,
            BindingResult result
            ) throws IOException {
        CustomerResponse customerResponse = new CustomerResponse();
        if(result.hasErrors()){
            List<String> errorMessage = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            customerResponse.setMessage(localizationUtils.getLocalizedMessage(MessageKeys.INSERT_CUSTOMER_FAILED));
            customerResponse.setErrors(errorMessage);
            return ResponseEntity.badRequest().body(customerResponse);
        }
        List<MultipartFile> files = customerDTO.getFiles();
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
            customerDTO.setImg(imagePath);
            //lưu vào bảng room_images
        }
        CustomerEntity customer = customerService.createCustomer(customerDTO);
        customerResponse.setMessage(localizationUtils.getLocalizedMessage(MessageKeys.INSERT_CUSTOMER_SUCCESSFULLY));
        customerResponse.setCustomerEntity(customer);
        return ResponseEntity.ok(customerResponse);
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

    @GetMapping("/staff")
    public ResponseEntity<List<CustomerDTO>> getMyCustomers(Authentication authentication) {
        UserEntity userDetails = (UserEntity) authentication.getPrincipal();
        Long staffId = userDetails.getUserId();

        List<CustomerDTO> customers = customerService.getCustomersByStaff(staffId);
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCustomerById(
            @PathVariable("id") Long customerId
    ) throws DataNotFoundException {
        try {
            CustomerDTO existingCustomer = customerService.getCustomerById(customerId);
            return ResponseEntity.ok(existingCustomer);
        }catch (DataNotFoundException ex){
            return ResponseEntity.badRequest().body("Not find customer with id = "+ customerId);
        }

    }

    @PutMapping(value = "{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateCustomer(
            @PathVariable Long id,
            @Valid @ModelAttribute CustomerDTO customerDTO,
            BindingResult result
    ) throws IOException {
        List<MultipartFile> files = customerDTO.getFiles();
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
            customerDTO.setImg(imagePath);
            //lưu vào bảng room_images
        }
        UpdateCustomerResponse updateCustomerResponse = new UpdateCustomerResponse();
        customerService.updateCustomer( id, customerDTO);
        updateCustomerResponse.setMessage("update customer successfully");
        return ResponseEntity.ok(updateCustomerResponse);
    }

    @PutMapping("/active/{id}")
    public ResponseEntity<UpdateCustomerResponse> updateCustomerActive(
            @PathVariable Long id,
            @Valid @RequestParam("active") Long active
    ){
        UpdateCustomerResponse updateCustomerResponse = new UpdateCustomerResponse();
        customerService.updateCustomerActive(id,active);
        updateCustomerResponse.setMessage("update customer active successfully");
        return ResponseEntity.ok(updateCustomerResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCustomer(
            @PathVariable Long id
    ){
        customerService.deleteCustomer(id);
        return ResponseEntity.ok("delete sucessfully");
    }

}
