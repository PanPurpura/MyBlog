package com.myblog.blog.exception;

import javax.management.relation.InvalidRoleInfoException;

public class InvalidRoleException extends InvalidRoleInfoException {

    public InvalidRoleException(String message) {
        super(message);
    }

}
