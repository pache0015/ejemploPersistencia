package bichomon.backend.model;

import ar.edu.unq.epers.bichomon.backend.model.*;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Nivel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class EntrenadorTest {

    private Entrenador entrenador;
    private Service service = new Service();
    private Nivel nivel = new Nivel(1, 1, 99);

    @Before
    public void setUp(){
        service.setEntrenadorDao(new HibernateEntrenadorDao());
        service.setNivelDao(new HibernateNivelDao());
        entrenador = new Entrenador("Ash", null, nivel, service);
        service.setEntrenadorDao(new HibernateEntrenadorDao());
        service.setExperiencia(new HibernateExperienciaDao());
    }
    @Test
    public void test_constructoDeEntrenador(){
        List listaDeBichosVacios =  new ArrayList<>();

        Assert.assertEquals(entrenador.getBichos(), listaDeBichosVacios);
        Assert.assertEquals(entrenador.getNivel().numeroNivel, 1, 0);
        Assert.assertEquals(entrenador.getPuntosDeExperiencia(), 0, 0);
    }
    @Test
    public void test001_unEntrenadorTieneUnNivel() {
        this.service.guardarNivel(nivel);
        this.service.guardarEntrenador(entrenador);

        Entrenador entrenadorRecuperado = this.service.recuperarEntrenador(this.entrenador.getId());

        Assert.assertEquals(entrenadorRecuperado.getNivel().numeroNivel, this.nivel.numeroNivel);
    }
    @Test
    public void test002_unEntrandorAumentaSuEnergia(){

        Experiencia tabla = new Experiencia(10, "Combatir");
        this.service.guardarEntrenador(entrenador);
        this.service.guardarExperiencia(tabla);

        Entrenador entranadorRecuperador = this.service.recuperarEntrenador(this.entrenador.getId());
        Experiencia tablaRecuperada = this.service.recuperarTabla(tabla.getId());

        entranadorRecuperador.ganarEnergia(tablaRecuperada.puntosDeExperiencia());

        Assert.assertEquals(entranadorRecuperador.getPuntosDeExperiencia(), 11, 0);
        Assert.assertEquals(entranadorRecuperador.getNivel().numeroNivel, 1, 1);
    }
}
