package bichomon.backend.service.mapa;

import ar.edu.unq.epers.bichomon.backend.jdbc.dao.BichoDao;
import ar.edu.unq.epers.bichomon.backend.jdbc.dao.EntrenadorDao;
import ar.edu.unq.epers.bichomon.backend.jdbc.dao.UbicacionDao;
import ar.edu.unq.epers.bichomon.backend.jdbc.dao.impl.HibernateBichoDao;
import ar.edu.unq.epers.bichomon.backend.jdbc.dao.impl.HibernateEntrenadorDao;
import ar.edu.unq.epers.bichomon.backend.jdbc.dao.impl.HibernateUbicacionDao;
import ar.edu.unq.epers.bichomon.backend.jdbc.service.bicho.BichoServiceImp;
import ar.edu.unq.epers.bichomon.backend.jdbc.service.mapa.MapaService;
import ar.edu.unq.epers.bichomon.backend.jdbc.service.mapa.MapaServiceImp;
import ar.edu.unq.epers.bichomon.backend.jdbc.service.runner.SessionFactoryProvider;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Nivel;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.ProveedorDeNiveles;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.especie.TipoBicho;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Dojo;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Guarderia;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

public class MapaServiceTest {
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
    Dojo dojo;
    MapaServiceImp mapaService;
    UbicacionDao ubicacionDao;

    @Before
    public void setUp(){
        SessionFactoryProvider.destroy();
        guarderia = new Guarderia("guarderia");
        dojo = new Dojo("gym");
        nivel = new Nivel(2, 1,99);
        List niveles = new ArrayList<Nivel>();
        niveles.add(nivel);
        proveedor = new ProveedorDeNiveles(niveles);
        reptilmon = new Especie("reptilmon", TipoBicho.TIERRA);
        especie = new Especie("especiemon", TipoBicho.TIERRA, reptilmon);
        bicho = new Bicho(especie);
        entrenador = new Entrenador("ASH", guarderia ,proveedor);
        bichoService = Mockito.spy(new BichoServiceImp());
        bichoDao = new HibernateBichoDao();
        entrenadorDao = new HibernateEntrenadorDao();

        bichoService.setBichoDao(bichoDao);
        bichoService.setEntrenadorDao(entrenadorDao);
        ubicacionDao = new HibernateUbicacionDao();
        mapaService = new MapaServiceImp();

    }

    @After
    public void cleanup() {
    }

    @Test
    public void unEntrenadorCambiaDeUbicacion(){
        mapaService.setUbicacionDao(ubicacionDao);
        mapaService.setEntrenadorDao(entrenadorDao);
        mapaService.setBichoDao(bichoDao);

        entrenadorDao.guardar(entrenador);

        mapaService.mover(entrenador.getNombre(), dojo.getNombre());
        Assert.assertEquals("gym", entrenador.getUbicacionActual().getNombre());
    }
}
