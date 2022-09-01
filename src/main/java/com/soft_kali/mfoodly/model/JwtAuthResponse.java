package com.soft_kali.mfoodly.model;

import com.soft_kali.mfoodly.entity.user.UserEntity;
import lombok.*;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class JwtAuthResponse {

    private String token;
}
