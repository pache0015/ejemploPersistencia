package bichomon.backend.ServiceTest.LeaderboardService;

import static org.junit.Assert.*;

import ar.edu.unq.epers.bichomon.backend.jdbc.dao.impl.HibernateEntrenadorDao;
import ar.edu.unq.epers.bichomon.backend.jdbc.dao.impl.HibernateUbicacionDao;
import ar.edu.unq.epers.bichomon.backend.jdbc.service.leaderboard.LeaderboardServiceImpl;
import ar.edu.unq.epers.bichomon.backend.jdbc.service.runner.SessionFactoryProvider;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Dojo;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;

import static bichomon.backend.ServiceTest.TestFactory.nuevoBicho;
import static bichomon.backend.ServiceTest.TestFactory.nuevoEntrenador;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class LeaderboardServiceTest {

    private LeaderboardServiceImpl service;
    private HibernateEntrenadorDao entrenadorDao;
    private HibernateUbicacionDao ubicacionDao;
    private Ubicacion dojo;
    private Entrenador entrenador;
    private Bicho bicho;

    @Before
    public void setup() {
        service = new LeaderboardServiceImpl();
        entrenadorDao = new HibernateEntrenadorDao();
        ubicacionDao = new HibernateUbicacionDao();
        service.setEntrenadorDao(entrenadorDao);
        service.setUbicacionDao(ubicacionDao);
        dojo = new Dojo("Alto dojo");
        bicho = nuevoBicho();
        entrenador = nuevoEntrenador("Hola", dojo);
        entrenador.capturarBichomon(bicho, 1);
    }

    @After
    public void cleanup() {
        SessionFactoryProvider.destroy();
    }

    @Test
    public void se_retornan_campeones_luego_de_que_un_dojo_declare_los_suyos() {
        dojo.declararCampeones(entrenador, bicho);
        service.guardarEntrenador(entrenador);

        List<Entrenador> campeones = service.campeones();

        assertFalse(campeones.isEmpty());
    }
}
