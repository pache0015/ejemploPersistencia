package ar.edu.unq.epers.bichomon.backend.jdbc.service.especie;

import ar.edu.unq.epers.bichomon.backend.jdbc.dao.impl.HibernateDAO;
import ar.edu.unq.epers.bichomon.backend.jdbc.service.runner.TransactionRunner;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Guarderia;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class HibernateGuarderiaDao extends HibernateDAO<Guarderia> implements GuarderiaDao {

    public HibernateGuarderiaDao() { super(Guarderia.class); }

    @Override
    public Guarderia recuperar(String nombre_guarderia) {
        Session session = TransactionRunner.getCurrentSession();
        return session.get(Guarderia.class, nombre_guarderia);
    }

    @Override
    public List<Bicho> recuperarEspeciesMenosPopulares() {
        Session session = TransactionRunner.getCurrentSession();
        String hqlQuery = "select b.especie from Bicho b " +
                "group by b.especie order by count(b.especie.cantidadBichos) DESC";

        Query<Bicho> query = session.createQuery(hqlQuery);
        query.setMaxResults(10);
        return query.getResultList();
    }
}
