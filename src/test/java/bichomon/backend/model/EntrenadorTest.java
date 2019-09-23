package bichomon.backend.model;

import ar.edu.unq.epers.bichomon.backend.model.*;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Nivel;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.ProveedorDeNiveles;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class EntrenadorTest {

    private Entrenador entrenador;
    private EntrenadorService entrenadorService = new EntrenadorService();
    private ExperienciaService experienciaService = new ExperienciaService();
    private ProveedorDeNiveles proveedor;
    private Nivel nivelUno  = new Nivel(1, 1, 99);
    private Nivel nivelDos  = new Nivel(2, 100, 400);
    private Nivel nivelTres = new Nivel(3, 401, 1000);
    private Nivel nivelCuatro = new Nivel(4, 1001, 2000);
    private Nivel nivelCinco  = new Nivel(5, 2001, 3000);
    private List<Nivel> niveles = new ArrayList<>();



    @Before
    public void setUp(){
        niveles.add(nivelUno);
        niveles.add(nivelDos);
        niveles.add(nivelTres);
        niveles.add(nivelCuatro);
        niveles.add(nivelCinco);

        proveedor = new ProveedorDeNiveles(niveles);

        entrenadorService.setEntrenadorDao(new HibernateEntrenadorDao());
        entrenador = new Entrenador("Ash", null, nivelUno, proveedor);
        entrenadorService.setEntrenadorDao(new HibernateEntrenadorDao());
        experienciaService.setExperiencia(new HibernateExperienciaDao());

    }
    @Test
    public void test_constructoDeEntrenador(){
        List listaDeBichosVacios =  new ArrayList<>();

        Assert.assertEquals(entrenador.getBichos(), listaDeBichosVacios);
        Assert.assertEquals(entrenador.getNivel().numeroNivel, 1, 0);
        Assert.assertEquals(entrenador.getPuntosDeExperiencia(), 1, 0);
    }
    @Test
    public void test001_unEntrenadorTieneUnNivel() {
        this.entrenadorService.guardarEntrenador(entrenador);

        Entrenador entrenadorRecuperado = this.entrenadorService.recuperarEntrenador(this.entrenador.getId());

        Assert.assertEquals(entrenadorRecuperado.getNivel().numeroNivel, this.nivelUno.numeroNivel);
    }
    @Test
    public void test002_unEntrandorAumentaSuEnergia(){

        Experiencia tabla = new Experiencia(10, "Combatir");
        this.entrenadorService.guardarEntrenador(entrenador);
        this.experienciaService.guardarExperiencia(tabla);

        Entrenador entranadorRecuperador = this.entrenadorService.recuperarEntrenador(this.entrenador.getId());
        Experiencia tablaRecuperada = this.experienciaService.recuperarTabla(tabla.getId());

        entranadorRecuperador.ganarEnergia(tablaRecuperada.puntosDeExperiencia());

        Assert.assertEquals(entranadorRecuperador.getPuntosDeExperiencia(), 11, 0);
        Assert.assertEquals(entranadorRecuperador.getNivel().numeroNivel, 1, 1);
    }
}
