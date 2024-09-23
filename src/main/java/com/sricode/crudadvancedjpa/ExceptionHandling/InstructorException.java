package com.sricode.crudadvancedjpa.ExceptionHandling;

public class InstructorException extends RuntimeException{
    public InstructorException(String message) {
        super(message);
    }

    public InstructorException(Throwable cause) {
        super(cause);
    }
}
