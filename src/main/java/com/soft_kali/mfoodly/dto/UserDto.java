package com.soft_kali.mfoodly.dto;

import com.soft_kali.mfoodly.dto.location.CityDto;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    @Valid
    private Long userId;

    @NotEmpty()
    @Size(min = 4,message = "Username must be min 4 charactes !!")
    private String name;

    @Size(min = 10, max = 10, message = "phone number must be 10 characters !!")
    private String phoneNumber;

    @NotEmpty
    @Size(min = 4,max = 10,message = "password must be min 4 charactes and max 10 characters !!")
    private String password;


    private List<RoleDto> roles;
    private CityDto cityEntity;

}
