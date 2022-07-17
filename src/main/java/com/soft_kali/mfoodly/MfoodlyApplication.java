package com.soft_kali.mfoodly;

import com.soft_kali.mfoodly.service.ConstantService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

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
