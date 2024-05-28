package com.cris.mannagg.services;

import com.cris.mannagg.DTO.CreateUserRequest;
import com.cris.mannagg.DTO.DownloadProfilePictureDTO;
import com.cris.mannagg.DTO.UpdateUserRequest;
import com.cris.mannagg.DTO.UserDTO;
import com.cris.mannagg.entities.UserEntity;
import com.cris.mannagg.model.UserModel;
import com.cris.mannagg.repositories.UserRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    FileStorageService fileStorageService;

    public UserDTO createUser(CreateUserRequest createUserRequest) {
        UserModel userModel = new UserModel(createUserRequest.getName(), createUserRequest.getSurname(), createUserRequest.getEmail(), createUserRequest.getProfilePicture());
        UserModel anotherUserModel = UserModel.entityToModel(userRepository.save(UserModel.modelToEntity(userModel)));
        return UserModel.modelToDto(anotherUserModel);
    }

    public UserDTO getSingleUser(long userId) {
        Optional<UserEntity> result = userRepository.findById(userId);
        if (result.isPresent()) {
            UserModel userModel = UserModel.entityToModel(result.get());
            return UserModel.modelToDto(userModel);
        } else {
            return null;
        }
    }

    public List<UserDTO> getAllUsers() {
        List<UserEntity> userEntities = userRepository.findAll();
        ArrayList<UserDTO> userDTOs = new ArrayList<>();
        for (UserEntity userEntity : userEntities) {
            UserModel userModel = UserModel.entityToModel(userEntity);
            UserDTO userDTO = UserModel.modelToDto(userModel);
            userDTOs.add(userDTO);
        }
        return userDTOs;
    }


    public UserDTO updateUser(long userId, UpdateUserRequest updateUserRequest) {
        Optional<UserEntity> result = userRepository.findById(userId);
        if (result.isPresent()) {
            result.get().setName(updateUserRequest.getName() == null ? result.get().getName() : updateUserRequest.getName());
            result.get().setSurname(updateUserRequest.getSurname() == null ? result.get().getSurname() : updateUserRequest.getSurname());
            result.get().setEmail(updateUserRequest.getEmail() == null ? result.get().getEmail() : updateUserRequest.getEmail());
            result.get().setProfilePicture(updateUserRequest.getProfilePicture() == null ? result.get().getProfilePicture() : updateUserRequest.getProfilePicture());
            UserEntity savedUser = userRepository.saveAndFlush(result.get());
            UserModel savedUserModel = UserModel.entityToModel(savedUser);
            return UserModel.modelToDto(savedUserModel);
        }
        return null;
    }

    public boolean deleteUser(long id) {
        Optional<UserEntity> result = userRepository.findById(id);
        if (result.isPresent()) {
            try {
                userRepository.delete(result.get());
                return true;
            } catch (Exception e) {
                return false;
            }
        } else {
            return false;
        }
    }

    public void uploadProfilePicture(Long userId, MultipartFile profilePicture) throws Exception {
        Optional<UserEntity> result = userRepository.findById(userId);
        if (result.isPresent()) {
            if (!profilePicture.isEmpty()) {
                fileStorageService.remove(String.valueOf(profilePicture));
                String fileName = fileStorageService.upload(profilePicture);
                result.get().setProfilePicture(fileName);
                UserEntity savedUser = userRepository.saveAndFlush(result.get());
                UserModel savedUserModel = UserModel.entityToModel(savedUser);
                UserModel.modelToDto(savedUserModel);
            }
        }
    }

    @SneakyThrows
    public DownloadProfilePictureDTO downloadProfilePicture(Long userId) {
        UserDTO userDTO = getSingleUser(userId);
        UserModel savedUserModel = UserModel.DTOtoModel(userDTO);
        UserEntity savedUser = UserModel.modelToEntity(savedUserModel);
        DownloadProfilePictureDTO dto = new DownloadProfilePictureDTO();
        dto.setUserEntity(savedUser);
        if (userDTO.getProfilePicture() == null) return dto;
        byte[] profilePictureBytes = fileStorageService.download(userDTO.getProfilePicture());
        dto.setProfileImage(profilePictureBytes);
        return dto;
    }
}