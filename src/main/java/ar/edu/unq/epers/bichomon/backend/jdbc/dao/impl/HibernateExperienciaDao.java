package ar.edu.unq.epers.bichomon.backend.jdbc.dao.impl;


import ar.edu.unq.epers.bichomon.backend.jdbc.dao.ExperienciaDao;
import ar.edu.unq.epers.bichomon.backend.jdbc.service.runner.TransactionRunner;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Experiencia;
import org.hibernate.Session;

public class HibernateExperienciaDao extends HibernateDAO<Experiencia> implements ExperienciaDao {

    public HibernateExperienciaDao() {super(Experiencia.class);}

    @Override
    public Experiencia recuperar(Long id) {
        Session session = TransactionRunner.getCurrentSession();
        return session.get(Experiencia.class, id);
    }

}
