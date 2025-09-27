package com.project.cdio.services.impl;

import ch.qos.logback.core.util.Loader;
import com.project.cdio.components.LocalizationUtils;
import com.project.cdio.convert.RoomConvert;
import com.project.cdio.entities.RoomEntity;
import com.project.cdio.exceptions.DataNotFoundException;
import com.project.cdio.models.RoomAllDTO;
import com.project.cdio.models.RoomDTO;
import com.project.cdio.repositories.ManagementRoomRepository;
import com.project.cdio.repositories.RoomRepository;
import com.project.cdio.request.RoomSearchRequest;
import com.project.cdio.responses.RoomDetailResponse;
import com.project.cdio.services.IRoomService;
import com.project.cdio.utils.MessageKeys;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class RoomService implements IRoomService {

    private final RoomRepository roomRepository;
    private final RoomConvert roomConvert;
    private final ManagementRoomRepository managementRoomRepository;
    private final LocalizationUtils localizationUtils;

    @Override
    public Page<RoomAllDTO> getAllRooms(int page, int limit) {
        Pageable pageable = PageRequest.of(page, limit/*, Sort.by("roomId").desc()*/);
        Page<RoomEntity> data = roomRepository.findAll(pageable);
        return data.map(r -> new RoomAllDTO(
                r.getRoomId(), r.getTitle(), r.getImage(), r.getAddress(),
                r.getDescription(), r.getGuests(), r.getSize(), r.getBeds(),
                r.getView(), r.getPrice(), r.getOldPrice(), r.getDiscount(),
                Boolean.TRUE.equals(r.getAirConditioning()),
                Boolean.TRUE.equals(r.getWifi()),
                Boolean.TRUE.equals(r.getHairDryer()),
                Boolean.TRUE.equals(r.getPetsAllowed()),
                Boolean.TRUE.equals(r.getNonSmoking())
        ));
    }



    @Override
    public Page<RoomAllDTO> searchRooms(RoomSearchRequest r) {
        Pageable pageable = PageRequest.of(r.getPage(), r.getLimit(), Sort.by("roomId").descending());
        Page<RoomEntity> data = roomRepository.searchRooms(r.getGuests(), r.getMinPrice(), r.getMaxPrice(), pageable);

//        return data.map(roomConvert::convertToDto);
        return data.map(searchRoom -> new RoomAllDTO(
                searchRoom.getRoomId(), searchRoom.getTitle(), searchRoom.getImage(), searchRoom.getAddress(),
                searchRoom.getDescription(), searchRoom.getGuests(), searchRoom.getSize(), searchRoom.getBeds(),
                searchRoom.getView(), searchRoom.getPrice(), searchRoom.getOldPrice(), searchRoom.getDiscount(),
                Boolean.TRUE.equals(searchRoom.getAirConditioning()),
                Boolean.TRUE.equals(searchRoom.getWifi()),
                Boolean.TRUE.equals(searchRoom.getHairDryer()),
                Boolean.TRUE.equals(searchRoom.getPetsAllowed()),
                Boolean.TRUE.equals(searchRoom.getNonSmoking())
        ));
    }

    @Override
    @Transactional
    public RoomEntity createRoom(RoomDTO roomDTO) {
        RoomEntity newRoom = roomConvert.convertToEntity(roomDTO);
        return roomRepository.save(newRoom);
    }

    @Override
    public List<RoomDTO> getRoomsByStaff(Long staffId) {
        //lay roomid ma staff quan ly
        List<Long> roomIds = managementRoomRepository.findByStaff_UserId(staffId)
                .stream()
                .map(mr -> mr.getRoom().getRoomId())
                .toList();

        //lay ra cac room
        List<RoomEntity> rooms = roomRepository.findAllById(roomIds);

        return rooms.stream()
                .map(roomConvert::convertToDto)
                .toList();
    }

    @Override
    public RoomDetailResponse getRoomById(Long roomId) throws DataNotFoundException {
        Optional<RoomEntity> optionnalRoom = roomRepository.findByRoomId(roomId);
        if(optionnalRoom.isEmpty()){
            throw new DataNotFoundException(localizationUtils.getLocalizedMessage(MessageKeys.FIND_ROOM_FAILED));
        }
        RoomEntity existingRoom = optionnalRoom.get();

        return roomConvert.convertToDtoResponseDetail(existingRoom);
    }

    @Override
    @Transactional
    public RoomEntity updateRoom(long id, RoomDTO roomDTO) {
        Optional<RoomEntity> optionalRoom = roomRepository.findByRoomId(id);
        RoomEntity existingRoom = optionalRoom.get();
      if(existingRoom != null){
          RoomEntity roomUpdateEntity = roomConvert.updateToEntityFromDTO(roomDTO, existingRoom, id);
          return roomRepository.save(roomUpdateEntity);
      }
       return null;
    }

    @Override
    @Transactional
    public void deleteRoom(long id) {
        roomRepository.deleteById(id);
    }
}
