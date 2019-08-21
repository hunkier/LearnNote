package cn.hunkier.spring.data.sqlerrorcodedemo;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;

public class CustomDuplicateKeyException extends DuplicateKeyException {
    public CustomDuplicateKeyException(String msg) {
        super(msg);
    }

    public CustomDuplicateKeyException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
