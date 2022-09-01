package com.soft_kali.mfoodly.exeptions;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UnauthorizeRequest extends RuntimeException{


    public UnauthorizeRequest(String message) {
        super(message);
    }
}
