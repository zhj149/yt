package com.github.yt.example.orm.service.impl;

import com.github.yt.core.service.impl.ServiceSupport;
import com.github.yt.example.orm.dao.TestMapper;
import com.github.yt.example.orm.domain.MemberT;
import com.github.yt.example.orm.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl extends ServiceSupport<MemberT, TestMapper> implements TestService {

    @Autowired
    private TestMapper financialMapper;

    @Override
    public TestMapper getMapper() {
        return financialMapper;
    }
}
