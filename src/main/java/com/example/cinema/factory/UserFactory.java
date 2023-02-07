package com.example.cinema.factory;

import com.example.cinema.dto.UserDTO;
import com.example.cinema.entity.UserDocument;

public interface UserFactory {

    default UserDTO buildUser(UserDocument userDocument){
        return UserDTO.builder()
                .id(userDocument.getId())
                .name(userDocument.getName())
                .lastname(userDocument.getLastname())
                .build();
    }

    default UserDocument editBuildUser(UserDTO userDTO, UserDTO userDB){
        return UserDocument.builder()
                .id(userDB.getId())
                .name(userDTO.getName())
                .lastname(userDTO.getLastname())
                .build();
    }

    default UserDocument saveBuildUser(UserDTO userDTO){
        return UserDocument.builder()
                .name(userDTO.getName())
                .lastname(userDTO.getLastname())
                .build();
    }
}
