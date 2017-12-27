package com.github.yt.core.dao.provider;

import com.github.yt.common.exception.BaseErrorException;
import com.github.yt.common.utils.JPAUtils;
import com.github.yt.core.dao.BaseMapper;
import com.github.yt.core.dao.MapperProvider;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;

import static com.github.yt.core.mybatis.SqlBuilder.*;


public class CommonProvider extends MapperProvider {

    public String save(Map<String, Object> param) {
        Class<?> entityClass = param.get(BaseMapper.ENTITY).getClass();
        begin();
        INSERT_INTO(getTableName(entityClass));
        Field idField = null;
        for (Field field : JPAUtils.getAllFields(entityClass)) {
            field.setAccessible(true);
            if (null != field.getAnnotation(Id.class) || null != field.getAnnotation(Transient.class)) {
                idField = field;
                continue;
            }
            Object value = JPAUtils.getValue(param.get(BaseMapper.ENTITY), field.getName());
            if (null == value) {
                continue;
            }
            VALUES(field.getName(), StringUtils.join("#{", BaseMapper.ENTITY, ".", field.getName(), "}"));
        }
        if (null == idField) {
            throw new BaseErrorException(StringUtils.join(entityClass.getName(), "实体未配置@Id "));
        }
        setId(param, idField);
        return sql();
    }

    public String saveForSelective(Map<String, Object> param) {
        Class<?> entityClass = param.get(BaseMapper.ENTITY).getClass();
        begin();
        INSERT_INTO(getTableName(entityClass));
        Field idField = null;
        for (Field field : JPAUtils.getAllFields(entityClass)) {
            field.setAccessible(true);
            if (null != field.getAnnotation(Id.class) || null != field.getAnnotation(Transient.class)) {
                idField = field;
                continue;
            }
            try {
                if (field.get(param.get(BaseMapper.ENTITY)) != null) {
                    VALUES(field.getName(), StringUtils.join("#{", BaseMapper.ENTITY, ".", field.getName(), "}"));
                }
            } catch (IllegalAccessException e) {
                throw new BaseErrorException(e);
            }
        }
        if (null == idField) {
            throw new BaseErrorException(StringUtils.join(entityClass.getName(), "实体未配置@Id "));
        }
        setId(param, idField);
        return sql();
    }

    public String update(Map<String, Object> param) {
        Class<?>  entityClass = param.get(BaseMapper.ENTITY).getClass();
        begin();
        UPDATE(getTableName(entityClass));
        Field idField = null;
        for (Field field : JPAUtils.getAllFields(entityClass)) {
            field.setAccessible(true);
            if (field.getType().isAssignableFrom(Collection.class) || null != field.getType().getAnnotation(Table.class)) {
                continue;
            }
            Annotation columnAnnotation = field.getAnnotation(Column.class);
            if (null != columnAnnotation && ((Column) columnAnnotation).nullable() == false
                    && null == JPAUtils.getValue(param.get(BaseMapper.ENTITY), field.getName())) {
                continue;
            }
            if (null == field.getAnnotation(Id.class)) {
                SET(StringUtils.join(getEqualsValue(field.getName(), StringUtils.join(BaseMapper.ENTITY, ".", field.getName()))));
                continue;
            }
            idField = field;
            WHERE(getEqualsValue(field.getName(), StringUtils.join(BaseMapper.ENTITY, ".", field.getName())));
        }
        if (null == idField) {
            throw new BaseErrorException(StringUtils.join(entityClass.getName(), "实体未配置@Id "));
        }
        return sql();
    }

    public String updateForSelective(Map<String, Object> param) {
        Class<?> entityClass = param.get(BaseMapper.ENTITY).getClass();
        begin();
        UPDATE(getTableName(entityClass));
        Field idField = null;
        for (Field field : JPAUtils.getAllFields(entityClass)) {
            field.setAccessible(true);
            if (field.getType().isAssignableFrom(Collection.class) || null != field.getType().getAnnotation(Table.class)) {
                continue;
            }
            if (null != field.getAnnotation(Id.class)) {
                idField = field;
                continue;
            }
            Object value = JPAUtils.getValue(param.get(BaseMapper.ENTITY), field.getName());
            if (null == value) {
                continue;
            }
            SET(StringUtils.join(getEqualsValue(field.getName(), StringUtils.join(BaseMapper.ENTITY, ".", field.getName()))));
        }
        if (null == idField) {
            throw new BaseErrorException(StringUtils.join(entityClass.getName(), "实体未配置@Id "));
        }
        WHERE(getEqualsValue(idField.getName(), StringUtils.join(BaseMapper.ENTITY, ".", idField.getName())));
        return sql();
    }

    public String delete(Map<String, Object> param) {
        begin();
        Class<?> entityClass = (Class) param.get(BaseMapper.ENTITY_CLASS);
        if(param.get(BaseMapper.ID)==null|| StringUtils.isEmpty(param.get(BaseMapper.ID).toString())){
            throw new BaseErrorException(StringUtils.join(entityClass.getName(), ",删除时主键不能为空!"));
        }
        DELETE_FROM(getTableName(entityClass));
        WHERE(getEqualsValue(JPAUtils.getIdField(entityClass).getName(), BaseMapper.ID));
        return sql();
    }

}
