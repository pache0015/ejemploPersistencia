package bichomon.backend.EspecieServiceDao;

import ar.edu.unq.epers.bichomon.backend.jdbc.dao.impl.HibernateEntrenadorDao;
import ar.edu.unq.epers.bichomon.backend.jdbc.service.bicho.BichoServiceImp;
import ar.edu.unq.epers.bichomon.backend.jdbc.service.especie.EspecieServiceDaoImp;
import ar.edu.unq.epers.bichomon.backend.jdbc.service.especie.GuarderiaDao;
import ar.edu.unq.epers.bichomon.backend.jdbc.service.especie.HibernateGuarderiaDao;
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


public class EspecieServiceDaoTest {

    private Factory factory = new Factory();
    private EspecieServiceDaoImp especieServiceDao = new EspecieServiceDaoImp();
    private HibernateEntrenadorDao entrenadorDao = new HibernateEntrenadorDao();
    private HibernateEspecieDao especieDao = new HibernateEspecieDao();
    private Nivel nivel = new Nivel(10, 1, 99);
    private BichoServiceImp bichoService = factory.bichoServiceImpl();

    private List<Nivel> niveles = new ArrayList<>();
    private ProveedorDeNiveles proveedor;
    private Entrenador entrenadorUno;
    private Entrenador entrenadorDos;
    private GuarderiaDao guarderiaDao = new HibernateGuarderiaDao();
    private Guarderia guarderia = new Guarderia("GuaGuarderia");
    private Guarderia guarderiaDos = new Guarderia("guarderia");
    private Especie especie_fuego;
    private Especie especie_tierra;
    private Especie especie_agua;

    @Before
    public void setUp() {
        bichoService.setEntrenadorDao(entrenadorDao);
        especieServiceDao.setEspecieDao(especieDao);
        especieServiceDao.setGuarderiaDao(guarderiaDao);
        niveles.add(nivel);
        proveedor = new ProveedorDeNiveles(niveles);
        entrenadorUno = new Entrenador("Alberto", guarderia, proveedor);
        entrenadorDos = new Entrenador("Cristina", guarderiaDos, proveedor);
        especie_tierra = new Especie("especie_tierra", TipoBicho.TIERRA);
        especie_fuego = new Especie("especie_fuego", TipoBicho.FUEGO);
        especie_agua = new Especie("especie_agua", TipoBicho.AGUA);
    }

    @After
    public void tearDown() {
        TransactionRunner.run(() -> {
            especieDao.borrarTodo();
        });
    }

    @Test
    public void NoSeTieneNingunaEspeciePopularEImpopularDeUnaBaseDeDatosVacia() {

        Assert.assertEquals(0, especieServiceDao.especiesMasPopulares().size());
        Assert.assertEquals(0, especieServiceDao.especiesMenosPopulares().size());

    }

    @Test
    public void SeTieneUnaEspeciePorqueUnEntrenadorDelSistemaCaputroUnBicho() {
        Bicho bicho = new Bicho(especie_fuego);
        entrenadorUno.capturarBichomon(bicho, 1);
        bichoService.guardarEntrenador(entrenadorUno);

        Assert.assertEquals(1, especieServiceDao.especiesMasPopulares().size());
    }

    @Test
    public void SeTienenLasEspeciesMasPopularesEntreLosBichosCapturadosDeLosEntrenadores() {
        Bicho bicho = new Bicho(especie_fuego);
        Bicho bichoUno = new Bicho(especie_tierra);
        Bicho bichoDos = new Bicho(especie_tierra);
        Bicho bichoTres = new Bicho(especie_tierra);
        Bicho bichoCuatro = new Bicho(especie_fuego);

        entrenadorUno.capturarBichomon(bicho, 1);
        entrenadorUno.capturarBichomon(bichoUno, 1);
        entrenadorUno.capturarBichomon(bichoDos, 1);
        entrenadorUno.capturarBichomon(bichoTres, 1);
        entrenadorUno.capturarBichomon(bichoCuatro, 1);


        bichoService.guardarEntrenador(entrenadorUno);
        List<Especie> especiesMasPopulares = especieServiceDao.especiesMasPopulares();

        Assert.assertEquals(2, especiesMasPopulares.size());
        Assert.assertTrue(especiesMasPopulares.stream().anyMatch(obj -> obj.getNombre().equals("especie_tierra")));
        Assert.assertTrue(especiesMasPopulares.stream().anyMatch(obj -> obj.getNombre().equals("especie_fuego")));

    }

    @Test
    public void SeObtieneLasEspeciesQuePertenecenAUnaGuarderia() {
        Bicho bicho = new Bicho(especie_tierra);
        Bicho bichoUno = new Bicho(especie_tierra);
        Bicho bichoDos = new Bicho(especie_agua);
        Bicho bichoTres = new Bicho(especie_fuego);

        entrenadorUno.capturarBichomon(bicho, 1);
        entrenadorUno.capturarBichomon(bichoUno, 1);
        entrenadorUno.capturarBichomon(bichoDos, 1);
        entrenadorUno.capturarBichomon(bichoTres, 1);

        entrenadorUno.abandonar(bicho);
        entrenadorUno.abandonar(bichoUno);
        entrenadorUno.abandonar(bichoTres);

        bichoService.guardarEntrenador(entrenadorUno);

        List<Especie> especiesMenosPopulares = especieServiceDao.especiesMenosPopulares();

        Assert.assertEquals(2, especieServiceDao.especiesMenosPopulares().size());
        Assert.assertTrue(especiesMenosPopulares.stream().anyMatch(obj -> obj.getNombre().equals("especie_fuego")));
        Assert.assertTrue(especiesMenosPopulares.stream().anyMatch(obj -> obj.getNombre().equals("especie_tierra")));

    }

    @Test
    public void SeTieneLasEspeciesDeTodasLasGuarderiasDelSistema() {
        Bicho bicho = new Bicho(especie_tierra);
        Bicho bichoUno = new Bicho(especie_tierra);
        Bicho bichoDos = new Bicho(especie_agua);
        Bicho bichoTres = new Bicho(especie_fuego);
        Bicho bichoCuatro = new Bicho(especie_fuego);

        entrenadorUno.capturarBichomon(bicho, 1);
        entrenadorDos.capturarBichomon(bichoUno, 1);
        entrenadorDos.capturarBichomon(bichoDos, 1);
        entrenadorUno.capturarBichomon(bichoTres, 1);
        entrenadorDos.capturarBichomon(bichoCuatro, 1);

        entrenadorUno.abandonar(bicho);
        entrenadorDos.abandonar(bichoCuatro);


        bichoService.guardarEntrenador(entrenadorUno);
        bichoService.guardarEntrenador(entrenadorDos);

        List<Especie> especiesMenosPopulares = especieServiceDao.especiesMenosPopulares();

        Assert.assertEquals(2, especiesMenosPopulares.size());
        Assert.assertTrue(especiesMenosPopulares.stream().anyMatch(obj -> obj.getNombre().equals("especie_tierra")));
        Assert.assertTrue(especiesMenosPopulares.stream().anyMatch(obj -> obj.getNombre().equals("especie_fuego")));

    }


}
