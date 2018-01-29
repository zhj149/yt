package com.github.yt.core.dao;

import com.github.yt.core.dao.provider.CommonProvider;
import com.github.yt.core.dao.provider.SearchProvider;
import org.apache.ibatis.annotations.*;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.List;
import java.util.Map;


public interface BaseMapper<T> {

    String ENTITY_CLASS = "entityClass";
    String ENTITY = "entity";
    String ENTITIES = "entities";
    String ID = "id";
    String DATA = "data";

    /**
     * 插入一条记录
     *
     * @param entity 业务实体
     */
    @InsertProvider(type = CommonProvider.class, method = "save")
    void save(@Param(ENTITY) T entity);


    /**
     * 批量插入
     *
     * @param entities 插入结果集
     */
    @InsertProvider(type = CommonProvider.class, method = "saveBatch")
    void saveBatch(@Valid @Param(ENTITIES) List<T> entities);

     /**
     * 插入非空字段
     *
     * @param entity 业务实体
     */
    @InsertProvider(type = CommonProvider.class, method = "saveForSelective")
    void saveForSelective(@Param(ENTITY) T entity);

    /**
     * 更新一条记录
     *
     * @param entity 业务实体
     */
    @UpdateProvider(type = CommonProvider.class, method = "update")
    void update(@Param(ENTITY) T entity);

    /**
     * 更新非空字段
     *
     * @param entity 业务实体
     */
    @UpdateProvider(type = CommonProvider.class, method = "updateForSelective")
    int updateForSelective(@Param(ENTITY) T entity);

    /**
     * 删除
     *
     * @param entityClass 实体类型
     * @param id          主键
     */
    @DeleteProvider(type = CommonProvider.class, method = "delete")
    void delete(@Param(ENTITY_CLASS) Class<?> entityClass, @Param(ID) Serializable id);

    /**
     * 逻辑删除
     *
     * @param entityClass 实体类型
     * @param id          主键
     */
    @DeleteProvider(type = CommonProvider.class, method = "logicDelete")
    void logicDelete(@Param(ENTITY_CLASS) Class<?> entityClass, @Param(ID) Serializable id);

    /**
     * 删除
     *
     * @param id          主键
     */
    @SelectProvider(type = SearchProvider.class, method = "findById")
    T find(@Param(ENTITY_CLASS) Class<?> entityClass,@Param(ID) Serializable id);


    /**
     * 按条件查询记录集合
     * 此处来写注解，在子类里面生效
     *
     * @param entityClass 实体类型
     * @param data        查询条件
     * @return 查询结果列表
     */
    @SelectProvider(type = SearchProvider.class, method = "findAll")
    List<T> findAll(@Param(ENTITY_CLASS) Class<T> entityClass, @Param(DATA) Map<String, Object> data);


    /**
     * 获取分页记录总数
     * 此处来写注解，在子类里面生效
     *
     * @param entityClass 业务实体类
     * @param data        查询条件
     * @return 实体列表
     */
    @SelectProvider(type = SearchProvider.class, method = "pageData")
    List<T> pageData(@Param(ENTITY_CLASS) Class<T> entityClass, @Param(DATA) Map<String, Object> data);

    /**
     * 统计分页记录总数
     *
     * @param entityClass 业务实体类
     * @param data        查询条件
     * @return 实体列表
     */
    @SelectProvider(type = SearchProvider.class, method = "pageTotalRecord")
    Long pageTotalRecord(@Param(ENTITY_CLASS) Class<T> entityClass, @Param(DATA) Map<String, Object> data);

}