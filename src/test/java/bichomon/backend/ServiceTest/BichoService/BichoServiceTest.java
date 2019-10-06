package bichomon.backend.ServiceTest.BichoService;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;

import ar.edu.unq.epers.bichomon.backend.jdbc.dao.BichoDao;
import ar.edu.unq.epers.bichomon.backend.jdbc.dao.EntrenadorDao;
import ar.edu.unq.epers.bichomon.backend.jdbc.dao.impl.HibernateBichoDao;
import ar.edu.unq.epers.bichomon.backend.jdbc.service.bicho.BichoServiceImp;
import ar.edu.unq.epers.bichomon.backend.jdbc.dao.impl.HibernateEntrenadorDao;
import ar.edu.unq.epers.bichomon.backend.jdbc.service.runner.SessionFactoryProvider;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.condicion.CondicionBasadaEnEnergia;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Nivel;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.ProveedorDeNiveles;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.especie.TipoBicho;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

public class BichoServiceTest {

    Ubicacion guarderia;
    Nivel nivel;
    ProveedorDeNiveles proveedor;
    Entrenador entrenador;
    Especie especie;
    Especie reptilmon;
    Bicho bicho;
    BichoServiceImp bichoService;
    BichoDao bichoDao;
    EntrenadorDao entrenadorDao;

    @Before
    public void setUp(){
        guarderia = new Guarderia("guarderia");
        nivel = new Nivel(2, 1,99);
        List niveles = new ArrayList<Nivel>();
        niveles.add(nivel);
        proveedor = new ProveedorDeNiveles(niveles);
        reptilmon = new Especie("reptilmon", TipoBicho.TIERRA);
        especie = new Especie("especiemon", TipoBicho.TIERRA, reptilmon);
        bicho = new Bicho(especie);
        entrenador = new Entrenador("ASH", null ,proveedor);
        bichoService = Mockito.spy(new BichoServiceImp());
        bichoDao = new HibernateBichoDao();
        entrenadorDao = new HibernateEntrenadorDao();

        bichoService.setBichoDao(bichoDao);
        bichoService.setEntrenadorDao(entrenadorDao);
    }

    @After
    public void cleanup() {
        SessionFactoryProvider.destroy();
    }

    @Test
    public void se_busca_un_bicho_por_nombre_de_entrenador() {
        Pueblo pueblo = new Pueblo("Pueblo");
        pueblo.agregarEspecie(especie, 100);
        entrenador.moverseA(pueblo);
        bichoService.guardarEntrenador(entrenador);

        BusquedaExitosa busquedaExitosa = Mockito.mock(BusquedaExitosa.class);
        Mockito.when(busquedaExitosa.busquedaExitosa()).thenReturn(true);
        doReturn(busquedaExitosa).when(bichoService).getBusqueda();

        Bicho bichoRecuperado = bichoService.buscar(entrenador.getNombre());

        assertEquals(bichoRecuperado.getEspecie().getNombre(), bicho.getEspecie().getNombre());
    }

    @Test(expected = ErrorAbandonoImposible.class)
    public void unEntrenadorNoPuedeAbandonarASuBichoEnUnDojo() {
        Dojo dojo = new Dojo("Dojo");
        entrenador.moverseA(dojo);
        entrenador.capturarBichomon(bicho, 10);

        bichoService.guardarEntrenador(entrenador);

        bichoService.abandonar(entrenador.getNombre(), bicho.getId());
    }

    @Test(expected = ErrorAbandonoImposible.class)
    public void unEntrenadorNoPuedeAbandonarASuBichoEnUnPueblo() {
        Pueblo pueblo = new Pueblo("Pueblo");
        entrenador.moverseA(pueblo);
        entrenador.capturarBichomon(bicho, 10);

        bichoService.setBichoDao(bichoDao);
        bichoService.setEntrenadorDao(entrenadorDao);

        bichoService.guardarEntrenador(entrenador);

        bichoService.abandonar(entrenador.getNombre(), bicho.getId());
    }

    @Test(expected = ErrorAbandonoImposible.class)
    public void unEntrenadorNoPuedeAbandonarASuBichoEnUnaGuarderiaSiEsElUnicoQueTiene() {
        Guarderia guaderia = new Guarderia("Guarderia");
        entrenador.moverseA(guaderia);
        entrenador.capturarBichomon(bicho, 10);

        bichoService.setBichoDao(bichoDao);
        bichoService.setEntrenadorDao(entrenadorDao);

        bichoService.guardarEntrenador(entrenador);

        bichoService.abandonar(entrenador.getNombre(), bicho.getId());
    }

    @Test
    public void unEntrenadorPuedeAbandonarASuBichoEnUnaGuarderiaSiTieneOtro() {
        Guarderia guaderia = new Guarderia("Guarderia");
        entrenador.moverseA(guaderia);
        entrenador.capturarBichomon(bicho, 10);
        Bicho otroBicho = new Bicho(especie);
        entrenador.capturarBichomon(otroBicho, 10);

        bichoService.setBichoDao(bichoDao);
        bichoService.setEntrenadorDao(entrenadorDao);

        bichoService.guardarEntrenador(entrenador);

        bichoService.abandonar(entrenador.getNombre(), bicho.getId());
    }

    @Test
    public void siUnBichoNoCumpleLaCondicionDeEvolucionNoPuedeEvolucionar(){
        bicho.setEnergia(1d);
        bicho.setCondicionDeEvolucion(new CondicionBasadaEnEnergia(3));
        entrenador.capturarBichomon(bicho, 10);
        bichoService.guardarEntrenador(entrenador);

        assertFalse(bichoService.puedeEvolucionar(entrenador.getNombre(), bicho.getId()));
    }
    @Test
    public void unBichoSabeSiPuedeEvolucionarONo(){
        bicho.setEnergia(10d);
        bicho.setCondicionDeEvolucion(new CondicionBasadaEnEnergia(3));
        entrenador.capturarBichomon(bicho, 10);

        bichoService.guardarEntrenador(entrenador);

        assertTrue(bichoService.puedeEvolucionar(entrenador.getNombre(), bicho.getId()));
    }

    @Test
    public void unBichoSiCumpleLaCondicionDeEvolucionPuedeEvolucionar(){
        bicho.setEnergia(10d);
        bicho.setCondicionDeEvolucion(new CondicionBasadaEnEnergia(3));
        entrenador.capturarBichomon(bicho, 10);

        bichoService.guardarEntrenador(entrenador);
        bicho = bichoService.evolucionar(entrenador.getNombre(), bicho.getId());

        assertEquals(reptilmon.getNombre(), bicho.getEspecie().getNombre());
    }
}


