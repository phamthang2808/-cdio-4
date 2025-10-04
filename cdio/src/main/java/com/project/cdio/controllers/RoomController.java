package com.project.cdio.controllers;

import com.project.cdio.components.LocalizationUtils;
import com.project.cdio.exceptions.DataNotFoundException;
import com.project.cdio.models.dto.RoomAllDTO;
import com.project.cdio.models.request.RoomSearchRequest;
import com.project.cdio.models.responses.RoomDetailResponse;
import com.project.cdio.services.impl.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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



}
