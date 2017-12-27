package com.github.yt.core.config;

import com.github.yt.core.handler.QueryHandler;
import javax.servlet.http.HttpServletRequest;


public interface PageConvert<T> {

    void convert(QueryHandler queryHandler, HttpServletRequest request);

}
