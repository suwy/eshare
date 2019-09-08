package com.yunde.website.dao;

import java.io.Serializable;
import java.util.*;

public interface IGenericDao<T extends Serializable, PK extends Serializable> {

    /**
     * 获取指定Id的对象实例
     * @param id 对象的主键
     * @return 调用成功返回对象，失败返回null
     */
    T get(PK id);

    /**
     * 获取所有对象的列表
     * @return
     */
    List<T> getAll();

    /**
     * 获取数量不超过maxCount的对象列表
     * @param maxCount 最大返回数量
     * @return
     */
    List<T> getAll(int maxCount);

    /**
     * 获取按指定属性排序的对象列表
     * @param maxCount 最大返回数量
     * @param orderBy 用于排序的属性名
     * @param isAsc 升序
     * @return
     */
    List<T> getAll(int maxCount, String orderBy, boolean isAsc);

    /**
     * 获取按指定属性集合排序的对象列表
     * @param maxCount
     * @param orderByMap
     * @return
     */
    List<T> getAll(int maxCount, LinkedHashMap<String, Boolean> orderByMap);

    List<T> getAll(int maxCount, HashMap<String, Object> valueMap, LinkedHashMap<String, Boolean> orderByMap);

//    PageQueryResult<T> getAll(int pageIndex, int pageSize, HashMap<String, Object> valueMap, LinkedHashMap<String, Boolean> orderByMap);

    List<T> getByHql(String hql, Map<String, Object> valueMap);

    /**
     * 保存对象
     * @param entity 要保存的对象
     * @return 保存成功返回true
     */
    boolean save(T entity);

    /**
     * 将对象的修改更新到数据库
     * @param entity
     * @return 更新成功返回true
     */
    boolean update(T entity);

    boolean saveOrUpdate(T entity);

    /**
     * 删除指定主键值的对象
     * @param id
     * @return 删除成功返回true，失败返回false
     */
    boolean deleteById(PK id);

    /**
     * 删除指定的对象
     * @param entity 待删除的对象
     * @return
     */
    boolean delete(T entity);

    /**
     * 提交session内的所有修改
     */
    void flush();

    /**
     * 清除Session缓冲
     */
    void clear();

    /**
     * 判断对象某些属性的值在数据库中是否唯一.（区分大小写）
     *
     * @param uniquePropertyNames 在POJO里不能重复的属性列表,以逗号分割 如"name,loginid,password"
     */
    boolean isUnique(T entity, String uniquePropertyNames);

    /**
     * 判断对象某些属性的值在数据库中是否唯一.
     *
     * @param entity              要判断的对象
     * @param uniquePropertyNames 在POJO里不能重复的属性列表,以逗号分割 如"name,loginid,password"
     * @param ignoreCase          true忽略大小写
     */
    boolean isUnique(T entity, String uniquePropertyNames, boolean ignoreCase);


    /**
     * 分页查询数据
     * @param pageIndex 页码 （从1开始）
     * @param pageSize 分页大小
     * @param hql 查询使用的hql,hql中的参数用“？”，不支持in操作
     * @param values 查询的参数值
     * @return
     */
//    PageQueryResult<T> queryPage(final int pageIndex, final int pageSize, final String hql, final Object... values);

    /**
     * 分页查询数据
     * @param pageIndex 页码 （从1开始）
     * @param pageSize 分页大小
     * @param hql 查询使用的hql,hql中的参数用“:+名称” 如：“:name1” ,“:name2”
     * @param valueMap 查询的参数的键-值映射
     * @return
     */
//    PageQueryResult<T> queryPage(final int  pageIndex, final int pageSize, final String hql, final Map<String, Object> valueMap);

    /**
     * 获取实体的主键
     *
     * @param entity 实体
     * @return 返回实体的主键
     */
    PK getEntityId(T entity);

    List<T> getByIds(Collection<PK> ids);

    /**
     * 获取指定序列的最新序号
     *
     * @param seqName 序列名称
     * @return 返回最新的序号
     */
    int getSeqNo(String seqName);

//    PageQueryResult<T> getByConditions(QueryConditionCollecion conds, LinkedHashMap<String, Boolean> orderByMap, int pageIndex, int pageSize);

    /**
     * 批量保存
     * @param entitys
     * @param <T>
     * @return
     */
    <T> int saveBatch(List<T> entitys);

    List formList(String hql, Map<String, Object> params, Boolean hadId);

    String orderBy();
}