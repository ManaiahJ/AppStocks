package com.manusoft.exceptions;

import java.io.Serial;

public class UserNotFoundException extends RuntimeException{
    @Serial
    private static final long serialVersionUID=1L;

    public UserNotFoundException(final String message) {
        super(message);
    }
}
