package ar.edu.unq.epers.bichomon.backend.jdbc.dao.impl;

import ar.edu.unq.epers.bichomon.backend.jdbc.dao.EspecieDao;
import ar.edu.unq.epers.bichomon.backend.jdbc.service.runner.TransactionRunner;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class HibernateEspecieDao extends HibernateDAO<Especie> implements EspecieDao {

    public HibernateEspecieDao() { super(Especie.class); }

    @Override
    public void guardar(Especie especie) {

    }

    @Override
    public void actualizar(Especie especie) {

    }

    @Override
    public List<Especie> recuperarTodos() {
        return null;
    }

    public Especie recuperarEspecieLider() {
        Session session = TransactionRunner.getCurrentSession();

        String hql = "select d.bichoCampeon.especie from Dojo d " +
                "group by d.bichoCampeon.especie order by count(distinct d.bichoCampeon) desc ";

        Query<Especie> query = session.createQuery(hql, Especie.class);
        query.setMaxResults(1);

        return query.getSingleResult();
    }

    @Override
    public Especie recuperar(String nombre_especie) {
        Session session = TransactionRunner.getCurrentSession();
        return session.get(Especie.class, nombre_especie);
    }

    @Override
    public List<Especie> recuperarEspeciesMasPopulares() {
        Session session = TransactionRunner.getCurrentSession();

        String query = "select especie from Entrenador entrenador JOIN entrenador.bichos bicho JOIN bicho.especie especie " +
                "GROUP BY especie ORDER BY count(bicho) DESC";

        Query<Especie> resultQuery = session.createQuery(query);
        resultQuery.setMaxResults(10);

        return resultQuery.getResultList();
    }

    @Override
    public List<Especie> recuperarEspeciesMenosPopulares() {
        Session session = TransactionRunner.getCurrentSession();

        String query = "select especie from Guarderia guarderia JOIN guarderia.abandonados bicho JOIN bicho.especie especie " +
                "GROUP BY especie ORDER BY count(bicho) DESC";


        Query<Especie> resultQuery = session.createQuery(query);
        resultQuery.setMaxResults(10);

        return resultQuery.getResultList();
    }
}