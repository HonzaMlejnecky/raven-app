package com.upce.raven.dao.bean;

import com.upce.raven.dao.*;
import org.hibernate.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.transaction.annotation.*;

import java.util.*;

public abstract class GenericDAOBean<T, Long> implements GenericDAO {

    private Class<T> type;

    @Autowired
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public GenericDAOBean(Class<T> type) {
        this.type = type;
    }

    // Not showing implementations of getSession() and setSessionFactory()
    public Session getSession() {
        Session session = sessionFactory.getCurrentSession();
        return session;
    }

    @Transactional(readOnly = false, rollbackFor = RuntimeException.class)
    public Long create(Object o) {
        return (Long) getSession().save(o);
    }

    @Transactional(readOnly = false, rollbackFor = RuntimeException.class)
    public void update(Object o) {
        getSession().update(o);
    }

    @Transactional(readOnly = true)
    public T read(Object id) {
        return (T) getSession().get(type, id);
    }

    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    public List<T> read() {
        return (List<T>) getSession().createQuery(String.valueOf(type)).list();
    }

    @Transactional(readOnly = false, rollbackFor = RuntimeException.class)
    public void delete(Object id) {
        T o = getSession().load(type, id);
        getSession().delete(o);
    }

}
