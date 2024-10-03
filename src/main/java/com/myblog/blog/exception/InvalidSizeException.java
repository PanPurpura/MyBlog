package com.myblog.blog.exception;

import org.apache.tomcat.util.http.fileupload.impl.SizeException;

public class InvalidSizeException extends RuntimeException {

    public InvalidSizeException(String message) {
        super(message);
    }
}
