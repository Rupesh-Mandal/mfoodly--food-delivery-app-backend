package com.soft_kali.mfoodly.controller;



import com.soft_kali.mfoodly.model.ApiResponse;
import com.soft_kali.mfoodly.dto.user.UserDto;
import com.soft_kali.mfoodly.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;


    @PostMapping("/")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
        UserDto createdUserDto=userService.addUser(userDto);
        return new ResponseEntity<>(createdUserDto, HttpStatus.CREATED);
    }


    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable Long userId){
        UserDto updatedUserDto=userService.updateUser(userDto,userId);
        return new ResponseEntity<>(updatedUserDto, HttpStatus.OK);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long userId){
        userService.deleteUserById(userId);
        return new ResponseEntity<>(new ApiResponse("User deleted Succefully",true), HttpStatus.OK);
    }


    @GetMapping("/")
    public ResponseEntity<List<UserDto>> getAllUser(){
        return new ResponseEntity<>(userService.getAllUser(), HttpStatus.OK);
    }


    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long userId){
        return new ResponseEntity<>(userService.getUserById(userId), HttpStatus.OK);
    }



}
