package com.example.MyWebMess.exception;

import com.example.MyWebMess.model.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionApiHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorMessage> notFoundException(NotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMessage(exception.getMessage()));
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ErrorMessage> forbiddenException(ForbiddenException exception) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorMessage(exception.getMessage()));
    }

    @ExceptionHandler(FileException.class)
    public ResponseEntity<ErrorMessage> fileReadException(FileException exception) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorMessage(exception.getMessage()));
    }

}
