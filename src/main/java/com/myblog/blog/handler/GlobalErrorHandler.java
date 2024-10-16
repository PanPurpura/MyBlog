package com.myblog.blog.handler;

import com.myblog.blog.dto.ErrorDto;
import com.myblog.blog.exception.*;
import io.jsonwebtoken.JwtException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

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

    @ExceptionHandler(InvalidCredentialsException.class)
    @ResponseBody
    public ResponseEntity<ErrorDto> handleInvalidPasswordException(InvalidCredentialsException e) {
        return ResponseEntity.status(UNAUTHORIZED).body(new ErrorDto(UNAUTHORIZED.value(), UNAUTHORIZED.getReasonPhrase(), e.getMessage()));
    }

    @ExceptionHandler(WrongImageTypeException.class)
    @ResponseBody
    public ResponseEntity<ErrorDto> handleWrongImageTypeException(WrongImageTypeException e) {
        return ResponseEntity.status(FORBIDDEN).body(new ErrorDto(FORBIDDEN.value(), FORBIDDEN.getReasonPhrase(), e.getMessage()));
    }

    @ExceptionHandler(InvalidSizeException.class)
    @ResponseBody
    public ResponseEntity<ErrorDto> handleInvalidSizeException(InvalidSizeException e) {
        return ResponseEntity.status(FORBIDDEN).body(new ErrorDto(FORBIDDEN.value(), FORBIDDEN.getReasonPhrase(), e.getMessage()));
    }

    @ExceptionHandler(InvalidRoleException.class)
    @ResponseBody
    public ResponseEntity<ErrorDto> handleInvalidRoleException(InvalidRoleException e) {
        return ResponseEntity.status(FORBIDDEN).body(new ErrorDto(FORBIDDEN.value(), FORBIDDEN.getReasonPhrase(), e.getMessage()));
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseBody
    public ResponseEntity<ErrorDto> handleAccessDeniedException(AccessDeniedException e) {
        return ResponseEntity.status(FORBIDDEN).body(new ErrorDto(FORBIDDEN.value(), FORBIDDEN.getReasonPhrase(), e.getMessage()));
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseBody
    public ResponseEntity<ErrorDto> handleAccessDeniedException(AuthenticationException e) {
        return ResponseEntity.status(UNAUTHORIZED).body(new ErrorDto(UNAUTHORIZED.value(), UNAUTHORIZED.getReasonPhrase(), e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<ErrorDto> handleOtherErrors(Exception e) {
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ErrorDto(INTERNAL_SERVER_ERROR.value(), INTERNAL_SERVER_ERROR.getReasonPhrase(), e.getMessage()));
    }

    @ExceptionHandler(NoResourceFoundException.class)
    @ResponseBody
    public ResponseEntity<ErrorDto> handleNoResourceFound(NoResourceFoundException e) {
        return ResponseEntity.status(NOT_FOUND).body(new ErrorDto(NOT_FOUND.value(), NOT_FOUND.getReasonPhrase(), e.getMessage()));
    }

}
