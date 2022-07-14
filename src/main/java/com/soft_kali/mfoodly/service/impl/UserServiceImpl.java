package com.soft_kali.mfoodly.service.impl;


import com.soft_kali.mfoodly.dto.UserDto;
import com.soft_kali.mfoodly.entity.Role;
import com.soft_kali.mfoodly.entity.UserEntity;
import com.soft_kali.mfoodly.entity.location.CityEntity;
import com.soft_kali.mfoodly.exeptions.ResourceNotFountException;
import com.soft_kali.mfoodly.repository.RoleRepository;
import com.soft_kali.mfoodly.repository.UserRepository;
import com.soft_kali.mfoodly.repository.location.CityRepository;
import com.soft_kali.mfoodly.service.UserService;
import com.soft_kali.mfoodly.utils.AppConstants;
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

    @Autowired
    CityRepository cityRepository;
    @Override
    public UserDto registerNewUser(UserDto userDto,int cityId) {
        CityEntity cityEntity=cityRepository.findById(cityId).orElseThrow(()-> new ResourceNotFountException("City","id",cityId));
        UserEntity userEntity=modelMapper.map(userDto,UserEntity.class);
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));

        Role role=roleRepository.findById(AppConstants.ROLE_CUSTOMER).get();

        List<Role> roleList=new ArrayList<>();
        roleList.add(role);

        userEntity.setRoles(roleList);
        userEntity.setCityEntity(cityEntity);
        UserEntity savedUser=userRepository.save(userEntity);

        return convetToUserDto(savedUser);
    }

    @Override
    public UserDto registerNewOutletUser(UserDto userDto, int cityId) {
        CityEntity cityEntity=cityRepository.findById(cityId).orElseThrow(()-> new ResourceNotFountException("City","id",cityId));
        UserEntity userEntity=modelMapper.map(userDto,UserEntity.class);
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));

        Role role=roleRepository.findById(AppConstants.ROLE_OUTLET).get();

        List<Role> roleList=new ArrayList<>();
        roleList.add(role);

        userEntity.setRoles(roleList);
        userEntity.setCityEntity(cityEntity);
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
    public UserDto updateUser(UserDto userDto, Long userId) {
        UserEntity userEntity=userRepository.findById(userId).orElseThrow(()-> new ResourceNotFountException("User","id",userId));

        userEntity.setName(userDto.getName());
        userEntity.setPhoneNumber(userDto.getPhoneNumber());
        userEntity.setPassword(userDto.getPassword());

        UserEntity updatedUser=userRepository.save(userEntity);
        return convetToUserDto(updatedUser);
    }

    @Override
    public UserDto getUserById(Long userId) {
        UserEntity userEntity=userRepository.findById(userId).orElseThrow(()-> new ResourceNotFountException("User","id",userId));
        return convetToUserDto(userEntity);
    }

    @Override
    public void deleteUserById(Long userId) {
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
        System.out.println(userDto);
        UserEntity userEntity=modelMapper.map(userDto,UserEntity.class);
        return userEntity;
    }


    UserDto convetToUserDto(UserEntity userEntity){
        System.out.println(userEntity);
        UserDto userDto=modelMapper.map(userEntity,UserDto.class);
        return userDto;
    }

}
