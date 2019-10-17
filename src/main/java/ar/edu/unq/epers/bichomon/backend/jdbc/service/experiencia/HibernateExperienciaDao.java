package ar.edu.unq.epers.bichomon.backend.jdbc.service.experiencia;

import ar.edu.unq.epers.bichomon.backend.jdbc.dao.impl.HibernateDAO;
import ar.edu.unq.epers.bichomon.backend.jdbc.service.runner.TransactionRunner;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Experiencia;
import org.hibernate.Session;

public class HibernateExperienciaDao extends HibernateDAO<Experiencia> implements ExperienciaDao {

    private HibernateExperienciaDao(){ super(Experiencia.class);}

    @Override
    public Experiencia recuperar(Long experiencia_id){
        Session session = TransactionRunner.getCurrentSession();
        return session.get(Experiencia.class, experiencia_id);
    }

}
