package com.soft_kali.mfoodly.jwt;

import com.soft_kali.mfoodly.entity.UserEntity;
import com.soft_kali.mfoodly.exeptions.ResourceNotFountException;
import com.soft_kali.mfoodly.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        System.out.println(username);
        UserEntity userEntity=userRepository.findByPhoneNumber(username).orElseThrow(()-> new ResourceNotFountException("User" ,"email",username));


        return userEntity;
    }
}
