package com.github.yt.core.dao.provider;

import com.github.yt.common.exception.BaseErrorException;
import com.github.yt.common.utils.JPAUtils;
import com.github.yt.core.dao.BaseMapper;
import com.github.yt.core.dao.MapperProvider;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

import static com.github.yt.core.mybatis.SqlBuilder.*;


public class SearchProvider extends MapperProvider {

    public String findById(Map<String, Object> param) {
        begin();
        Class<?> entityClass = (Class) param.get(BaseMapper.ENTITY_CLASS);
        if (param.get(BaseMapper.ID) == null || StringUtils.isEmpty(param.get(BaseMapper.ID).toString())) {
            throw new BaseErrorException(StringUtils.join(entityClass.getName(), ",find单个对象时主键不能为空!"));
        }
        SELECT("*");
        FROM(getTableName(entityClass));
        WHERE(getEqualsValue(JPAUtils.getIdField(entityClass).getName(), BaseMapper.ID));
        return sql();
    }

    public String findAll(Map<String, Object> param) {
        begin();
        Class<?> entityClass = (Class) param.get(BaseMapper.ENTITY_CLASS);
        Map<String, Object> map = ((Map<String, Object>) param.get(BaseMapper.DATA));
        String selectColumnSql = createSelectColumnSql(map);
        if (MapUtils.isNotEmpty(map) && map.containsKey("distinct")) {
            SELECT_DISTINCT(selectColumnSql);
        } else {
            SELECT(selectColumnSql);
        }
        FROM(getTableNameWithAlias(entityClass));
        createAllWhere(entityClass, map, false, false);
        return StringUtils.join(sql(), limit);
    }

    public String pageData(Map<String, Object> param) {
        begin();
        Class<?> entityClass = (Class) param.get(BaseMapper.ENTITY_CLASS);
        Map<String, Object> map = ((Map<String, Object>) param.get(BaseMapper.DATA));
        String selectColumnSql = createSelectColumnSql(map);
        if (MapUtils.isNotEmpty(map) && map.containsKey("distinct")) {
            SELECT_DISTINCT(selectColumnSql);
        } else {
            SELECT(selectColumnSql);
        }
        FROM(getTableNameWithAlias(entityClass));
        createAllWhere(entityClass, map, true, false);
        return StringUtils.join(sql(), limit);
    }

    public String pageTotalRecord(Map<String, Object> param) {
        begin();
        Class<?> entityClass = (Class) param.get(BaseMapper.ENTITY_CLASS);
        SELECT(createSelectCountColumnSql(param));
        FROM(getTableNameWithAlias(entityClass));
        createAllWhere(entityClass, (Map<String, Object>) param.get(BaseMapper.DATA), false, true);
        return sql();
    }
}
