package com.cris.mannagg.DTO;

import com.cris.mannagg.entities.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DownloadProfilePictureDTO {

    private UserEntity userEntity;

    private byte[] profileImage;
}
