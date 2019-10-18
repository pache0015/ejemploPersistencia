package ar.edu.unq.epers.bichomon.backend.jdbc.dao.impl;

import ar.edu.unq.epers.bichomon.backend.jdbc.dao.UbicacionDao;
import ar.edu.unq.epers.bichomon.backend.jdbc.service.runner.TransactionRunner;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Dojo;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Guarderia;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;
import org.hibernate.Session;
import org.hibernate.query.Query;


public class HibernateUbicacionDao extends HibernateDAO<Ubicacion> implements UbicacionDao {

    public HibernateUbicacionDao() {
        super(Ubicacion.class);
    }

    @Override
    public Ubicacion recuperar(String nombre) {
        Session session = TransactionRunner.getCurrentSession();
        return session.get(Ubicacion.class, nombre);
    }

    @Override
    public Dojo recuperarDojo(String dojo) {
        Session session = TransactionRunner.getCurrentSession();
        return session.get(Dojo.class, dojo);
    }

    @Override
    public Bicho recuperarIdCampeonHistoricoEnDojo(String dojo) {
        Session session = TransactionRunner.getCurrentSession();
        String hql = "select f.bichoCampeon from FichaDeCampeon f where f.dojo.nombre = :nombre " +
                "order by (DATEDIFF(IFNULL(f.fechaFin, current_date ), f.fechaInicio)) asc ";
        Query<Bicho> query = session.createQuery(hql, Bicho.class);
        query.setParameter("nombre", dojo);

        query.setMaxResults(1);

        return query.getSingleResult();
    }
    @Override
    public Guarderia recuperarGuarderia(String nombre_guarderia) {
        Session session = TransactionRunner.getCurrentSession();
        return session.get(Guarderia.class, nombre_guarderia);
    }


}
