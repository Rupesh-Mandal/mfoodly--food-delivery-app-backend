package com.rupesh_mandal.blog_app_backend.security;

import com.rupesh_mandal.blog_app_backend.entity.UserEntity;
import com.rupesh_mandal.blog_app_backend.exeptions.ResourceNotFountException;
import com.rupesh_mandal.blog_app_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        System.out.println(username);
        UserEntity userEntity=userRepository.findByEmail(username).orElseThrow(()-> new ResourceNotFountException("User" ,"email",username));


        return userEntity;
    }
}
