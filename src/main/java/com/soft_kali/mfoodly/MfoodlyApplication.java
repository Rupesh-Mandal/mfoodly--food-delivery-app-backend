package com.soft_kali.mfoodly;

import com.soft_kali.mfoodly.entity.CategoryEntity;
import com.soft_kali.mfoodly.entity.Role;
import com.soft_kali.mfoodly.repository.CategoryRepository;
import com.soft_kali.mfoodly.repository.RoleRepository;
import com.soft_kali.mfoodly.service.ConstantService;
import com.soft_kali.mfoodly.utils.AppConstants;
import com.soft_kali.mfoodly.utils.CategoryConstants;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class MfoodlyApplication implements CommandLineRunner {

    @Autowired
    ConstantService constantService;

    public static void main(String[] args) {
        SpringApplication.run(MfoodlyApplication.class, args);
    }

    @Override
    public void run(String... args){
      constantService.saveConstant();

    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
