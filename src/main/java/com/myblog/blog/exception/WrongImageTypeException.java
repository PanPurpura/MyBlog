package com.myblog.blog.exception;

public class WrongImageTypeException extends RuntimeException{
    public WrongImageTypeException(String message) {
        super(message);
    }
}
