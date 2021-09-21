package com.chatroom.app.exception.user;

import com.chatroom.app.dto.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorResponseDTO> handleUserNotFoundException(UserNotFoundException exception){
        return new ResponseEntity<>(new ErrorResponseDTO("User Not Found", exception.getMessage()), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler
    public ResponseEntity<ErrorResponseDTO> handleOtherException(Exception exception){
        return new ResponseEntity<>(new ErrorResponseDTO("Unknown exception occurred" , exception.getMessage()), HttpStatus.FORBIDDEN);
    }

}
