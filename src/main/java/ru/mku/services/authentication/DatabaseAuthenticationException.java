package ru.mku.services.authentication;

import org.springframework.security.core.AuthenticationException;

public class DatabaseAuthenticationException extends AuthenticationException {
    public DatabaseAuthenticationException(String msg, Throwable t) {
        super(msg, t);
    }
}
