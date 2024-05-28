package com.cris.mannagg.model;

import com.cris.mannagg.DTO.UserDTO;
import com.cris.mannagg.entities.UserEntity;
import lombok.Data;

@Data
public class UserModel {

    private Long id;
    private String name;
    private String surname;
    private String email;
    private String profilePicture;

    public UserModel(Long id, String name, String surname, String email, String profilePicture) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.profilePicture = profilePicture;
    }

    public UserModel(String name, String surname, String email, String profilePicture) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.profilePicture = profilePicture;
    }

    public static UserDTO modelToDto(UserModel userModel) {
        return new UserDTO(userModel.getId(), userModel.getName(), userModel.getSurname(), userModel.getEmail(), userModel.getProfilePicture());
    }

    public static UserEntity modelToEntity(UserModel userModel) {
        return new UserEntity(userModel.getId(), userModel.getName(), userModel.getSurname(), userModel.getEmail(), userModel.getProfilePicture());
    }

    public static UserModel entityToModel(UserEntity userEntity) {
        return new UserModel(userEntity.getId(), userEntity.getName(), userEntity.getSurname(), userEntity.getEmail(), userEntity.getProfilePicture());
    }

    public static UserModel DTOtoModel(UserDTO userDTO) {
        return new UserModel((userDTO.getId()), userDTO.getName(), userDTO.getSurname(), userDTO.getEmail(), userDTO.getProfilePicture());
    }
}
