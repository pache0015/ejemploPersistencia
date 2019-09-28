package bichomon.backend.ServiceTest.BichoService;

import ar.edu.unq.epers.bichomon.backend.jdbc.dao.impl.HibernateBichoDao;
import ar.edu.unq.epers.bichomon.backend.jdbc.service.bicho.BichoDaoService;
import ar.edu.unq.epers.bichomon.backend.jdbc.service.bicho.BichoServiceImp;
import ar.edu.unq.epers.bichomon.backend.jdbc.service.entrenador.EntrenadorDaoService;
import ar.edu.unq.epers.bichomon.backend.jdbc.dao.impl.HibernateEntrenadorDao;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.LimiteBicho;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Nivel;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.ProveedorDeNiveles;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.especie.TipoBicho;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Dojo;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Guarderia;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.UbicacionIncorrectaException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class BichoServiceTest {

    Ubicacion dojo;
    Ubicacion guarderia;
    Nivel nivel;
    ProveedorDeNiveles proveedor;
    Entrenador entrenador;
    Especie especie;
    Bicho bicho;
    BichoServiceImp bichoService;
    BichoDaoService bichoDao;
    EntrenadorDaoService entrenadorDao;

    @Before
    public void setUp(){
        dojo = new Dojo("Dojo");
        guarderia = new Guarderia("guarderia");
        nivel = new Nivel(1, 1,99);
        List niveles = new ArrayList<Nivel>();
        niveles.add(nivel);
        proveedor = new ProveedorDeNiveles(niveles);
        especie = new Especie("especiemon", TipoBicho.TIERRA);
        bicho = new Bicho(especie);
        entrenador = new Entrenador("ASH", null ,proveedor);
        bichoService = new BichoServiceImp();
        bichoDao = new BichoDaoService();
        entrenadorDao = new EntrenadorDaoService();
    }

    @Test(expected = UbicacionIncorrectaException.class)
    public void test001UnEntrenadorNoPuedeAbandonarASuBichoEnUnDojo() throws LimiteBicho {
        entrenador.setUbicacionEn(dojo);
        entrenador.capturarBichomon(bicho, 10);

        bichoDao.setBichoDao(new HibernateBichoDao());
        bichoDao.guardarBicho(bicho);

        entrenadorDao.setEntrenadorDao(new HibernateEntrenadorDao());
        entrenadorDao.guardarEntrenador(entrenador);

        bichoService.setBichoDaoService(bichoDao);
        bichoService.setEntrenadorDaoService(entrenadorDao);

        bichoService.abandonar(entrenador.getNombre(), bicho.getId());

    }
}


