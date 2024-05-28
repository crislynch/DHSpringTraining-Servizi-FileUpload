package com.cris.mannagg.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequest {

    private Long id;
    private String name;
    private String surname;
    private String email;
    private String profilePicture;

}
