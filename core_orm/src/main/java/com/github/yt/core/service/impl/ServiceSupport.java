package com.github.yt.core.service.impl;

import com.github.yt.common.utils.BeanUtils;
import com.github.yt.common.utils.JPAUtils;
import com.github.yt.core.dao.BaseMapper;
import com.github.yt.core.domain.BaseEntity;
import com.github.yt.core.handler.QueryHandler;
import com.github.yt.core.result.QueryResult;
import com.github.yt.core.service.BaseService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public abstract class ServiceSupport<T, M extends BaseMapper<T>> implements BaseService<T> {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    public abstract M getMapper();

    @Override
    public void save(T entity) {
        this.processCreateColumns(entity);
        getMapper().save(entity);
    }

    @Override
    public void saveForSelective(T entity) {
        this.processCreateColumns(entity);
        getMapper().saveForSelective(entity);
    }

    @Override
    public void update(T entity) {
        if (null == JPAUtils.gtIdValue(entity)) {
            return;
        }
        processModifyColumns(entity);
        getMapper().update(entity);
    }

    @Override
    public void updateForSelective(T entity) {
        if (null == JPAUtils.gtIdValue(entity)) {
            return;
        }
        this.processModifyColumns(entity);
        getMapper().updateForSelective(entity);
    }

    @Override
    public void delete(Class<T> clazz, Serializable id) {
        getMapper().delete(clazz , id);
    }

    @Override
    public T find(Class<T> clazz,Serializable id) {
        return getMapper().find(clazz,id);
    }

    @Override
    public List<T> findAll(T entity){
        return getMapper().findAll((Class<T>) entity.getClass(), BeanUtils.getValueMap(null, entity));
    }

    @Override
    public List<T> findAll(T entity, QueryHandler queryHandler) {
        if (queryHandler == null) {
            return getMapper().findAll((Class<T>) entity.getClass(), BeanUtils.getValueMap(queryHandler, entity));
        } else {
            return getMapper().findAll((Class<T>) entity.getClass(), BeanUtils.getValueMap(queryHandler, entity)
                    .chainPutAll(queryHandler == null ? null : queryHandler.getExpandData()));
        }
    }

    @Override
    public QueryResult<T> getData(T entity, QueryHandler queryHandle) {
        QueryResult<T> qr = new QueryResult();
        qr.setRecordsTotal(getMapper().pageTotalRecord((Class<T>) entity.getClass(), BeanUtils.getValueMap(queryHandle, entity)
                .chainPutAll(queryHandle == null ? null : queryHandle.getExpandData())));
        if (qr.getRecordsTotal() == 0) {
            qr.setData(new ArrayList<T>());
            return qr;
        }
        qr.setData(getMapper().pageData((Class<T>) entity.getClass(), BeanUtils.getValueMap(queryHandle, entity).chainPutAll
                (queryHandle == null ? null : queryHandle.getExpandData())));
        return qr;
    }

    private Object getSessionAttr(String attr) {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession()
                .getAttribute(attr);
    }

    private void setFounder(BaseEntity entity) {
        try {
            if (StringUtils.isEmpty(entity.getFounderId())) {
                entity.setFounderId(null == getSessionAttr(BaseEntity.OPERATOR_ID) ? BaseEntity.DEFAULT_OPERATOR :
                        (String) getSessionAttr(BaseEntity.OPERATOR_ID));
            }
            if (StringUtils.isEmpty(entity.getFounderName())) {
                entity.setFounderName(null == getSessionAttr(BaseEntity.OPERATOR_NAME) ? BaseEntity.DEFAULT_OPERATOR :
                        (String) getSessionAttr(BaseEntity.OPERATOR_NAME));
            }
        } catch (Exception e) {
            logger.debug("setFounder时session获取失败!");
            entity.setFounderId(BaseEntity.DEFAULT_OPERATOR);
            entity.setFounderName(BaseEntity.DEFAULT_OPERATOR);
        }
    }

    private void setModifyFounder(BaseEntity entity) {
        try {
            if (StringUtils.isEmpty(entity.getModifierId())) {
                entity.setModifierId(null == getSessionAttr(BaseEntity.OPERATOR_ID) ? BaseEntity.DEFAULT_OPERATOR :
                        (String) getSessionAttr(BaseEntity.OPERATOR_ID));
            }
            if (StringUtils.isEmpty(entity.getModifierName())) {
                entity.setModifierName(null == getSessionAttr(BaseEntity.OPERATOR_NAME) ? BaseEntity.DEFAULT_OPERATOR :
                        (String) getSessionAttr(BaseEntity.OPERATOR_NAME));
            }
        } catch (Exception e) {
            logger.info("update时session获取失败!");
            entity.setModifierId(BaseEntity.DEFAULT_OPERATOR);
            entity.setModifierName(BaseEntity.DEFAULT_OPERATOR);
        }
    }

    private void processCreateColumns(T entity) {
        if (entity instanceof BaseEntity) {
            BaseEntity baseEntity = ((BaseEntity) entity);
            if (baseEntity.getCreateDateTime() == null) {
                baseEntity.setCreateDateTime(new Date());
            }
            if (baseEntity.getModifyDateTime() == null) {
                baseEntity.setModifyDateTime(baseEntity.getCreateDateTime());
            }
            setFounder((BaseEntity) entity);
        }
    }

    private void processModifyColumns(T entity) {
        if (entity instanceof BaseEntity) {
            BaseEntity baseEntity = ((BaseEntity) entity);
            if (baseEntity.getModifyDateTime() == null) {
                baseEntity.setModifyDateTime(new Date());
            }
            this.setModifyFounder((BaseEntity) entity);
        }
    }
}