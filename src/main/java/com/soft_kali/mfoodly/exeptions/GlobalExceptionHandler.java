package com.rupesh_mandal.blog_app_backend.exeptions;

import com.google.gson.Gson;
import com.rupesh_mandal.blog_app_backend.payloads.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiResponse> methodArgumentTypeMismatchExceptionHandler(MethodArgumentTypeMismatchException exception){
        String message="Invalid Format "+exception.getName()+" : "+exception.getValue();
        return new ResponseEntity<>(new ApiResponse(message,false), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFountException.class)
    public ResponseEntity<ApiResponse> responseNotFountExceptionHandler(ResourceNotFountException exception){
        String message=exception.getMessage();
        return new ResponseEntity<>(new ApiResponse(message,false), HttpStatus.NOT_FOUND);
    }

//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ApiResponse> exception(Exception exception){
//        String message=exception.getMessage();
//        return new ResponseEntity<>(new ApiResponse(message,false), HttpStatus.NOT_FOUND);
//    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException exception){
        Map<String, String> map=new HashMap<>();

        exception.getBindingResult().getAllErrors().forEach((objectError) -> {
            String fieldName= ((FieldError)objectError).getField();
            String message=objectError.getDefaultMessage();
            map.put(fieldName,message);
        });

        return new ResponseEntity<>(map,HttpStatus.BAD_REQUEST);
    }

}
