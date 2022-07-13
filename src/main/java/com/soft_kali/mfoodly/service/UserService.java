package com.rupesh_mandal.blog_app_backend.services;

import com.rupesh_mandal.blog_app_backend.payloads.UserDto;

import java.util.List;

public interface UserService {

    UserDto registerNewUser(UserDto userDto);
    UserDto addUser(UserDto userDto);
    UserDto updateUser(UserDto userDto, int userId);
    UserDto getUserById(int userId);
    void deleteUserById(int userId);
    List<UserDto> getAllUser();


}
