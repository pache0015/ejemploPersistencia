package ar.edu.unq.epers.bichomon.backend.jdbc.service.data;

import ar.edu.unq.epers.bichomon.backend.dao.impl.JDBCDataDAO;

public class DataServiceImpl implements DataService {

    private JDBCDataDAO dao = new JDBCDataDAO();

    @Override
    public void eliminarDatos() {
        dao.eliminarDatos();
    }

    @Override
    public void crearSetDatosIniciales() {
        dao.crearDatosIniciales();
    }
}