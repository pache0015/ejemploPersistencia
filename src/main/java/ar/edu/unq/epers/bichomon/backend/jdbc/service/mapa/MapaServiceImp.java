package ar.edu.unq.epers.bichomon.backend.jdbc.service.mapa;

import ar.edu.unq.epers.bichomon.backend.jdbc.dao.BichoDao;
import ar.edu.unq.epers.bichomon.backend.jdbc.dao.EntrenadorDao;
import ar.edu.unq.epers.bichomon.backend.jdbc.dao.UbicacionDao;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Dojo;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.NoHayCampeonException;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;
import ar.edu.unq.epers.bichomon.backend.neo4j.CaminoMuyCostoso;
import ar.edu.unq.epers.bichomon.backend.neo4j.Neo4jDAO;

import static ar.edu.unq.epers.bichomon.backend.jdbc.service.runner.TransactionRunner.run;

public class MapaServiceImp implements MapaService {

    private EntrenadorDao entrenadorDao;
    private BichoDao bichoDao;
    private UbicacionDao ubicacionDao;
    private Neo4jDAO neo4jDAO;

    public void setEntrenadorDao(EntrenadorDao entrenadorDao) {
        this.entrenadorDao = entrenadorDao;
    }

    public void setBichoDao(BichoDao bichoDao) {
        this.bichoDao = bichoDao;
    }

    public void setUbicacionDao(UbicacionDao ubicacionDao) {
        this.ubicacionDao = ubicacionDao;
    }

    public void setNeo4jDAO(Neo4jDAO neo4jDAO) {
        this.neo4jDAO = neo4jDAO;
    }

    @Override
    public void mover(String entrenador, String ubicacion) {
        run(() -> {
            Entrenador entrenadorRecuperado = this.entrenadorDao.recuperar(entrenador);
            Ubicacion ubicacionRecuperada = this.ubicacionDao.recuperar(ubicacion);
            neo4jDAO.assertPuedeMover(entrenadorRecuperado, ubicacion);

            entrenadorRecuperado.ubicarseEn(ubicacionRecuperada);

            entrenadorDao.actualizarUbicacion(entrenadorRecuperado, ubicacionRecuperada);
        });
    }

    public void moverMasCorto(String unEntrenador, String ubicacion) {
        run(() -> {
            Entrenador entrenador = entrenadorDao.recuperar(unEntrenador);
            Ubicacion ubicacionLlegada = ubicacionDao.recuperar(ubicacion);
            String salida = entrenador.getUbicacionActual().getNombre();
            int monedas = entrenador.getCantidadDeMonedas();
            Integer precio = neo4jDAO.precioCaminoMasCorto(salida, ubicacion);
            neo4jDAO.assertEntrenadorPuedePagar(entrenador, precio);

            if (monedas >= precio) {
                entrenador.ubicarseEn(ubicacionLlegada);
                entrenador.setCantidadDeMonedas(monedas - precio);
            } else {
                throw new CaminoMuyCostoso();
            }
        });
    }

    @Override
    public int cantidadDeEntrenadores(String ubicacion) {
        return run(() -> {
            return entrenadorDao.recuperarTodosEnUbicacion(ubicacion).size();
        });
    }

    @Override
    public Bicho campeon(String nombreDojo) {
        return run(() -> {
            Dojo dojo = ubicacionDao.recuperarDojo(nombreDojo);
            Bicho posibleCampeon = dojo.getBichoCAmpeon();
            assertHayCampeon(posibleCampeon);
            return posibleCampeon;
        });

    }

    public void assertHayCampeon(Bicho posibleCampeon) {
        if (posibleCampeon == null) {
            throw new NoHayCampeonException();
        }
    }

    @Override
    public Bicho campeonHistorico(String dojo) {
        return run(() -> {
            return ubicacionDao.recuperarIdCampeonHistoricoEnDojo(dojo);
        });
    }

    public void crearUbicacion(Ubicacion ubicacion) {
        run(() -> {
            ubicacionDao.guardar(ubicacion);
        });
    }
}
