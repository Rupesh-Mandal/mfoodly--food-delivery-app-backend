package com.soft_kali.mfoodly.dto;

import lombok.*;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class JwtAuthRequest {
    private String phoneNumber;
    private String password;
}
