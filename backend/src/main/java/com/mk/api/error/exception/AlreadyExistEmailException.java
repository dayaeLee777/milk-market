package com.mk.api.error.exception;

import java.sql.SQLException;

public class AlreadyExistEmailException extends SQLException {
    private static final String MESSAGE = "이미 등록된 이메일입니다.";

    public AlreadyExistEmailException() {
        super(MESSAGE);
    }
}
