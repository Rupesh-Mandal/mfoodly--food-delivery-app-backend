package com.soft_kali.mfoodly.exeptions;


import com.soft_kali.mfoodly.model.ApiResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.util.NestedServletException;

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


    @ExceptionHandler(UnauthorizeRequest.class)
    public ResponseEntity<ApiResponse> unauthorizeRequestExceptionHandler(UnauthorizeRequest exception){
        String message=exception.getMessage();
        return new ResponseEntity<>(new ApiResponse(message,false), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadRequest.class)
    public ResponseEntity<ApiResponse> badRequestExceptionHandler(BadRequest exception){
        String message=exception.getMessage();
        return new ResponseEntity<>(new ApiResponse(message,false), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> exception(Exception exception){
        String message=exception.getMessage();
        return new ResponseEntity<>(new ApiResponse(message,false), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalAccessException.class)
    public ResponseEntity<ApiResponse> exception(IllegalAccessException exception){
        String message=exception.getMessage();
        return new ResponseEntity<>(new ApiResponse(message,false), HttpStatus.UNAUTHORIZED);
    }

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

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponse> dataIntegrityViolationException(DataIntegrityViolationException exception){
        String message=exception.getMessage();
        System.out.println(exception);
        return new ResponseEntity<>(new ApiResponse(message,false), HttpStatus.NOT_FOUND);
    }

}
