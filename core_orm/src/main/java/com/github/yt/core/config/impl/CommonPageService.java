package com.github.yt.core.config.impl;

import com.github.yt.core.config.PageConvert;
import com.github.yt.core.handler.QueryHandler;
import com.github.yt.core.result.QueryResult;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.regex.Pattern;


public class CommonPageService<T> implements PageConvert<T> {

    @Override
    public void convert(QueryHandler queryHandler, HttpServletRequest request) {
//        String length = request.getParameter("length");
//        if(StringUtils.isNotEmpty(length)){
//            queryHandler.setRows(Integer.valueOf(length));
//        }
//        String start = request.getParameter("start");
//        if(StringUtils.isNotEmpty(start)){
//            queryHandler.setPage(Integer.valueOf(start)/ queryHandler.getRows()+1);
//        }

        String pageSize = request.getParameter("pageSize");
        if(StringUtils.isNotEmpty(pageSize)){
            queryHandler.setRows(Integer.valueOf(pageSize));
        }

        String currentPage = request.getParameter("currentPage");
        if(StringUtils.isNotEmpty(currentPage)){
            queryHandler.setPage(Integer.valueOf(currentPage));
        }
    }
}
