package bichomon.backend.EspecieServiceDao;

import ar.edu.unq.epers.bichomon.backend.jdbc.dao.impl.HibernateBichoDao;
import ar.edu.unq.epers.bichomon.backend.jdbc.dao.impl.HibernateEntrenadorDao;
import ar.edu.unq.epers.bichomon.backend.jdbc.service.bicho.BichoService;
import ar.edu.unq.epers.bichomon.backend.jdbc.service.bicho.BichoServiceImp;
import ar.edu.unq.epers.bichomon.backend.jdbc.service.especie.EspecieServiceDao;
import ar.edu.unq.epers.bichomon.backend.jdbc.service.especie.EspecieServiceDaoImp;
import ar.edu.unq.epers.bichomon.backend.jdbc.service.runner.SessionFactoryProvider;
import ar.edu.unq.epers.bichomon.backend.jdbc.service.runner.TransactionRunner;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.duelo.ResultadoCombate;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Nivel;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.ProveedorDeNiveles;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.especie.TipoBicho;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Dojo;
import org.junit.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


public class EspecieServiceDaoTest {

    private EspecieServiceDaoImp especieServiceDao = new EspecieServiceDaoImp();
    private HibernateEntrenadorDao entrenadorDao = new HibernateEntrenadorDao();
    private HibernateBichoDao bichoDao = new HibernateBichoDao();
    private Nivel nivel = new Nivel(10, 1, 99);
    private List<Nivel> niveles = new ArrayList<>();
    private ProveedorDeNiveles proveedor;
    private Entrenador entrenador;
    private BichoServiceImp bichoService = new BichoServiceImp();

    @Before
    public void setUp(){
        bichoService.setEntrenadorDao(entrenadorDao);
        especieServiceDao.setEntrenadorDao(entrenadorDao);
        niveles.add(nivel);
        proveedor = new ProveedorDeNiveles(niveles);
        entrenador = new Entrenador("Alberto", null, proveedor);
    }
    @After
    public void tearDown(){
        SessionFactoryProvider.destroy();
    }
    @Test
    public void test001(){

       Assert.assertEquals(0, especieServiceDao.especiesMasPopulares().size());

        }
    @Test
    public void test002(){
        Especie especie_tierra = new Especie("especie", TipoBicho.TIERRA);
        Bicho bicho = new Bicho(especie_tierra);
        entrenador.capturarBichomon(bicho, 1);
        bichoService.guardarEntrenador(entrenador);

        Assert.assertEquals(1, especieServiceDao.especiesMasPopulares().size());
    }
    @Test
    public void test003(){
        Especie especie_tierra = new Especie("especie_popular", TipoBicho.TIERRA);
        Especie especie_fuego = new Especie("especie_no_popular", TipoBicho.FUEGO);
        Bicho bicho = new Bicho(especie_fuego);
        Bicho bichoUno = new Bicho(especie_tierra);
        Bicho bichoDos = new Bicho(especie_tierra);
        Bicho bichoTres = new Bicho(especie_tierra);

        entrenador.capturarBichomon(bicho, 1);
        entrenador.capturarBichomon(bichoUno, 1);
        entrenador.capturarBichomon(bichoDos, 1);
        entrenador.capturarBichomon(bichoTres, 1);

        bichoService.guardarEntrenador(entrenador);

        Assert.assertEquals("especie_popular", especieServiceDao.especiesMasPopulares().size());
    }
}
