package com.myblog.blog.handler;

import com.myblog.blog.dto.ErrorDto;
import com.myblog.blog.exception.InvalidPasswordException;
import com.myblog.blog.exception.NotFoundException;
import com.myblog.blog.exception.UserExistException;
import com.myblog.blog.model.User;
import io.jsonwebtoken.JwtException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class GlobalErrorHandler {

    @ExceptionHandler(NotFoundException.class)
    @ResponseBody
    public ResponseEntity<ErrorDto> handleNotFoundException(NotFoundException e) {
        return ResponseEntity.status(NOT_FOUND).body(new ErrorDto(NOT_FOUND.value(), NOT_FOUND.getReasonPhrase(), e.getMessage()));
    }

    @ExceptionHandler(UserExistException.class)
    @ResponseBody
    public ResponseEntity<ErrorDto> handleUserExistException(UserExistException e) {
        return ResponseEntity.status(BAD_REQUEST).body(new ErrorDto(BAD_REQUEST.value(), BAD_REQUEST.getReasonPhrase(), e.getMessage()));
    }

    @ExceptionHandler(JwtException.class)
    @ResponseBody
    public ResponseEntity<ErrorDto> handleJwtException(JwtException e) {
        return ResponseEntity.status(UNAUTHORIZED).body(new ErrorDto(UNAUTHORIZED.value(), UNAUTHORIZED.getReasonPhrase(), e.getMessage()));
    }

    @ExceptionHandler(InvalidPasswordException.class)
    @ResponseBody
    public ResponseEntity<ErrorDto> handleInvalidPasswordException(InvalidPasswordException e) {
        return ResponseEntity.status(UNAUTHORIZED).body(new ErrorDto(UNAUTHORIZED.value(), UNAUTHORIZED.getReasonPhrase(), e.getMessage()));
    }

}
