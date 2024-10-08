package com.myblog.blog.handler;

import com.myblog.blog.exception.WrongImageTypeException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final HandlerExceptionResolver handlerExceptionResolver;

    public CustomAuthenticationEntryPoint(HandlerExceptionResolver handlerExceptionResolver_) {
        this.handlerExceptionResolver = handlerExceptionResolver_;
    }
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        handlerExceptionResolver.resolveException(request,response, WrongImageTypeException.class, authException);

    }
}
