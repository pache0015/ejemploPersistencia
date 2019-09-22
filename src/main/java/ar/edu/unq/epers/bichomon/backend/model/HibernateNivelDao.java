package ar.edu.unq.epers.bichomon.backend.model;

import ar.edu.unq.epers.bichomon.backend.dao.impl.HibernateDAO;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Nivel;

public class HibernateNivelDao extends HibernateDAO<Nivel> implements NivelDao  {

    public HibernateNivelDao() { super(Nivel.class); }


}
