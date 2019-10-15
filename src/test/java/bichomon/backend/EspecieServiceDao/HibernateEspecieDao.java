package bichomon.backend.EspecieServiceDao;

import ar.edu.unq.epers.bichomon.backend.jdbc.dao.impl.HibernateDAO;
import ar.edu.unq.epers.bichomon.backend.jdbc.service.especie.EspecieDao;
import ar.edu.unq.epers.bichomon.backend.jdbc.service.runner.TransactionRunner;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class HibernateEspecieDao extends HibernateDAO<Especie> implements EspecieDao {

    public HibernateEspecieDao() {
        super(Especie.class);
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
        resultQuery.setMaxResults(2);

        return resultQuery.getResultList();
    }

    @Override
    public List<Especie> recuperarEspeciesMenosPopulares() {
        Session session = TransactionRunner.getCurrentSession();

        String query = "select especie from Guarderia guarderia JOIN guarderia.abandonados bicho JOIN bicho.especie especie " +
                "GROUP BY especie ORDER BY count(bicho) DESC";


        Query<Especie> resultQuery = session.createQuery(query);
        resultQuery.setMaxResults(2);

        return resultQuery.getResultList();
    }
}
