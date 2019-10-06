package ar.edu.unq.epers.bichomon.backend.jdbc.dao.impl;

import ar.edu.unq.epers.bichomon.backend.jdbc.dao.EspecieDao;
import ar.edu.unq.epers.bichomon.backend.jdbc.service.runner.TransactionRunner;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class HibernateEspecieDao implements EspecieDao {
    @Override
    public void guardar(Especie especie) {

    }

    @Override
    public void actualizar(Especie especie) {

    }

    @Override
    public Especie recuperar(String nombreEspecie) {
        return null;
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
}