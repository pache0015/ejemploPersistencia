package ar.edu.unq.epers.bichomon.backend.model;

import ar.edu.unq.epers.bichomon.backend.dao.impl.HibernateDAO;
import ar.edu.unq.epers.bichomon.backend.model.EntrenadorDao;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Nivel;
import ar.edu.unq.epers.bichomon.backend.service.runner.TransactionRunner;
import org.hibernate.Session;

import javax.persistence.Query;

public class HibernateEntrenadorDao extends HibernateDAO<Entrenador> implements EntrenadorDao {


    public HibernateEntrenadorDao() { super(Entrenador.class); }

    @Override
    public Nivel getNivel(Integer puntosDeExperiencia) {
        Session session = TransactionRunner.getCurrentSession();
        String hql = "SELECT * FROM Nivel AS niveles " +
                     "WHERE niveles.experienciaMinima >= ("+puntosDeExperiencia+") AND " +
                     "niveles.experienciaMaxima <= ("+puntosDeExperiencia+")";

        Query query = session.createQuery(hql,  Nivel.class);
        query.setMaxResults(1);
        System.out.println(query.getSingleResult());
        return new Nivel();
    }

    @Override
    public Entrenador recuperar(Long id) {
        Session session = TransactionRunner.getCurrentSession();
        return session.get(Entrenador.class, id);
    }
}
