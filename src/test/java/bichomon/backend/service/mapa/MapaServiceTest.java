package bichomon.backend.service.mapa;

import ar.edu.unq.epers.bichomon.backend.jdbc.dao.BichoDao;
import ar.edu.unq.epers.bichomon.backend.jdbc.dao.EntrenadorDao;
import ar.edu.unq.epers.bichomon.backend.jdbc.dao.UbicacionDao;
import ar.edu.unq.epers.bichomon.backend.jdbc.dao.impl.HibernateBichoDao;
import ar.edu.unq.epers.bichomon.backend.jdbc.dao.impl.HibernateEntrenadorDao;
import ar.edu.unq.epers.bichomon.backend.jdbc.dao.impl.HibernateEspecieDao;
import ar.edu.unq.epers.bichomon.backend.jdbc.dao.impl.HibernateUbicacionDao;
import ar.edu.unq.epers.bichomon.backend.jdbc.service.bicho.BichoServiceImpl;
import ar.edu.unq.epers.bichomon.backend.jdbc.service.mapa.MapaServiceImp;
import ar.edu.unq.epers.bichomon.backend.jdbc.service.runner.SessionFactoryProvider;
import ar.edu.unq.epers.bichomon.backend.jdbc.service.runner.TransactionRunner;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Nivel;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.ProveedorDeNiveles;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.especie.TipoBicho;
import ar.edu.unq.epers.bichomon.backend.model.historialDeCampeones.FichaDeCampeon;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Dojo;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Guarderia;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.NoHayCampeonException;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
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
    Bicho bicho2;
    BichoServiceImpl bichoService;
    BichoDao bichoDao;
    EntrenadorDao entrenadorDao;
    Dojo dojo;
    MapaServiceImp mapaService;
    UbicacionDao ubicacionDao;
    Entrenador entrenador2;

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
        bicho2 = new Bicho(especie);
        entrenador = new Entrenador("ASH", guarderia ,proveedor);
        entrenador2 = new Entrenador("ASHU", guarderia ,proveedor);
        bichoService = Mockito.spy(new BichoServiceImpl());
        bichoDao = new HibernateBichoDao();
        entrenadorDao = new HibernateEntrenadorDao();

        bichoService.setBichoDao(bichoDao);
        bichoService.setEntrenadorDao(entrenadorDao);
        ubicacionDao = new HibernateUbicacionDao();
        mapaService = new MapaServiceImp();



        mapaService.setUbicacionDao(ubicacionDao);
        mapaService.setEntrenadorDao(entrenadorDao);
        mapaService.setBichoDao(bichoDao);
    }

    @After
    public void cleanup() {
        TransactionRunner.run(() -> {
            new HibernateEspecieDao().borrarTodo();
        });
    }

    @Test
    public void unEntrenadorCambiaDeUbicacion(){

        bichoService.guardarEntrenador(entrenador);

        TransactionRunner.run(()-> ubicacionDao.guardar(dojo));
        mapaService.mover(entrenador.getNombre(), dojo.getNombre());

        Entrenador entrenadorRecuperado = TransactionRunner.run(() -> entrenadorDao.recuperar(entrenador.getNombre()));

        Assert.assertEquals("gym", entrenadorRecuperado.getUbicacionActual().getNombre());
    }



    @Test
    public void dosEntrenadoresEnLaMismaUbicacionSonGuardadosyLuegoSePideLaCantidadDeEentrenadoresEnEsaUbicacion(){


        bichoService.guardarEntrenador(entrenador);
        bichoService.guardarEntrenador(entrenador2);

        Assert.assertEquals(2, mapaService.cantidadDeEntrenadores(guarderia.getNombre()));
    }

    @Test(expected = NoHayCampeonException.class)
    public void unDojoNoTieneCampeon(){

        TransactionRunner.run(()-> ubicacionDao.guardar(dojo));
        mapaService.campeon(dojo.getNombre());
    }

    @Test
    public void seLePideElCampeonActualAUnDojo(){
        dojo.setBichoCampeon(bicho);

        TransactionRunner.run(()-> ubicacionDao.guardar(dojo));

        String nombreBicho =  mapaService.campeon(dojo.getNombre()).getNombre();

        Assert.assertEquals(bicho.getNombre(), nombreBicho);
    }

    @Test
    public void SeVerificaElCampeonHistoricoDeUnDojo(){
        FichaDeCampeon ficha1 = new FichaDeCampeon(entrenador,bicho, LocalDate.of(2018, 10,18), dojo);

        ficha1.setFechaFin(LocalDate.of(2019, 10,10));
        FichaDeCampeon ficha2 = new FichaDeCampeon(entrenador2,bicho2, LocalDate.of(2019, 10,17), dojo);
        ficha2.setFechaFin(LocalDate.now());

        dojo.setFichas(ficha1);
        dojo.setFichas(ficha2);

        TransactionRunner.run(()-> ubicacionDao.guardar(dojo));



        Assert.assertEquals(bicho.getNombre(), mapaService.campeonHistorico(dojo.getNombre()).getNombre());
    }

}
