package com.soft_kali.mfoodly.exeptions;

public class BadRequest extends RuntimeException{

    public BadRequest(String message) {
        super(message);
    }
}
