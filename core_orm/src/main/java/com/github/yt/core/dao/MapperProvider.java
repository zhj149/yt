package com.github.yt.core.dao;

import com.github.yt.common.exception.BaseErrorException;
import com.github.yt.common.utils.JPAUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.Table;
import javax.persistence.Transient;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.github.yt.core.mybatis.SqlBuilder.*;


public class MapperProvider {

    protected String limit;

    protected void begin() {
        BEGIN();
    }

    protected String sql() {
        return SQL();
    }

    protected void setId(Map<String, Object> param, Field idField) {
        if (!idField.getType().isAssignableFrom(String.class)) {
            return;
        }
        try {
            if (StringUtils.isNotEmpty((String) idField.get(param.get(BaseMapper.ENTITY)))) {
                VALUES(idField.getName(), StringUtils.join("#{", BaseMapper.ENTITY, ".", idField.getName(), "}"));
                return;
            }
            String id = UUID.randomUUID().toString().replace("-", "");
            VALUES(idField.getName(), StringUtils.join("'", id, "'"));
            idField.set(param.get(BaseMapper.ENTITY), id);
        } catch (IllegalAccessException e) {
            throw new BaseErrorException(e);
        }
    }

    protected String getTableName(Class entityClass) {
        Annotation table = entityClass.getAnnotation(Table.class);
        if (null == table) {
            throw new BaseErrorException(StringUtils.join("实体未配置Table注解 entityClass =", entityClass.getName()));
        }
        String tableName = ((Table) table).name();
        if (StringUtils.isEmpty(tableName)) {
            throw new BaseErrorException(StringUtils.join("实体的Table注解未配置name属性 entityClass =", entityClass.getName()));
        }
        return tableName;
    }

    protected String getTableNameWithAlias(Class entityClass) {
        return StringUtils.join(getTableName(entityClass), " t");
    }

    protected String createSelectColumnSql(Map<String, Object> map) {
        String selectColumnSql = " t.* ";
        if (MapUtils.isNotEmpty(map) && map.containsKey("selectColumnSql") && map.get("selectColumnSql") != null) {
            selectColumnSql = " " + map.get("selectColumnSql").toString() + " ";
        }
        return selectColumnSql;
    }

    protected String getEqualsValue(String column, String value) {
        return StringUtils.join(column, " = #{", value, "}");
    }

    protected void createAllWhere(Class<?> entityClass,Map<String, Object> param, boolean usePage) {
        if (MapUtils.isEmpty(param)) {
            return;
        }
        try {
            createFieldsWhereSql(entityClass,param);
            parseQueryHandle(param, usePage);
        } catch (Exception e) {
            throw new BaseErrorException(e);
        }
    }

    private void createFieldsWhereSql(Class clazz,Map<String, Object> param) {
        for (Field field : JPAUtils.getAllFields(clazz)) {
            createFieldWhereSql(field,param);
        }
    }

    private boolean createFieldWhereSql(Field field,Map<String, Object> param) {
        if (!validateFieldWhereSql(field, param)) {
            return false;
        }
        if (null != field.getType().getAnnotation(Table.class)) {
            return false;
        }
        WHERE(StringUtils.join("t.", getEqualsValue(field.getName(), StringUtils.join(BaseMapper.DATA + ".", field.getName()))));
        return true;
    }

    private boolean validateFieldWhereSql(Field field, Map<String, Object>
            param) {
        if (null != field.getAnnotation(Transient.class) || field.getType().isAssignableFrom(Class.class)) {
            return false;
        }
        return param.containsKey(field.getName());
    }

    private void parseQueryHandle(Map<String, Object> param, Boolean usePage) {
        if (param.containsKey("whereSqls")) {
            List<String> whereSqlList = (List<String>) param.get("whereSqls");
            for (String whereSql : whereSqlList) {
                WHERE(whereSql);
            }
        }
        createLimit(param, usePage);
    }

    private void createLimit(Map<String, Object> param, Boolean usePage) {
        if (param.containsKey("page") && usePage) {
            limit = StringUtils.join(" limit ", ((Integer) param.get("page") - 1) * (Integer) param.get("rows"), " , ",
                    param.get("rows").toString());
        }
    }

    protected String createSelectCountColumnSql(Map<String, Object> param) {
        Map<String, Object> map = ((Map<String, Object>) param.get(BaseMapper.DATA));
        String selectColumnSql = "count(distinct t." + JPAUtils.getIdField((Class<?>) param.get(BaseMapper.ENTITY_CLASS)).getName() + ")";
        if (map.containsKey("selectColumnSql") && map.get("selectColumnSql") != null) {
            selectColumnSql = " count(distinct " + map.get("selectColumnSql").toString() + ") ";
        }
        return selectColumnSql;
    }

}
