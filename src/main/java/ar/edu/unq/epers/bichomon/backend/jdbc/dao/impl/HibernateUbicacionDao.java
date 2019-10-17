package ar.edu.unq.epers.bichomon.backend.jdbc.dao.impl;

import ar.edu.unq.epers.bichomon.backend.jdbc.dao.UbicacionDao;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;

public class HibernateUbicacionDao extends HibernateDAO<Ubicacion> implements UbicacionDao {
    public HibernateUbicacionDao() {
        super(Ubicacion.class);
    }
}
