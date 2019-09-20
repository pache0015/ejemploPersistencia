package ar.edu.unq.epers.bichomon.backend.dao.impl;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.service.runner.TransactionRunner;
import org.hibernate.Session;

public class HibernateBichoDao extends HibernateDAO<Bicho> implements BichoDao{

    public HibernateBichoDao() { super(Bicho.class); }

    @Override
    public Bicho recuperar(Long id) {
        Session session = TransactionRunner.getCurrentSession();
        return session.get(Bicho.class, id);
    }

}
