package com.myblog.blog.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {

    ADMIN_READ("admin:read"),
    ADMIN_WRITE("admin:write"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_DELETE("admin:delete"),
    MODERATOR_READ("moderator:read"),
    MODERATOR_WRITE("moderator:write"),
    MODERATOR_UPDATE("moderator:update"),
    MODERATOR_DELETE("moderator:delete"),
    USER_READ("user:read"),
    USER_WRITE("user:write"),
    USER_UPDATE("user:update"),
    USER_DELETE("user:delete")

    ;

    @Getter
    private final String permission;
}
