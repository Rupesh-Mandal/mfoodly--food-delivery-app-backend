package com.soft_kali.mfoodly.service;


import com.soft_kali.mfoodly.dto.user.UserDto;
import com.soft_kali.mfoodly.entity.user.UserEntity;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UserService {

    UserDto registerNewUser(UserDto userDto,int cityId);
    UserDto registerNewOutletUser(UserDto userDto,int cityId);
    UserDto addUser(UserDto userDto);
    UserDto updateUser(UserDto userDto, Long userId);
    UserDto getUserById(Long userId);
    void deleteUserById(Long userId);
    List<UserDto> getAllUser();


    UserEntity findByPhoneNumber(String phoneNumber);
}
