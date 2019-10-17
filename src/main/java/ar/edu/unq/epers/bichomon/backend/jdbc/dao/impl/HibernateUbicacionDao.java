package ar.edu.unq.epers.bichomon.backend.jdbc.dao.impl;

import ar.edu.unq.epers.bichomon.backend.jdbc.dao.UbicacionDao;
import ar.edu.unq.epers.bichomon.backend.jdbc.service.runner.TransactionRunner;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Guarderia;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;
import org.hibernate.Session;

public class HibernateUbicacionDao extends HibernateDAO<Ubicacion> implements UbicacionDao {
    public HibernateUbicacionDao() {
        super(Ubicacion.class);
    }

    @Override
    public Guarderia recuperarGuarderia(String nombre_guarderia) {
        Session session = TransactionRunner.getCurrentSession();
        return session.get(Guarderia.class, nombre_guarderia);
    }
}
