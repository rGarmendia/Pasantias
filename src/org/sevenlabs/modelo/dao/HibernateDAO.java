package org.sevenlabs.modelo.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.FlushMode;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;



public class HibernateDAO
  implements IHibernateDAO
{
  private boolean cacheQueries = false;
  
  private String queryCacheRegion;
  
  private Log log = LogFactory.getLog(HibernateDAO.class);
  



  @Autowired
  SessionFactory sessionFactory;
  



  public SessionFactory getSessionFactory()
  {
    return this.sessionFactory;
  }
  
  public void setSessionFactory(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }
  
  public void mergeEntity(Object entity)
  {
    if (getCurrentSession() != null) {
      try {
        getCurrentSession().merge(entity);
      } catch (HibernateException e) {
        this.log.error("Fail to flush session", e);
        throw new DAOException("Fail to flush session", e);
      }
    }
  }
  
  public void refreshEntity(Object entity) {
    if (getCurrentSession() != null) {
      try {
        getCurrentSession().refresh(entity);
      } catch (HibernateException e) {
        this.log.error("Fail to flush session", e);
        throw new DAOException("Fail to flush session", e);
      }
    }
  }
  
  public void evictEntity(Object entity)
  {
    if (getCurrentSession() != null)
      try {
        getCurrentSession().evict(entity);
      } catch (HibernateException e) {
        this.log.error("Fail to flush session", e);
        throw new DAOException("Fail to flush session", e);
      }
  }
  
  public Object findByKey(Class clase, Serializable clave) {
    try { Object data = getCurrentSession().get(clase, clave);
      if (data != null) {
        return data;
      }
      
      return null;
    }
    catch (RuntimeException re) {
      throw re;
    }
  }
  
  public void flushSession() throws DAOException {
    if (getCurrentSession() != null)
      try {
        getCurrentSession().flush();
      } catch (HibernateException e) {
        this.log.error("Fail to flush session", e);
        throw new DAOException("Fail to flush session", e);
      }
  }
  
  public void clearSession() throws DAOException {
    try {
      getCurrentSession().clear();
    } catch (HibernateException ex) {
      this.log.error("Fail to clear session", ex);
      throw new DAOException("Fail to clear session", ex);
    }
  }
  
  public void update(Object persistentObject) throws DAOException {
    try {
      getCurrentSession().update(persistentObject);
    } catch (HibernateException ex) {
      this.log.error("Fail to update", ex);
      throw new DAOException("Fail to update", ex);
    }
  }
  
  public void delete(Object persistentObject) throws DAOException {
    try {
      getCurrentSession().delete(persistentObject);
    } catch (HibernateException ex) {
      this.log.error("Fail to delete", ex);
      throw new DAOException("Fail to delete", ex);
    }
  }
  
  public List queryByExample(Object persistentObject) throws DAOException {
    List objs = new ArrayList();
    





    return objs;
  }
  
  public void executeUpdateBySQL(String sql, Object[] valores) throws DAOException
  {
    try
    {
      Query queryObject = getCurrentSession().createQuery(sql);
      
      prepareQuery(queryObject);
      if (valores != null) {
        for (int i = 0; i < valores.length; i++) {
          if ((valores[i] instanceof String))
          {
            queryObject.setString(i, (String)valores[i]);
          }
          else if ((valores[i] instanceof Integer))
          {
            queryObject.setInteger(i, ((Integer)valores[i]).intValue());
          }
          else if ((valores[i] instanceof Date))
          {
            queryObject.setDate(i, (Date)valores[i]);
          }
          else
          {
            queryObject.setEntity(i, valores[i]);
          }
        }
      }
      queryObject.executeUpdate();
    }
    catch (Exception ex) {
      this.log.error("Fail to find all  objects", ex);
      throw new DAOException("Fail to find all  objects", ex);
    }
  }
  
  public List findAll(Class clazz) throws DAOException {
    List objs = new ArrayList();
    try {
      objs = 
        getCurrentSession().createQuery("from " + clazz.getName()).list();
    } catch (HibernateException ex) {
      this.log.error("Fail to find all  objects", ex);
      throw new DAOException("Fail to find all  objects", ex);
    }
    return objs;
  }
  
  public List findAll(Class clazz, List orders) throws DAOException {
    List objs = new ArrayList();
    Session session = getCurrentSession();
    try {
      Criteria criteria = session.createCriteria(clazz);
      for (Iterator iter = orders.iterator(); iter.hasNext();) {
        criteria.addOrder((Order)iter.next());
      }
      objs = criteria.list();
    } catch (HibernateException ex) {
      this.log.error("Fail to load all ", ex);
      throw new DAOException("Fail to load all", ex);
    }
    return objs;
  }
  
  public List findByProperty(Class clazz, Criterion restriction) throws DAOException
  {
    List objs = new ArrayList();
    try {
      objs = getCurrentSession().createCriteria(clazz).add(restriction).list();
    } catch (HibernateException ex) {
      this.log.error("Fail to find objects by property", ex);
      throw new DAOException("Fail to find objects by property", ex);
    }
    return objs;
  }
  
  public List findByProperty(Class clazz, Criterion restriction, Order orden) throws DAOException
  {
    List objs = new ArrayList();
    try {
      Session session = getCurrentSession();
      Criteria criteria = session.createCriteria(clazz);
      criteria.add(restriction);
      criteria.addOrder(orden);
      objs = criteria.list();
    } catch (HibernateException ex) {
      this.log.error("Fail to find objects by property", ex);
      throw new DAOException("Fail to find objects by property", ex);
    }
    return objs;
  }
  
  public List findByCriterions(Class clazz, List restrictions) throws DAOException
  {
    List objs = new ArrayList();
    try
    {
      Session session = getCurrentSession();
      Criteria criteria = session.createCriteria(clazz);
      Iterator it = restrictions.iterator();
      while (it.hasNext())
        criteria.add((Criterion)it.next());
      objs = criteria.list();
    } catch (HibernateException ex) {
      this.log.error("Fail to find objects by criterions", ex);
      throw new DAOException("Fail to find objects by criterions", ex);
    }
    return objs;
  }
  
  public List findByCriterions(Class clazz, List restrictions, List orders) throws DAOException
  {
    List objs = new ArrayList();
    try
    {
      Session session = getCurrentSession();
      Criteria criteria = session.createCriteria(clazz);
      Iterator it = restrictions.iterator();
      while (it.hasNext()) {
        criteria.add((Criterion)it.next());
      }
      for (Iterator iter = orders.iterator(); iter.hasNext();) {
        criteria.addOrder((Order)iter.next());
      }
      objs = criteria.list();
    } catch (HibernateException ex) {
      this.log.error("Fail to find objects by criterions", ex);
      throw new DAOException("Fail to find objects by criterions", ex);
    }
    return objs;
  }
  
  public List findByCriterions(Class clazz, List restrictions, int firstResult, int maxResult) throws DAOException
  {
    List objs = new ArrayList();
    try
    {
      Session session = getCurrentSession();
      Criteria criteria = session.createCriteria(clazz).setFirstResult(
        firstResult).setMaxResults(maxResult);
      Iterator it = restrictions.iterator();
      while (it.hasNext())
        criteria.add((Criterion)it.next());
      objs = criteria.list();
    } catch (HibernateException ex) {
      this.log.error("Fail to find objects by criterions", ex);
      throw new DAOException("Fail to find objects by criterions", ex);
    }
    return objs;
  }
  
  public List findBySQLQuery(String sqlQuery, String aliasName, Class clazz) throws DAOException
  {
    List result = new ArrayList();
    try {
      Session session = getCurrentSession();
      result = session.createSQLQuery(sqlQuery).addEntity(aliasName, 
        clazz).list();
    } catch (HibernateException ex) {
      this.log.error("Fail to execute query", ex);
      throw new DAOException("Fail to execute query", ex);
    }
    return result;
  }
  
  public List findBySQLQuery(String sqlQuery) throws DAOException
  {
    List result = new ArrayList();
    try {
      Session session = getCurrentSession();
      result = session.createSQLQuery(sqlQuery).list();
    } catch (HibernateException ex) {
      this.log.error("Fail to execute query", ex);
      throw new DAOException("Fail to execute query", ex);
    }
    return result;
  }
  
  public List findByHQLQuery(String hqlQuery) throws DAOException {
    List result = new ArrayList();
    try {
      Session session = getCurrentSession();
      result = session.createQuery(hqlQuery).list();
    } catch (HibernateException ex) {
      this.log.error("Fail to execute query", ex);
      throw new DAOException("Fail to execute query", ex);
    }
    return result;
  }
  
  public List findByNamedParam(String queryString, String paramName, Object value) throws DAOException
  {
    return findByNamedParam(queryString, new String[] { paramName }, 
      new Object[] { value });
  }
  
  public List findByNamedParam(String queryString, String paramName, Object value, int firstResult, int maxResult) throws DAOException
  {
    return findByNamedParam(queryString, new String[] { paramName }, 
      new Object[] { value }, firstResult, maxResult);
  }
  
  public List findByQuery(String queryString, String[] nombres, Object[] values) throws DAOException
  {
    try
    {
      Session session = getCurrentSession();
      Query queryObject = session.createQuery(queryString);
      
      prepareQuery(queryObject);
      if (values != null) {
        for (int i = 0; i < values.length; i++) {
          if ((values[i] instanceof String))
          {
            queryObject.setString(nombres[i], (String)values[i]);
          }
          else if ((values[i] instanceof Integer))
          {
            queryObject.setInteger(nombres[i], ((Integer)values[i]).intValue());
          }
          else if ((values[i] instanceof Date))
          {
            queryObject.setDate(nombres[i], (Date)values[i]);
          }
          else
          {
            queryObject.setEntity(nombres[i], values[i]);
          }
        }
      }
      
      return queryObject.list();
    }
    catch (HibernateException ex) {
      this.log.error("Fail to find", ex);
      throw new DAOException("Fail to find", ex);
    }
  }
  
  public List findByNamedParam(String queryString, String[] paramNames, Object[] values) throws DAOException
  {
    try
    {
      if (paramNames.length != values.length) {
        throw new IllegalArgumentException(
          "Length of paramNames array must match length of values array");
      }
      Session session = getCurrentSession();
      Query queryObject = session.createQuery(queryString);
      
      if (values != null) {
        for (int i = 0; i < values.length; i++) {
          applyNamedParameterToQuery(queryObject, paramNames[i], 
            values[i]);
        }
      }
      
      return queryObject.list();
    }
    catch (HibernateException ex) {
      this.log.error("Fail to find", ex);
      throw new DAOException("Fail to find", ex);
    }
  }
  
  public List findByNamedParam(String queryString, String[] paramNames, Object[] values, int firstResult, int maxResult)
    throws DAOException
  {
    if (paramNames.length != values.length) {
      throw new IllegalArgumentException(
        "Length of paramNames array must match length of values array");
    }
    try
    {
      Session session = getCurrentSession();
      Query queryObject = session.createQuery(queryString);
      queryObject.setFirstResult(firstResult);
      queryObject.setMaxResults(maxResult);
      prepareQuery(queryObject);
      if (values != null) {
        for (int i = 0; i < values.length; i++) {
          applyNamedParameterToQuery(queryObject, paramNames[i], 
            values[i]);
        }
      }
      return queryObject.list();
    } catch (HibernateException ex) {
      this.log.error("Fail to find by named param");
      throw new DAOException("Fail to find by named param", ex);
    }
  }
  
  public List findByValueBean(String queryString, Object valueBean) throws DAOException
  {
    Session session = getCurrentSession();
    try {
      Query queryObject = session.createQuery(queryString);
      prepareQuery(queryObject);
      queryObject.setProperties(valueBean);
      return queryObject.list();
    } catch (HibernateException ex) {
      this.log.error("Fail to find by valueBean", ex);
      throw new DAOException("Fail to find by valueBeam", ex);
    }
  }
  
  public List findByNamedQuery(String queryName) throws DAOException {
    return findByNamedQuery(queryName, null);
  }
  
  public List findByNamedQuery(String queryName, Object value) throws DAOException
  {
    return findByNamedQuery(queryName, new Object[] { value });
  }
  
  public List findByNamedQuery(String queryName, Object[] values) throws DAOException
  {
    Session session = getCurrentSession();
    try {
      Query queryObject = session.getNamedQuery(queryName);
      prepareQuery(queryObject);
      if (values != null) {
        for (int i = 0; i < values.length; i++) {
          queryObject.setParameter(i, values[i]);
        }
      }
      return queryObject.list();
    } catch (HibernateException ex) {
      this.log.error("Fail to find by Named query", ex);
      throw new DAOException("Fail to find by Named query", ex);
    }
  }
  
  public List findByNamedQueryAndNamedParam(String queryName, String paramName, Object value) throws DAOException
  {
    return findByNamedQueryAndNamedParam(queryName, 
      new String[] { paramName }, new Object[] { value });
  }
  
  public List findByNamedQueryAndNamedParam(String queryName, String[] paramNames, Object[] values)
    throws DAOException
  {
    if ((paramNames != null) && (values != null) && 
      (paramNames.length != values.length)) {
      throw new IllegalArgumentException(
        "Length of paramNames array must match length of values array");
    }
    Session session = getCurrentSession();
    try {
      Query queryObject = session.getNamedQuery(queryName);
      prepareQuery(queryObject);
      if (values != null) {
        for (int i = 0; i < values.length; i++) {
          applyNamedParameterToQuery(queryObject, paramNames[i], 
            values[i]);
        }
      }
      return queryObject.list();
    } catch (HibernateException ex) {
      this.log.error("Fail to find by Named query and Named Param", ex);
      throw new DAOException(
        "Fail to find by Named query and Named Param", ex);
    }
  }
  
  public List findByNamedQueryAndValueBean(String queryName, Object valueBean) throws DAOException
  {
    Session session = getCurrentSession();
    try {
      Query queryObject = session.getNamedQuery(queryName);
      prepareQuery(queryObject);
      queryObject.setProperties(valueBean);
      return queryObject.list();
    } catch (HibernateException ex) {
      this.log.error("Fail to find by Named query and value bean", ex);
      throw new DAOException(
        "Fail to find by Named query and value bean", ex);
    }
  }
  
  protected void prepareQuery(Query queryObject) {
    if (isCacheQueries()) {
      queryObject.setCacheable(true);
      if (getQueryCacheRegion() != null) {
        queryObject.setCacheRegion(getQueryCacheRegion());
      }
    }
  }
  
  protected void applyNamedParameterToQuery(Query queryObject, String paramName, Object value) throws HibernateException
  {
    if ((value instanceof Collection)) {
      queryObject.setParameterList(paramName, (Collection)value);
    } else if ((value instanceof Object[])) {
      queryObject.setParameterList(paramName, (Object[])value);
    } else {
      queryObject.setParameter(paramName, value);
    }
  }
  
  public boolean isCacheQueries() {
    return this.cacheQueries;
  }
  
  public String getQueryCacheRegion() {
    return this.queryCacheRegion;
  }
  
  public Serializable save(Object persistentObject) throws DAOException {
    try {
      return getCurrentSession().save(persistentObject);
    }
    catch (HibernateException ex) {
      this.log.error("Fail to save persistentObject", ex);
      throw new DAOException("Fail to save persistentObject", ex);
    }
  }
  
  public void saveOrUpdate(Object persistentObject) throws DAOException {
    try {
      getCurrentSession().setFlushMode(FlushMode.COMMIT);
      getCurrentSession().saveOrUpdate(persistentObject);
    } catch (HibernateException ex) {
      ex.printStackTrace();
      this.log.error("Fail to save or update persistentObject", ex);
      throw new DAOException("Fail to save or update persistentObject", 
        ex);
    }
    catch (IllegalStateException e) {
      e.printStackTrace();
    }
  }
  
  public List find(String queryString, int firstResult, int maxResult) throws DAOException
  {
    return find(queryString, null, firstResult, maxResult);
  }
  
  public List find(String queryString) throws DAOException {
    return find(queryString, null);
  }
  
  public List find(String queryString, Object value) throws DAOException {
    return find(queryString, new Object[] { value });
  }
  
  public List find(String queryString, Object[] values) throws DAOException
  {
    Session session = getCurrentSession();
    try {
      Query queryObject = session.createQuery(queryString);
      if (values != null) {
        for (int i = 0; i < values.length; i++) {
          queryObject.setParameter(i, values[i]);
        }
      }
      return queryObject.list();
    } catch (HibernateException ex) {
      this.log.error("Fail to find by query string", ex);
      throw new DAOException("Fail to find by query string", ex);
    }
  }
  
  public List find(String queryString, Object[] values, int firstResult, int maxResult) throws DAOException
  {
    Session session = getCurrentSession();
    try {
      Query queryObject = session.createQuery(queryString)
        .setFirstResult(firstResult).setMaxResults(maxResult);
      prepareQuery(queryObject);
      if (values != null) {
        for (int i = 0; i < values.length; i++) {
          queryObject.setParameter(i, values[i]);
        }
      }
      return queryObject.list();
    } catch (HibernateException ex) {
      this.log.error("Fail to find by query string", ex);
      throw new DAOException("Fail to find by query string", ex);
    }
  }
  
  public List loadAll(Class entityClass) throws DAOException {
    try {
      return 
        getCurrentSession().createQuery("from " + entityClass.getName()).list();
    } catch (HibernateException ex) {
      this.log.error("Fail to load all ", ex);
      throw new DAOException("Fail to load all", ex);
    }
  }
  
  public List loadAll(Class entityClass, int firstResult, int maxResult) throws DAOException
  {
    Session session = getCurrentSession();
    try {
      Criteria criteria = session.createCriteria(entityClass)
        .setFirstResult(firstResult).setMaxResults(maxResult);
      return criteria.list();
    } catch (HibernateException ex) {
      this.log.error("Fail to load all ", ex);
      throw new DAOException("Fail to load all", ex);
    }
  }
  
  private Session openSession() {
    return getCurrentSession();
  }
  

  public Object get(Class clase, Serializable valor)
  {
    Session session = getCurrentSession();
    try
    {
      return session.get(clase, valor);
    } catch (HibernateException ex) {
      this.log.error("Fail to load all ", ex);
      throw new DAOException("Fail to load all", ex);
    }
  }
  

  public Object load(Class clase, Serializable valor)
  {
    Session session = getCurrentSession();
    
    session.flush();
    try
    {
      return session.load(clase, valor);
    } catch (HibernateException ex) {
      this.log.error("Fail to load all ", ex);
      throw new DAOException("Fail to load all", ex);
    }
  }
  

  public Session getCurrentSession()
  {
    return this.sessionFactory.getCurrentSession();
  }
  
  @Transactional
  public void initialiceCollection(Set collections)
  {
    Hibernate.initialize(collections);
  }
  
  @Transactional
  public void initialiceCollection(List collections)
  {
    Hibernate.initialize(collections);
  }
  

  public void initialiceCollection(Object entity, Set collections)
  {
    if (getCurrentSession() != null) {
      try {
        getCurrentSession().refresh(entity);
        getCurrentSession().flush();
        getCurrentSession().merge(entity);
        collections.size();
      } catch (HibernateException e) {
        this.log.error("Fail to flush session", e);
        throw new DAOException("Fail to flush session", e);
      }
    }
  }
}
