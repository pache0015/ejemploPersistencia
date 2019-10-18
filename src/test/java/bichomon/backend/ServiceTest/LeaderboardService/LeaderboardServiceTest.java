package bichomon.backend.ServiceTest.LeaderboardService;

import ar.edu.unq.epers.bichomon.backend.jdbc.dao.impl.HibernateEntrenadorDao;
import ar.edu.unq.epers.bichomon.backend.jdbc.dao.impl.HibernateEspecieDao;
import ar.edu.unq.epers.bichomon.backend.jdbc.dao.impl.HibernateUbicacionDao;
import ar.edu.unq.epers.bichomon.backend.jdbc.service.leaderboard.LeaderboardServiceImpl;
import ar.edu.unq.epers.bichomon.backend.jdbc.service.runner.TransactionRunner;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.especie.TipoBicho;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Dojo;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;
import bichomon.backend.factory.Factory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static bichomon.backend.factory.TestFactory.nuevoBicho;
import static bichomon.backend.factory.TestFactory.nuevoEntrenador;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class LeaderboardServiceTest {

    private LeaderboardServiceImpl service;
    private HibernateEntrenadorDao entrenadorDao;
    private HibernateUbicacionDao ubicacionDao;
    private HibernateEspecieDao especieDao;
    private Ubicacion dojo;
    private Entrenador entrenador;
    private Bicho bicho;

    @Before
    public void setup() {
        service = new LeaderboardServiceImpl();
        entrenadorDao = new HibernateEntrenadorDao();
        ubicacionDao = new HibernateUbicacionDao();
        especieDao = new HibernateEspecieDao();
        service.setEntrenadorDao(entrenadorDao);
        service.setUbicacionDao(ubicacionDao);
        service.setEspecieDao(especieDao);
        dojo = new Dojo("Alto dojo");
        bicho = nuevoBicho();
        bicho.setEnergia(0.0);
        entrenador = nuevoEntrenador("Hola", dojo);
        entrenador.capturarBichomon(bicho, 30);
    }

    @After
    public void cleanup() {
        TransactionRunner.run(() -> {
            especieDao.borrarTodo();
        });
    }

    @Test
    public void se_retornan_campeones_luego_de_que_un_dojo_declare_los_suyos() {
        dojo.declararCampeones(entrenador, bicho);
        service.guardarEntrenador(entrenador);

        List<Entrenador> campeones = service.campeones();

        assertFalse(campeones.isEmpty());
    }

    @Test
    public void se_retorna_la_especie_lider() {
        Dojo primerDojo = new Dojo("Primero");
        Dojo segundoDojo = new Dojo("Segundo");
        Dojo tercerDojo = new Dojo("Tercero");
        Dojo cuartoDojo = new Dojo("Cuarto");
        Dojo quintoDojo = new Dojo("Quinto");

        Especie especie = Factory.especieSinEvolucion("Otra Especie", TipoBicho.TIERRA);
        Bicho segundoBicho = Factory.bicho(especie);
        Bicho tercerBicho = Factory.bicho(especie);

        entrenador.capturarBichomon(segundoBicho, 0);
        entrenador.capturarBichomon(tercerBicho, 0);

        primerDojo.declararCampeones(entrenador, bicho);
        segundoDojo.declararCampeones(entrenador, bicho);
        tercerDojo.declararCampeones(entrenador, segundoBicho);
        cuartoDojo.declararCampeones(entrenador, tercerBicho);
        quintoDojo.declararCampeones(entrenador, bicho);
        service.guardarEntrenador(entrenador);
        service.guardarUbicacion(segundoDojo);
        service.guardarUbicacion(tercerDojo);
        service.guardarUbicacion(cuartoDojo);
        service.guardarUbicacion(quintoDojo);

        Especie especieLider = service.especieLider();

        assertEquals("Otra Especie", especieLider.getNombre());
    }

    @Test
    public void se_retorna_la_especie_lider_2() {
        Dojo primerDojo = new Dojo("Primero");
        Dojo segundoDojo = new Dojo("Segundo");
        Dojo tercerDojo = new Dojo("Tercero");
        Dojo cuartoDojo = new Dojo("Cuarto");
        Dojo quintoDojo = new Dojo("Quinto");
        Dojo sextoDojo = new Dojo("Sexto");
        Dojo septimoDojo = new Dojo("Septimo");
        Dojo octavoDOjo = new Dojo("Octavo");

        Especie especie = Factory.especieSinEvolucion("Otra Especie", TipoBicho.TIERRA);
        Bicho segundoBicho = Factory.bicho(especie);
        Bicho tercerBicho = Factory.bicho(especie);
        Especie asd = Factory.especieSinEvolucion("Otra otra especie", TipoBicho.AIRE);
        Bicho cuartoBicho = Factory.bicho(especie);
        Bicho quintoBicho = Factory.bicho(asd);
        Bicho sextoBicho = Factory.bicho(asd);

        entrenador.capturarBichomon(segundoBicho, 0);
        entrenador.capturarBichomon(tercerBicho, 0);
        sextoDojo.declararCampeones(entrenador, cuartoBicho);

        tercerDojo.declararCampeones(entrenador, segundoBicho);
        cuartoDojo.declararCampeones(entrenador, tercerBicho);

        primerDojo.declararCampeones(entrenador, bicho);
        segundoDojo.declararCampeones(entrenador, bicho);
        quintoDojo.declararCampeones(entrenador, bicho);

        septimoDojo.declararCampeones(entrenador, quintoBicho);
        octavoDOjo.declararCampeones(entrenador, sextoBicho);

        service.guardarEntrenador(entrenador);
        service.guardarUbicacion(segundoDojo);
        service.guardarUbicacion(tercerDojo);
        service.guardarUbicacion(cuartoDojo);
        service.guardarUbicacion(quintoDojo);
        service.guardarUbicacion(sextoDojo);
        service.guardarUbicacion(septimoDojo);
        service.guardarUbicacion(octavoDOjo);

        Especie especieLider = service.especieLider();

        assertEquals("Otra Especie", especieLider.getNombre());
    }

    @Test
    public void se_retornan_los_lideres() {
        Especie especie = Factory.especieSinEvolucion("Especie", TipoBicho.TIERRA);
        Bicho primerBicho = Factory.bicho(especie);
        primerBicho.setEnergia(100.0);
        Bicho segundoBicho = Factory.bicho(especie);
        segundoBicho.setEnergia(50.0);
        Bicho tercerBicho = Factory.bicho(especie);
        tercerBicho.setEnergia(30.0);

        Entrenador otroEntrenador = nuevoEntrenador("Guachin", dojo);

        entrenador.capturarBichomon(primerBicho, 10);
        otroEntrenador.capturarBichomon(segundoBicho, 20);
        otroEntrenador.capturarBichomon(tercerBicho, 0);

        service.guardarEntrenador(entrenador);
        service.guardarEntrenador(otroEntrenador);

        List<Entrenador> lideres = service.lideres();

        assertEquals(lideres.get(0).getNombre(), "Hola");
        assertEquals(lideres.get(1).getNombre(), "Guachin");
    }
}
