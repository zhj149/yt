package com.github.yt.core.handler;

import com.github.yt.core.config.impl.PageConfiguration;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class QueryHandler {

    // 当前页
    private int page = 1;
    // 页面大小
    private int rows = 10;

    //是否使用distinct
    private Boolean distinct;
    //扩展的whereSql
    private List<String> whereSqls = new ArrayList<>();
    //扩展数据
    private Map<String, Object> expandData = new HashMap<>();

    //指定查询列sql，例如“ t.name,t.pass”
    protected String selectColumnSql;

    public QueryHandler setSelectColumnSql(String sql) {
        if (StringUtils.isNotEmpty(sql)) {
            this.selectColumnSql = sql;
        }
        return this;
    }

    public int getPage() {
        return page;
    }

    public QueryHandler setPage(int page) {
        this.page = page;
        return this;
    }

    private static int maxRows = 2000;
    private static int resetRows = 20;

    //以下两个方法需要引用core处系统初始化时设置
    public static void setMaxRows(int maxRows_) {
        maxRows = maxRows_;
    }

    public static void setResetRows(int resetRows_) {
        resetRows = resetRows_;
    }

    public int getRows() {
        if (rows > maxRows) {
            rows = resetRows;
        }
        return rows;
    }

    public QueryHandler setRows(int rows) {
        this.rows = rows;
        return this;
    }

    public QueryHandler addWhereSql(String whereSql) {
        if (StringUtils.isNotEmpty(whereSql)) {
            whereSqls.add(whereSql);
        }
        return this;
    }

    public QueryHandler configPage() {
        PageConfiguration.create().convert(this, getHttpServletRequest());
        return this;
    }

    public static HttpServletRequest getHttpServletRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attributes == null ? null : attributes.getRequest();
    }

    public QueryHandler addDistinct() {
        distinct = true;
        return this;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public QueryHandler addExpandData(String key, Object value) {
        expandData.put(key, value);
        return this;
    }

    public Map<String, Object> getExpandData() {
        return expandData;
    }

    public String getSelectColumnSql() {
        return selectColumnSql;
    }

}

