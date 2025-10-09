package com.project.cdio.services.impl;

import com.project.cdio.config.ModelMapperConfig;
import com.project.cdio.entities.RoomTypeEntity;
import com.project.cdio.models.dto.RoomTypeDTO;
import com.project.cdio.repositories.RoomTypeRepository;
import com.project.cdio.services.IRoomTypeService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoomTypeService implements IRoomTypeService {


    private final ModelMapper modelMapper;
    private final RoomTypeRepository roomTypeRepository;

    @Override
    public RoomTypeDTO createRoomType(RoomTypeDTO roomTypeDTO) {
        RoomTypeEntity  roomTypeEntity = modelMapper.map(roomTypeDTO, RoomTypeEntity.class);
        RoomTypeEntity roomType = roomTypeRepository.save(roomTypeEntity);
        return modelMapper.map(roomType, RoomTypeDTO.class);
    }
}
