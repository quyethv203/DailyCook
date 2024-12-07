package com.example.foodwed.exception;

import com.example.foodwed.dto.response.ApiRespone;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ApiRespone> handlingValiddation(MethodArgumentNotValidException exception){
        String emunKey = exception.getFieldError().getDefaultMessage();
        ErrorCode errorCode = ErrorCode.valueOf(emunKey);

        ApiRespone apiRespone = new ApiRespone<>();
        apiRespone.setStatus(errorCode.getStatus());
        apiRespone.setCode(String.valueOf(errorCode.getCode()));
        apiRespone.setMessage(errorCode.getMessage());
        return ResponseEntity.badRequest().body(apiRespone);
    }

    @ExceptionHandler(value = Appexception.class)
    ResponseEntity<ApiRespone> handlingAppException(Appexception exception){
        ErrorCode errorCode = exception.getErrorCode();
        ApiRespone apiResponse = new ApiRespone<>();
        apiResponse.setStatus(errorCode.getStatus());
        apiResponse.setCode(String.valueOf(errorCode.getCode()));
        apiResponse.setMessage(errorCode.getMessage());

        return ResponseEntity.badRequest().body(apiResponse);
    }

}
