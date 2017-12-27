package com.github.yt.example.orm.exception;


public class TestException11 extends MemberAbstractException {

    private static MemberExceptionEnum ERR = MemberExceptionEnum.ERROR_11;

    public TestException11() {
        super(ERR);
    }

    public TestException11(Exception e) {
        super(ERR, e);
    }
}
