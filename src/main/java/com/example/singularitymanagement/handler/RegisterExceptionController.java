package com.example.singularitymanagement.handler;

import com.example.singularitymanagement.exception.UserNameOrEmailExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RegisterExceptionController {
    @ExceptionHandler(value = UserNameOrEmailExistsException.class)
    public ResponseEntity<Object> exception(UserNameOrEmailExistsException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
