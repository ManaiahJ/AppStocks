package com.manusoft.exceptions;

import java.io.Serial;

public class UserExistingException extends RuntimeException{
    @Serial
    private static final long serialVersionUID=1L;

    public UserExistingException(final String message) {
        super(message);
    }
}
