//package com.yunde.website.dao.hibernate.impl;
//
//import com.yunde.frame.tools.StringKit;
//import com.yunde.website.dao.hibernate.IGenericDao;
//import org.apache.commons.beanutils.PropertyUtils;
//import org.fsdcic.lzda.assis.PageQueryResult;
//import org.fsdcic.lzda.assis.QueryConditionCollecion;
//import org.fsdcic.lzda.assis.ReflectionUtils;
//import org.hibernate.*;
//import org.hibernate.criterion.*;
//import org.hibernate.internal.CriteriaImpl;
//import org.hibernate.metadata.ClassMetadata;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
//import org.springframework.util.Assert;
//
//import java.io.Serializable;
//import java.lang.reflect.InvocationTargetException;
//import java.util.*;
//
//public abstract class GenericDao<T extends Serializable, PK extends Serializable> extends HibernateDaoSupport implements IGenericDao<T, PK> {
//
//    private static final int defaultMaxResultCount = 1000;
//    protected Class entityClass;
//
//    GenericDao() {
//        this.entityClass = ReflectionUtils.getSuperClassGenricType(getClass(), 0);
//    }
//
//    // 自动注入SessionFactory
//    @Autowired
//    public void setupSessionFactory(final SessionFactory sessionFactory) {
//        this.setSessionFactory(sessionFactory);
//    }
//
//
//    /**
//     * 查询默认最大返回记录数
//     */
//    protected int getDefaultMaxResultCount() {
//        return this.defaultMaxResultCount;
//    }
//
//
//    public T get(PK id) {
//        return (T) this.getHibernateTemplate().get(this.entityClass, id);
//    }
//
//    //region =====getAll()====
//    public List<T> getAll() {
//        return this.getAll(this.getDefaultMaxResultCount());
//    }
//
//    public List<T> getAll(int maxCount) {
//        return this.getAll(maxCount, null);
//    }
//
//
//    public List<T> getAll(int maxCount, String orderBy, boolean isAsc) {
//        LinkedHashMap<String, Boolean> orderByMap = new LinkedHashMap<>();
//        if (!StringKit.isNullOrEmpty(orderBy)) {
//            orderByMap.put(orderBy, isAsc);
//        }
//        return this.getAll(maxCount, orderByMap);
//    }
//
//    public List<T> getAll(int maxCount, LinkedHashMap<String, Boolean> orderByMap) {
//        return this.getAll(maxCount, null, orderByMap);
//    }
//
//    public List<T> getAll(int maxCount, HashMap<String, Object> valueMap, LinkedHashMap<String, Boolean> orderByMap) {
//        return this.getAll(1, maxCount, valueMap, orderByMap).getData();
//    }
//
//    public PageQueryResult<T> getAll(int pageIndex, int pageSize, HashMap<String, Object> valueMap, LinkedHashMap<String, Boolean> orderByMap) {
//        return getHibernateTemplate().execute(session -> {
//            String hql = String.format("from %s where 1=1 ", entityClass.getName());
//            if (valueMap != null) {
//                for (String key : valueMap.keySet()) {
//                    String paramName = key.replace('.', '_');
//                    hql += String.format(" and %1$s=:%2$s ", key, paramName);
//                }
//            }
//
//            if (orderByMap != null) {
//                String orderBy = "";
//                for (Map.Entry<String, Boolean> entry : orderByMap.entrySet()) {
//                    orderBy += entry.getKey() + " " + (entry.getValue() ? "ASC" : "DESC") + ",";
//                }
//
//                if (orderBy.length() > 1) {
//                    hql = hql + " order by " + orderBy.substring(0, orderBy.length() - 1);
//                }
//            }
//            //Object tmpObj = valueMap!=null ? valueMap.values() : null;
//            if (valueMap != null) {
//                return this.queryPage(pageIndex, pageSize, hql, valueMap);
//            } else {
//                return this.queryPage(pageIndex, pageSize, hql);
//            }
//        });
//    }
//
//    public List<T> getByHql(String hql, Map<String, Object> valueMap) {
//        return getHibernateTemplate().execute(session -> {
//            Query query = createQuery(session, hql, valueMap);
//            return (List<T>) query.list();
//        });
//    }
//
//    //endregion getAll
//
//    public boolean save(T entity) {
//        return getHibernateTemplate().save(entity) != null;
//    }
//
//    public boolean update(T entity) {
//        getHibernateTemplate().update(entity);
//        return true;
//    }
//
//    public boolean saveOrUpdate(T entity) {
//        getHibernateTemplate().saveOrUpdate(entity);
//        return true;
//    }
//
//    public boolean deleteById(PK id) {
//        boolean result = false;
//        T entity = this.get(id);
//        if (entity != null) {
//            result = this.delete(entity);
//        }
//
//        return result;
//    }
//
//    public boolean delete(T entity) {
//        getHibernateTemplate().delete(entity);
//        return true;
//    }
//
//    public void flush() {
//        this.getHibernateTemplate().flush();
//    }
//
//    public void clear() {
//        this.getHibernateTemplate().clear();
//    }
//
//    public boolean isUnique(T entity, String uniquePropertyNames) {
//        return this.isUnique(entity, uniquePropertyNames, false);
//    }
//
//    public boolean isUnique(T entity, String uniquePropertyNames, boolean ignoreCase) {
//        Assert.hasText(uniquePropertyNames);
//        DetachedCriteria criteria = DetachedCriteria.forClass(entityClass);
//
//        //Criteria criteria = createCriteria().setProjection(Projections.rowCount());
//        String[] nameList = uniquePropertyNames.split(",");
//        try {
//            // 循环加入唯一列
//            for (String name : nameList) {
//                SimpleExpression expression = Restrictions.eq(name, PropertyUtils.getProperty(entity, name));
//                if (ignoreCase) {
//                    criteria.add(expression.ignoreCase());
//                } else {
//                    criteria.add(expression);
//                }
//            }
//
//            // 以下代码为了如果是update的情况,排除entity自身.
//
//            String idName = getIdName();
//
//            // 取得entity的主键值
//            Serializable id = getId(entity);
//
//            // 如果id!=null,说明对象已存在,该操作为update,加入排除自身的判断
//            if (id != null)
//                criteria.add(Restrictions.not(Restrictions.eq(idName, id)));
//        } catch (Exception e) {
//            org.springframework.util.ReflectionUtils.handleReflectionException(e);
//        }
//
//        return getHibernateTemplate().findByCriteria(criteria).size() == 0;
//    }
//
//    @SuppressWarnings("unchecked")
//    public PageQueryResult<T> queryPage(final int pageIndex, final int pageSize, final String hql, final Object... values) {
//        List<T> list = getHibernateTemplate().execute(session -> {
//            Query query = createQuery(session, hql, values);
//            query.setFirstResult((pageIndex - 1) * pageSize);
//            query.setMaxResults(pageSize);
//            return (List<T>) query.list();
//        });
//        long totalCount = getHibernateTemplate().execute(session -> getHqlQueryCount(session, hql, values));
//        return new PageQueryResult<T>(totalCount, pageIndex, pageSize, list);
//    }
//
//    @SuppressWarnings("unchecked")
//    public PageQueryResult<T> queryPage(final int pageIndex, final int pageSize, final String hql, final Map<String, Object> valueMap) {
//        List<T> list = getHibernateTemplate().execute(session -> {
//            Query query = createQuery(session, hql, valueMap);
//            return (List<T>) query.setFirstResult((pageIndex - 1) * pageSize)
//                    .setMaxResults(pageSize)
//                    .list();
//        });
//
//        long totalCount = getHibernateTemplate().execute(session -> getHqlQueryCount(session, hql, valueMap));
//        PageQueryResult<T> result = new PageQueryResult<T>(totalCount, pageIndex, pageSize, list);
//        return result;
//    }
//
//    public List<T> getByIds(Collection<PK> ids) {
//        return getHibernateTemplate().execute(session -> {
//            String hql = String.format("from %1$s where %2$s in (:%2$s) ", entityClass.getName(), getIdName());
//            HashMap<String, Object> valueMap = new HashMap<String, Object>();
//            valueMap.put(getIdName(), ids);
//            Query query = createQuery(session, hql, valueMap);
//            return (List<T>) query.list();
//        });
//    }
//
//    public PageQueryResult<T> getByConditions(QueryConditionCollecion conds, LinkedHashMap<String, Boolean> orderByMap, int pageIndex, int pageSize) {
//        return getHibernateTemplate().execute(session -> {
//            Criteria criteria = session.createCriteria(entityClass);
//            for (int i = 0; i < conds.size(); i++) {
//                Criterion cr = conds.get(i).getSimpleCriterion(criteria);
//                if (cr != null) {
//                    criteria.add(cr);
//                }
//            }
//
//            if (orderByMap != null) {
//                for (Map.Entry<String, Boolean> entry : orderByMap.entrySet()) {
//                    if (entry.getValue()) {
//                        criteria.addOrder(Order.asc(entry.getKey()));
//                    } else {
//                        criteria.addOrder(Order.desc(entry.getKey()));
//                    }
//                }
//            }
//
//            return queryPage(criteria, pageIndex, pageSize);
//        });
//    }
//
//    private PageQueryResult<T> queryPage(Criteria criteria, int pageIndex, int pageSize) {
//        //获取数据
//        if (pageSize > 0 && pageIndex > 0) {
//            criteria.setMaxResults(pageSize);
//            criteria.setFirstResult((pageIndex - 1) * pageSize);
//        }
//        List<T> data = (List<T>) criteria.list();
//
//        //去掉分页限制
//        criteria.setProjection(null);
//        //criteria.setResultTransformer(Criteria.ROOT_ENTITY);
//        criteria.setMaxResults(0);
//        criteria.setFirstResult(0);
//
//        //去掉排序条件
//        Iterator<CriteriaImpl.OrderEntry> orderIter = ((CriteriaImpl) criteria).iterateOrderings();
//        while (orderIter.hasNext()) {
//            orderIter.next();
//            orderIter.remove();
//        }
//
//        //计算记录数
//        criteria.setProjection(Projections.rowCount());
//        Long totalCount = (Long) criteria.uniqueResult();
//
//
//        return new PageQueryResult<T>(totalCount, pageIndex, pageSize, data);
//    }
//
//
//    @Override
//    public int getSeqNo(String seqName) {
//
//        int result = getHibernateTemplate().execute(session -> {
//            String sql = "select nextval('" + seqName + "')";
//            SQLQuery qry = session.createSQLQuery(sql);
//            List list = qry.list();
//            return Integer.valueOf(list.get(0).toString());
//        });
//        return result;
//    }
//
//    protected Query createQuery(Session session, String hql, Map<String, Object> valueMap) {
//        Query query = session.createQuery(hql);
//        if (valueMap != null) {
//            for (String key : valueMap.keySet()) {
//                Object val = valueMap.get(key);
//
//                String paramName = key.replace('.', '_');
//
//                if (val instanceof Collection) {
//                    query.setParameterList(paramName, (Collection) val);
//                } else if (val instanceof Object[]) {
//                    query.setParameterList(paramName, (Object[]) val);
//                } else {
//                    query.setParameter(paramName, valueMap.get(key));
//                }
//
//            }
//        }
//        return query;
//    }
//
//    private Query createQuery(Session session, final String hql, final Object... values) {
//        Query query = session.createQuery(hql);
//        if (values != null) {
//            for (int i = 0; i < values.length; i++) {
//                Object val = values[i];
//                query.setParameter(i, val);
//            }
//        }
//        return query;
//    }
//
//    //region ====获取hql查询结果记录数的方法====
//    private Long getHqlQueryCount(Session session, final String hql, final Map<String, Object> valueMap) {
//        String counthql = getRecordCountHql(hql);
//
//        Query query = createQuery(session, counthql, valueMap);
//        return (Long) query.uniqueResult();
//    }
//
//    private Long getHqlQueryCount(Session session, final String hql, final Object... values) {
//        String counthql = getRecordCountHql(hql);
//
//        Query query = createQuery(session, counthql, values);
//        return (Long) query.uniqueResult();
//    }
//
//    private String getRecordCountHql(String hql) {
//        int sql_from = hql.toLowerCase().indexOf("from");
//        int sql_orderby = hql.toLowerCase().indexOf("order by");
//        String counthql;
//
//        if (sql_orderby > 0) {
//            counthql = "select count(*) "
//                    + hql.substring(sql_from, sql_orderby);
//        } else {
//            counthql = "select count(*) " + hql.substring(sql_from);
//        }
//        return counthql;
//    }
//    //endregion 获取hql查询结果记录数的方法
//
//    public PK getEntityId(T entity) {
//        PK result = null;
//        try {
//            result = (PK) this.getId(entity);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return result;
//    }
//
//    /**
//     * 取得对象的主键值,辅助函数.
//     */
//    private Serializable getId(Object entity) throws NoSuchMethodException, IllegalAccessException,
//            InvocationTargetException {
//        Assert.notNull(entity);
//        Assert.notNull(entityClass);
//        return (Serializable) PropertyUtils.getProperty(entity, getIdName());
//    }
//
//    /**
//     * 取得对象的主键名,辅助函数.
//     */
//    private String getIdName() {
//        ClassMetadata meta = getSessionFactory().getClassMetadata(entityClass);
//        Assert.notNull(meta, "Class " + entityClass + " not define in hibernate session factory.");
//        String idName = meta.getIdentifierPropertyName();
//        Assert.hasText(idName, entityClass.getSimpleName() + " has no identifier property define.");
//        return idName;
//    }
//
//    /**
//     * 批量保存
//     * @param entitys
//     * @param <T>
//     * @return
//     */
//    @Override
//    public <T> int saveBatch(List<T> entitys) {
//        int affectedRow = getHibernateTemplate().execute(session -> {
//            entitys.forEach(entity -> {
//                session.save(entity);
//            });
//            return entitys.size();
//        });
//        return affectedRow;
//    }
//
//    @Override
//    public List formList(String hql, Map<String, Object> params, Boolean hadId) {
//        return null;
//    }
//
//    @Override
//    public String orderBy() {
//        return null;
//    }
//}
