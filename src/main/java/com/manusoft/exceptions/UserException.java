package com.manusoft.exceptions;

import java.io.Serial;

public class UserException extends  RuntimeException {

    @Serial
    private  static  final long serialVersionUID=1L;
    public UserException(final String message) {
        super(message);
    }
}
