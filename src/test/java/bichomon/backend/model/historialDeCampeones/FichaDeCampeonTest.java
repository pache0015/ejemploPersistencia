package bichomon.backend.model.historialDeCampeones;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Nivel;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.ProveedorDeNiveles;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.especie.TipoBicho;
import ar.edu.unq.epers.bichomon.backend.model.historialDeCampeones.FichaDeCampeon;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Guarderia;
import bichomon.backend.factory.Factory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FichaDeCampeonTest {
    Entrenador campeon;
    Bicho bicho;
    LocalDate fechaInicio;
    Especie reptilmon;
    ProveedorDeNiveles proveedor;
    List niveles = new ArrayList<Nivel>();
    Nivel nivel;
    FichaDeCampeon ficha;

    @Before
    public void setup(){
        reptilmon = Factory.especieSinEvolucion("Reptilmon", TipoBicho.TIERRA);
        bicho = new Bicho(reptilmon, "helloworld");
        nivel = new Nivel(1, 1, 99);
        niveles.add(nivel);
        proveedor = Factory.proveedorDeNiveles(niveles);
        campeon = Factory.entrenador("Entrenador", new Guarderia("Guarderia"), proveedor);
        fechaInicio = LocalDate.now();
        ficha = new FichaDeCampeon(campeon, bicho, fechaInicio);
    }

    @Test
    public void seTesteanLosGet(){
        Assert.assertEquals(campeon, ficha.getCampeon());
        Assert.assertEquals(bicho, ficha.getBichoCampeon());
        Assert.assertEquals(fechaInicio, ficha.getFechaInicio());
    }
}
