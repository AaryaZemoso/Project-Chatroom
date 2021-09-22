package com.chatroom.app.exception.chatroom;

import com.chatroom.app.dto.ErrorResponseDTO;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Order(2)
public class ChatroomExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorResponseDTO> handleChatroomNotFoundException(ChatroomNotFoundException exception){
        return new ResponseEntity<>(new ErrorResponseDTO("Chatroom Not Found" , exception.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponseDTO> handleOtherException(Exception exception){
        return new ResponseEntity<>(new ErrorResponseDTO("Unknown exception occurred" , exception.getMessage()), HttpStatus.FORBIDDEN);
    }
}
