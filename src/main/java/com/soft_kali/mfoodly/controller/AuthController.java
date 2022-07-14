package com.soft_kali.mfoodly.controller;


import com.soft_kali.mfoodly.model.JwtAuthRequest;
import com.soft_kali.mfoodly.model.JwtAuthResponse;
import com.soft_kali.mfoodly.dto.UserDto;
import com.soft_kali.mfoodly.jwt.JwtTokenHelper;
import com.soft_kali.mfoodly.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    JwtTokenHelper jwtTokenHelper;

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserService userService;

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> creatToken(@RequestBody JwtAuthRequest request) throws Exception {
        authenticate(request.getPhoneNumber(), request.getPassword());
        UserDetails userDetails=userDetailsService.loadUserByUsername(request.getPhoneNumber());
        String token= jwtTokenHelper.generateToken(userDetails);

        return new ResponseEntity<>(new JwtAuthResponse(token), HttpStatus.OK);
    }


    @PostMapping("/cityId/{cityId}/register")
    public ResponseEntity<UserDto> registerUser(@Valid @RequestBody UserDto userDto,@PathVariable int cityId){
        UserDto registeredUser=userService.registerNewUser(userDto,cityId);
        return new ResponseEntity<>(registeredUser,HttpStatus.CREATED);
    }



    @PostMapping("/cityId/{cityId}/register-new-outlet-user")
    public ResponseEntity<UserDto> registerNewOutletUser(@Valid @RequestBody UserDto userDto,@PathVariable int cityId){
        UserDto registeredUser=userService.registerNewOutletUser(userDto,cityId);
        return new ResponseEntity<>(registeredUser,HttpStatus.CREATED);
    }

    private void authenticate(String email, String password) throws Exception {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(email, password);

        try {
            authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        }catch (BadCredentialsException e){
            System.out.println("Invalid Details !!");
            throw new Exception("Invalid username or password");
        }


    }



}
