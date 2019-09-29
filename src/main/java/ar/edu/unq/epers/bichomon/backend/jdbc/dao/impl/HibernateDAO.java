package ar.edu.unq.epers.bichomon.backend.jdbc.dao.impl;

import ar.edu.unq.epers.bichomon.backend.jdbc.service.runner.TransactionRunner;
import org.hibernate.Session;

public class HibernateDAO<T> {

    private Class<T> entityType;

    public HibernateDAO(Class<T> entityType){
        this.entityType = entityType;
    }

    public void guardar(T item) {
        Session session = TransactionRunner.getCurrentSession();
        session.save(item);
    }

    public T recuperar(Long id) {
        Session session = TransactionRunner.getCurrentSession();
        return session.get(entityType, id);
    }
}


