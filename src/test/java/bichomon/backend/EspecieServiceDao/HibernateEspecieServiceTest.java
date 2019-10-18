package bichomon.backend.EspecieServiceDao;

import ar.edu.unq.epers.bichomon.backend.jdbc.dao.UbicacionDao;
import ar.edu.unq.epers.bichomon.backend.jdbc.dao.impl.HibernateEntrenadorDao;
import ar.edu.unq.epers.bichomon.backend.jdbc.dao.impl.HibernateEspecieDao;
import ar.edu.unq.epers.bichomon.backend.jdbc.dao.impl.HibernateUbicacionDao;
import ar.edu.unq.epers.bichomon.backend.jdbc.service.bicho.BichoServiceImpl;
import ar.edu.unq.epers.bichomon.backend.jdbc.service.especie.EspecieServiceImpl;
import ar.edu.unq.epers.bichomon.backend.jdbc.service.runner.TransactionRunner;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Nivel;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.ProveedorDeNiveles;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.especie.TipoBicho;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Guarderia;
import bichomon.backend.factory.Factory;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


public class HibernateEspecieServiceTest {

    private HibernateEspecieDao especieDao = new HibernateEspecieDao();
    private UbicacionDao ubicacionDao = new HibernateUbicacionDao();
    private EspecieServiceImpl especieService = new EspecieServiceImpl();
    private HibernateEntrenadorDao entrenadorDao = new HibernateEntrenadorDao();
    private Nivel nivel = new Nivel(10, 1, 99);
    private BichoServiceImpl bichoService = new BichoServiceImpl();
    private List<Nivel> niveles = new ArrayList<>();
    private ProveedorDeNiveles proveedor;
    private Entrenador entrenadorUno;
    private Entrenador entrenadorDos;
    private Guarderia guarderia = new Guarderia("GuaGuarderia");
    private Guarderia guarderiaDos = new Guarderia("guarderia");
    private Especie especie_fuego;
    private Especie especie_tierra;
    private Especie especie_agua;

    @Before
    public void setUp() {
        especieService.setEspecieDao(especieDao);
        especieService.setUbicacionDao(ubicacionDao);
        bichoService.setEntrenadorDao(entrenadorDao);
        niveles.add(nivel);
        List<Nivel> niveles = this.niveles;
        proveedor = Factory.proveedorDeNiveles(niveles);
        entrenadorUno = Factory.entrenador("Alberto", this.guarderia, this.proveedor);
        entrenadorDos = Factory.entrenador("Cristina", guarderiaDos, this.proveedor);
        this.especie_tierra = Factory.especieSinEvolucion("especie_tierra", TipoBicho.TIERRA);
        especie_fuego = Factory.especieSinEvolucion("especie_fuego", TipoBicho.FUEGO);
        especie_agua = Factory.especieSinEvolucion("especie_agua", TipoBicho.AGUA);
    }

    @After
    public void tearDown() {
        TransactionRunner.run(() -> {
            especieDao.borrarTodo();
        });
    }

    @Test
    public void NoSeTieneNingunaEspeciePopularEImpopularDeUnaBaseDeDatosVacia() {

        Assert.assertEquals(0, especieService.especiesMasPopulares().size());
        Assert.assertEquals(0, especieService.especiesMenosPopulares().size());

    }

    @Test
    public void SeTieneUnaEspeciePorqueUnEntrenadorDelSistemaCaputroUnBicho() {
        Especie especie = this.especie_fuego;
        Bicho bicho = Factory.bicho(especie);
        entrenadorUno.capturarBichomon(bicho, 1);
        bichoService.guardarEntrenador(entrenadorUno);

        Assert.assertEquals(1, especieService.especiesMasPopulares().size());
    }

    @Test
    public void SeTienenLasEspeciesMasPopularesEntreLosBichosCapturadosDeLosEntrenadores() {
        Bicho bicho = Factory.bicho(especie_fuego);
        Bicho bichoUno = Factory.bicho(especie_tierra);
        Bicho bichoDos = Factory.bicho(especie_tierra);
        Bicho bichoTres = Factory.bicho(especie_tierra);
        Bicho bichoCuatro = Factory.bicho(especie_fuego);

        entrenadorUno.capturarBichomon(bicho, 1);
        entrenadorUno.capturarBichomon(bichoUno, 1);
        entrenadorUno.capturarBichomon(bichoDos, 1);
        entrenadorUno.capturarBichomon(bichoTres, 1);
        entrenadorUno.capturarBichomon(bichoCuatro, 1);


        bichoService.guardarEntrenador(entrenadorUno);
        List<Especie> especiesMasPopulares = especieService.especiesMasPopulares();

        Assert.assertEquals(2, especiesMasPopulares.size());
        Assert.assertTrue(especiesMasPopulares.stream().anyMatch(obj -> obj.getNombre().equals("especie_tierra")));
        Assert.assertTrue(especiesMasPopulares.stream().anyMatch(obj -> obj.getNombre().equals("especie_fuego")));

    }

    @Test
    public void SeObtieneLasEspeciesQuePertenecenAUnaGuarderia() {
        Bicho bicho = Factory.bicho(especie_tierra);
        Bicho bichoUno = Factory.bicho(especie_tierra);
        Bicho bichoDos = Factory.bicho(especie_agua);
        Bicho bichoTres = Factory.bicho(especie_fuego);

        entrenadorUno.capturarBichomon(bicho, 1);
        entrenadorUno.capturarBichomon(bichoUno, 1);
        entrenadorUno.capturarBichomon(bichoDos, 1);
        entrenadorUno.capturarBichomon(bichoTres, 1);

        entrenadorUno.abandonar(bicho);
        entrenadorUno.abandonar(bichoUno);
        entrenadorUno.abandonar(bichoTres);

        bichoService.guardarEntrenador(entrenadorUno);

        List<Especie> especiesMenosPopulares = especieService.especiesMenosPopulares();

        Assert.assertEquals(2, especieService.especiesMenosPopulares().size());
        Assert.assertTrue(especiesMenosPopulares.stream().anyMatch(obj -> obj.getNombre().equals("especie_fuego")));
        Assert.assertTrue(especiesMenosPopulares.stream().anyMatch(obj -> obj.getNombre().equals("especie_tierra")));

    }

    @Test
    public void SeTieneLasEspeciesDeTodasLasGuarderiasDelSistema() {
        Bicho bicho = Factory.bicho(especie_tierra);
        Bicho bichoUno = Factory.bicho(especie_tierra);
        Bicho bichoDos = Factory.bicho(especie_agua);
        Bicho bichoTres = Factory.bicho(especie_fuego);
        Bicho bichoCuatro = Factory.bicho(especie_fuego);

        entrenadorUno.capturarBichomon(bicho, 1);
        entrenadorDos.capturarBichomon(bichoUno, 1);
        entrenadorDos.capturarBichomon(bichoDos, 1);
        entrenadorUno.capturarBichomon(bichoTres, 1);
        entrenadorDos.capturarBichomon(bichoCuatro, 1);

        entrenadorUno.abandonar(bicho);
        entrenadorDos.abandonar(bichoCuatro);


        bichoService.guardarEntrenador(entrenadorUno);
        bichoService.guardarEntrenador(entrenadorDos);

        List<Especie> especiesMenosPopulares = especieService.especiesMenosPopulares();

        Assert.assertEquals(2, especiesMenosPopulares.size());
        Assert.assertTrue(especiesMenosPopulares.stream().anyMatch(obj -> obj.getNombre().equals("especie_tierra")));
        Assert.assertTrue(especiesMenosPopulares.stream().anyMatch(obj -> obj.getNombre().equals("especie_fuego")));

    }
}
