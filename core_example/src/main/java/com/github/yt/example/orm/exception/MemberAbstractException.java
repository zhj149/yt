package com.github.yt.example.orm.exception;

import com.github.yt.common.exception.BaseAccidentException;
import com.github.yt.common.exception.ExceptionBody;

public abstract class MemberAbstractException extends BaseAccidentException {

    public MemberAbstractException(MemberExceptionEnum cartExceptionEnum) {
        super(new ExceptionBody(cartExceptionEnum.name(), cartExceptionEnum.getMessage()));
    }

    public MemberAbstractException(MemberExceptionEnum cartExceptionEnum, Exception e) {
        super(new ExceptionBody(cartExceptionEnum.name(), cartExceptionEnum.getMessage()), e);
    }

    public MemberAbstractException(MemberExceptionEnum cartExceptionEnum, String message) {
        super(new ExceptionBody(cartExceptionEnum.name(), message));
    }

    public MemberAbstractException(MemberExceptionEnum cartExceptionEnum, String message, Exception e) {
        super(new ExceptionBody(cartExceptionEnum.name(), message), e);
    }
}
