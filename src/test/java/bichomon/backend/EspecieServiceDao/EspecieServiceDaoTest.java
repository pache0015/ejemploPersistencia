package bichomon.backend.EspecieServiceDao;

import ar.edu.unq.epers.bichomon.backend.jdbc.dao.impl.HibernateEntrenadorDao;
import ar.edu.unq.epers.bichomon.backend.jdbc.service.bicho.BichoService;
import ar.edu.unq.epers.bichomon.backend.jdbc.service.bicho.BichoServiceImp;
import ar.edu.unq.epers.bichomon.backend.jdbc.service.especie.EspecieServiceDaoImp;
import ar.edu.unq.epers.bichomon.backend.jdbc.service.especie.GuarderiaDao;
import ar.edu.unq.epers.bichomon.backend.jdbc.service.especie.HibernateGuarderiaDao;
import ar.edu.unq.epers.bichomon.backend.jdbc.service.runner.SessionFactoryProvider;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Nivel;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.ProveedorDeNiveles;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.especie.TipoBicho;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Guarderia;
import org.hibernate.cfg.BinderHelper;
import org.junit.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


public class EspecieServiceDaoTest {

    private EspecieServiceDaoImp especieServiceDao = new EspecieServiceDaoImp();
    private HibernateEntrenadorDao entrenadorDao = new HibernateEntrenadorDao();
    private Nivel nivel = new Nivel(10, 1, 99);
    private BichoServiceImp bichoService = new BichoServiceImp();
    private List<Nivel> niveles = new ArrayList<>();
    private ProveedorDeNiveles proveedor;
    private Entrenador entrenadorUno;
    private Entrenador entrenadorDos;
    private GuarderiaDao guarderiaDao = new HibernateGuarderiaDao();
    private Guarderia guarderia = new Guarderia("GuaGuaGuarderia");
    private Especie especie_fuego;
    private Especie especie_tierra;
    private Especie especie_agua;

    @Before
    public void setUp(){
        bichoService.setEntrenadorDao(entrenadorDao);
        especieServiceDao.setEntrenadorDao(entrenadorDao);
        especieServiceDao.setGuarderiaDao(guarderiaDao);
        niveles.add(nivel);
        proveedor = new ProveedorDeNiveles(niveles);
        entrenadorUno = new Entrenador("Alberto", guarderia, proveedor);
        entrenadorDos = new Entrenador("Cristina", guarderia, proveedor);
        especie_tierra = new Especie("especie_popular", TipoBicho.TIERRA);
        especie_fuego = new Especie("especie_no_popular", TipoBicho.FUEGO);
        especie_agua = new Especie("especie_menos_popular", TipoBicho.AGUA);
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
        Bicho bicho = new Bicho(especie_fuego);
        entrenadorUno.capturarBichomon(bicho, 1);
        bichoService.guardarEntrenador(entrenadorUno);

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

        entrenadorUno.capturarBichomon(bicho, 1);
        entrenadorUno.capturarBichomon(bichoUno, 1);
        entrenadorUno.capturarBichomon(bichoDos, 1);
        entrenadorUno.capturarBichomon(bichoTres, 1);

        bichoService.guardarEntrenador(entrenadorUno);

        Assert.assertEquals(2, especieServiceDao.especiesMasPopulares().size());
    }
    @Test
    public void test004(){
        Bicho bicho = new Bicho(especie_tierra);
        Bicho bichoUno = new Bicho(especie_tierra);
        Bicho bichoDos = new Bicho(especie_tierra);
        Bicho bichoTres = new Bicho(especie_fuego);

        entrenadorUno.capturarBichomon(bicho, 1);
        entrenadorUno.capturarBichomon(bichoUno, 1);
        entrenadorUno.capturarBichomon(bichoDos, 1);
        entrenadorUno.capturarBichomon(bichoTres, 1);

        entrenadorUno.abandonar(bicho);
        entrenadorUno.abandonar(bichoUno);
        entrenadorUno.abandonar(bichoDos);

        bichoService.guardarEntrenador(entrenadorUno);
        especieServiceDao.guardarGuarderia(guarderia);

        List<Especie> especiesMenosPopulares = new ArrayList<>();
        especiesMenosPopulares.add(especie_tierra);

        Assert.assertEquals(especiesMenosPopulares , especieServiceDao.especiesMenosPopulares());

    }

    @Test
    public void test005(){
        Bicho bicho = new Bicho(especie_tierra);
        Bicho bichoUno = new Bicho(especie_tierra);
        Bicho bichoDos = new Bicho(especie_agua);
        Bicho bichoTres = new Bicho(especie_fuego);
        Bicho bichoCuatro = new Bicho(especie_fuego);

        entrenadorUno.capturarBichomon(bicho, 1);
        entrenadorDos.capturarBichomon(bichoUno, 1);
        entrenadorDos.capturarBichomon(bichoDos,1);
        entrenadorUno.capturarBichomon(bichoTres, 1);

        bichoService.guardarEntrenador(entrenadorUno);
        bichoService.guardarEntrenador(entrenadorDos);

        List<Especie> especiesMasPopulares = new ArrayList<>();
        especiesMasPopulares.add(especie_tierra);
        especiesMasPopulares.add(especie_fuego);

        Assert.assertEquals(especiesMasPopulares , especieServiceDao.especiesMenosPopulares());

    }


}
