package com.rupesh_mandal.blog_app_backend.controller;

import com.rupesh_mandal.blog_app_backend.payloads.JwtAuthRequest;
import com.rupesh_mandal.blog_app_backend.payloads.JwtAuthResponse;
import com.rupesh_mandal.blog_app_backend.payloads.UserDto;
import com.rupesh_mandal.blog_app_backend.security.JwtTokenHelper;
import com.rupesh_mandal.blog_app_backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        authenticate(request.getEmail(), request.getPassword());
        UserDetails userDetails=userDetailsService.loadUserByUsername(request.getEmail());
        String token= jwtTokenHelper.generateToken(userDetails);

        return new ResponseEntity<>(new JwtAuthResponse(token), HttpStatus.OK);
    }


    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@Valid @RequestBody UserDto userDto){
        UserDto registeredUser=userService.registerNewUser(userDto);
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
