package com.rupesh_mandal.blog_app_backend.services.impl;

import com.rupesh_mandal.blog_app_backend.config.AppConstants;
import com.rupesh_mandal.blog_app_backend.entity.Role;
import com.rupesh_mandal.blog_app_backend.entity.UserEntity;
import com.rupesh_mandal.blog_app_backend.exeptions.ResourceNotFountException;
import com.rupesh_mandal.blog_app_backend.payloads.UserDto;
import com.rupesh_mandal.blog_app_backend.repository.RoleRepository;
import com.rupesh_mandal.blog_app_backend.repository.UserRepository;
import com.rupesh_mandal.blog_app_backend.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;
    @Override
    public UserDto registerNewUser(UserDto userDto) {
        UserEntity userEntity=modelMapper.map(userDto,UserEntity.class);
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));

        Role role=roleRepository.findById(AppConstants.ROLE_NORMAL).get();

        List<Role> roleList=new ArrayList<>();
        roleList.add(role);

        userEntity.setRoles(roleList);
        UserEntity savedUser=userRepository.save(userEntity);

        return convetToUserDto(savedUser);
    }

    @Override
    public UserDto addUser(UserDto userDto) {
        UserEntity userEntity=convetToUserEntity(userDto);
        UserEntity savedUser=userRepository.save(userEntity);
        return convetToUserDto(savedUser);
    }

    @Override
    public UserDto updateUser(UserDto userDto, int userId) {
        UserEntity userEntity=userRepository.findById(userId).orElseThrow(()-> new ResourceNotFountException("User","id",userId));

        userEntity.setName(userDto.getName());
        userEntity.setEmail(userDto.getEmail());
        userEntity.setPassword(userDto.getPassword());
        userEntity.setAbout(userDto.getAbout());

        UserEntity updatedUser=userRepository.save(userEntity);
        return convetToUserDto(updatedUser);
    }

    @Override
    public UserDto getUserById(int userId) {
        UserEntity userEntity=userRepository.findById(userId).orElseThrow(()-> new ResourceNotFountException("User","id",userId));
        return convetToUserDto(userEntity);
    }

    @Override
    public void deleteUserById(int userId) {
        UserEntity userEntity=userRepository.findById(userId).orElseThrow(()-> new ResourceNotFountException("User","id",userId));
        userRepository.delete(userEntity);
    }

    @Override
    public List<UserDto> getAllUser() {
        List<UserEntity> userEntityList=userRepository.findAll();

        List<UserDto> userDtoList =userEntityList.stream().map(userEntity -> convetToUserDto(userEntity)).collect(Collectors.toList());

        return userDtoList;
    }


    UserEntity convetToUserEntity(UserDto userDto){
        UserEntity userEntity=modelMapper.map(userDto,UserEntity.class);
        return userEntity;
    }


    UserDto convetToUserDto(UserEntity userEntity){
        UserDto userDto=modelMapper.map(userEntity,UserDto.class);
        return userDto;
    }

}
