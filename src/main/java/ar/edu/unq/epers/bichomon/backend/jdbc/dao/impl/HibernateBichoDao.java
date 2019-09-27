package ar.edu.unq.epers.bichomon.backend.jdbc.dao.impl;

import ar.edu.unq.epers.bichomon.backend.jdbc.dao.BichoDao;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.jdbc.service.runner.TransactionRunner;
import org.hibernate.Session;

public class HibernateBichoDao extends HibernateDAO<Bicho> implements BichoDao {

    public HibernateBichoDao() { super(Bicho.class); }

    @Override
    public Bicho recuperar(Long id) {
        Session session = TransactionRunner.getCurrentSession();
        return session.get(Bicho.class, id);
    }

}
