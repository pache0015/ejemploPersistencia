package ar.edu.unq.epers.bichomon.backend.jdbc.dao.impl;

import ar.edu.unq.epers.bichomon.backend.jdbc.dao.EntrenadorDao;
import ar.edu.unq.epers.bichomon.backend.jdbc.service.runner.TransactionRunner;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;


public class HibernateEntrenadorDao extends HibernateDAO<Entrenador> implements EntrenadorDao {


    public HibernateEntrenadorDao() { super(Entrenador.class); }

    @Override
    public Entrenador recuperar(String nombre_entrenador) {
        Session session = TransactionRunner.getCurrentSession();
        return session.get(Entrenador.class, nombre_entrenador);
    }

    @Override
    public List<Entrenador> recuperarCampeones() {
        Session session = TransactionRunner.getCurrentSession();
/*
        String hql = "select e from Entrenador e "
                + "inner join Dojo d "
                + "inner join FichaDeCampeon f "
                + "order by f.fechaInicio asc";
*/

        String hql = "select f.campeon from FichaDeCampeon f "
                + "where f.fechaFin is null "
                + "order by fechaInicio asc";

        Query<Entrenador> query = session.createQuery(hql,  Entrenador.class);
        query.setMaxResults(10);

        return query.getResultList();
    }
}