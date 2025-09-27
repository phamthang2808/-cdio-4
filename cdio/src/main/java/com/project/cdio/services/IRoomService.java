package com.project.cdio.services;

import com.project.cdio.entities.RoomEntity;
import com.project.cdio.exceptions.DataNotFoundException;
import com.project.cdio.models.RoomAllDTO;
import com.project.cdio.models.RoomDTO;
import com.project.cdio.request.RoomSearchRequest;
import com.project.cdio.responses.RoomDetailResponse;
import org.springframework.data.domain.Page;


import java.util.List;

public interface IRoomService {
    Page<RoomAllDTO> getAllRooms(int page, int limit);
    Page<RoomAllDTO> searchRooms(RoomSearchRequest roomSearchRequest);
    RoomEntity createRoom(RoomDTO roomDTO);
    List<RoomDTO> getRoomsByStaff(Long staffId);
    RoomDetailResponse getRoomById(Long RoomId) throws DataNotFoundException;
    RoomEntity updateRoom(long id, RoomDTO roomDTO);
    void deleteRoom(long id);
}
