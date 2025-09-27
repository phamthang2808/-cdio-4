package com.project.cdio.controllers;

import com.project.cdio.components.LocalizationUtils;
import com.project.cdio.entities.RoomEntity;
import com.project.cdio.entities.UserEntity;
import com.project.cdio.exceptions.DataNotFoundException;
import com.project.cdio.models.RoomAllDTO;
import com.project.cdio.models.RoomDTO;
import com.project.cdio.request.RoomSearchRequest;
import com.project.cdio.responses.RoomDetailResponse;
import com.project.cdio.responses.RoomResponse;
import com.project.cdio.responses.UpdateRoomResponse;
import com.project.cdio.services.impl.RoomService;
import com.project.cdio.utils.MessageKeys;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("${api.prefix}/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;
    private final LocalizationUtils localizationUtils;

    @GetMapping("/search")
    public ResponseEntity<List<RoomAllDTO>> searchRooms(
         @ModelAttribute RoomSearchRequest roomSearchRequest
    ) {
        roomSearchRequest.normalizeDefaults();  //set default cho page
        Page<RoomAllDTO> p = roomService.searchRooms(roomSearchRequest);
        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(p.getTotalElements()))
                .header("X-Total-Pages", String.valueOf(p.getTotalPages()))
                .header("X-Page", String.valueOf(roomSearchRequest.getPage()))
                .header("X-Size", String.valueOf(roomSearchRequest.getLimit()))
                .body(p.getContent());              // chỉ trả danh sách

    }

    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createRoom(
            @Valid @ModelAttribute RoomDTO roomDTO,
            BindingResult result

    ) throws IOException {
        RoomResponse roomResponse = new RoomResponse();
        if(result.hasErrors()){
            List<String> errorMessages = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            roomResponse.setMessage(localizationUtils.getLocalizedMessage(MessageKeys.INSERT_ROOM_FAILED));
            roomResponse.setErrors(errorMessages);
            return ResponseEntity.badRequest().body(roomResponse);
        }
        List<MultipartFile> files = roomDTO.getFiles();
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
            roomDTO.setImage(imagePath);
            //lưu vào bảng room_images
        }
        RoomEntity room = roomService.createRoom(roomDTO);
        roomResponse.setMessage(localizationUtils.getLocalizedMessage(MessageKeys.INSERT_ROOM_SUCCESSFULLY));
        roomResponse.setRoomDTO(room);
        return ResponseEntity.ok(roomResponse);
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

    @GetMapping("")
    public ResponseEntity<List<RoomAllDTO>> getAllRooms(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit
    ) {
        Page<RoomAllDTO> p = roomService.getAllRooms(page, limit);
        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(p.getTotalElements()))
                .header("X-Total-Pages", String.valueOf(p.getTotalPages()))
                .header("X-Page", String.valueOf(page))
                .header("X-Size", String.valueOf(limit))
                .body(p.getContent());              // chỉ trả danh sách
    }


    @GetMapping("/staff")
    public ResponseEntity<List<RoomDTO>> getMyRooms(Authentication authentication) {
        UserEntity userDetails = (UserEntity) authentication.getPrincipal();
        Long staffId = userDetails.getUserId();

        List<RoomDTO> rooms = roomService.getRoomsByStaff(staffId);
        return ResponseEntity.ok(rooms);
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getRoomById(
            @PathVariable("id") Long roomId
    ) throws DataNotFoundException {
        try {
            RoomDetailResponse existingRoom = roomService.getRoomById(roomId);
            return ResponseEntity.ok(existingRoom);
        }catch (DataNotFoundException ex){
            return ResponseEntity.badRequest().body("Not find room with id = "+ roomId);
        }

    }

    @PutMapping(value = "{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateRoom(
             @PathVariable Long id,
             @Valid @ModelAttribute RoomDTO roomDTO
    ) throws IOException {
        List<MultipartFile> files = roomDTO.getFiles();
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
            String filename = null; // Thay thế hàm này với code của bạn để lưu file
            try {
                filename = storeFile(file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            //lưu vào đối tượng room trong DB => sẽ làm sau
            String imagePath = "/uploads/" + filename;
            roomDTO.setImage(imagePath);
            //lưu vào bảng room_images
        }
        UpdateRoomResponse updateRoomResponse = new UpdateRoomResponse();
        roomService.updateRoom( id, roomDTO);
//        updateRoomResponse.setMessage("update room successfully");
//        return ResponseEntity.ok(updateRoomResponse);
        return ResponseEntity.ok("update room successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRoom(
            @PathVariable Long id
    ){
        roomService.deleteRoom(id);
        return ResponseEntity.ok("delete sucessfully");
    }

}
