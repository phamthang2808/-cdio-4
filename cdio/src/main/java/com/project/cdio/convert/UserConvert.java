package com.project.cdio.convert;

import com.project.cdio.entities.UserEntity;
import com.project.cdio.models.UserDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserConvert {

    private final ModelMapper modelMapper;

    public UserEntity convertToEntity (UserDTO userDTO){
        UserEntity userEntity = modelMapper.map(userDTO, UserEntity.class);
        BeanUtils.copyProperties(userDTO,userEntity,"retypePassword");
        return userEntity;
    }

    public UserDTO convertToDto (UserEntity user){
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        return userDTO;
    }
}
