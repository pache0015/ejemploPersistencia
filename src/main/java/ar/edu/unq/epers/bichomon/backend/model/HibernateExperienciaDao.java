package ar.edu.unq.epers.bichomon.backend.model;


import ar.edu.unq.epers.bichomon.backend.jdbc.dao.impl.HibernateDAO;
import ar.edu.unq.epers.bichomon.backend.jdbc.service.runner.TransactionRunner;
import org.hibernate.Session;

public class HibernateExperienciaDao extends HibernateDAO<Experiencia> implements ExperienciaDao {

    public HibernateExperienciaDao() {super(Experiencia.class);}

    @Override
    public Experiencia recuperar(Long id) {
        Session session = TransactionRunner.getCurrentSession();
        return session.get(Experiencia.class, id);
    }

}
