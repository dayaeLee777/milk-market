package com.mk.api.error.exception;

import java.sql.SQLException;

public class AlreadyExistNicknameException extends SQLException {
    private static final String MESSAGE = "이미 등록된 닉네임입니다.";

    public AlreadyExistNicknameException() {
        super(MESSAGE);
    }
}