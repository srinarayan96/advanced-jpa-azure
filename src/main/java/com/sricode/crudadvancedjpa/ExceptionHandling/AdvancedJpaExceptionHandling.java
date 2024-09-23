package com.sricode.crudadvancedjpa.ExceptionHandling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AdvancedJpaExceptionHandling {
    @ExceptionHandler(InstructorException.class)
    public ResponseEntity<ErrorResponse> instructorExcpHandle(InstructorException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.builder()
                        .message(ex.getMessage())
                        .success(false)
                        .build());
    }
    @ExceptionHandler(CourseException.class)
    public ResponseEntity<ErrorResponse> courseExcpHandle(CourseException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.builder()
                        .message(ex.getMessage())
                        .success(false)
                        .build());
    }
}
