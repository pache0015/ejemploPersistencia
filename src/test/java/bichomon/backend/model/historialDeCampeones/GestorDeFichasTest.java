package bichomon.backend.model.historialDeCampeones;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Nivel;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.ProveedorDeNiveles;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.especie.TipoBicho;
import ar.edu.unq.epers.bichomon.backend.model.historialDeCampeones.FichaDeCampeon;
import ar.edu.unq.epers.bichomon.backend.model.historialDeCampeones.GestorDeFichasDeCampeones;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Dojo;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Guarderia;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GestorDeFichasTest {

    Entrenador campeon;
    Bicho bicho;
    LocalDate fechaInicio;
    Especie reptilmon;
    ProveedorDeNiveles proveedor;
    List niveles = new ArrayList<Nivel>();
    Nivel nivel;
    List fichas = new ArrayList<FichaDeCampeon>();
    FichaDeCampeon ficha;
    GestorDeFichasDeCampeones gestor;
    Dojo dojo;

    @Before
    public void setup(){
        reptilmon = new Especie("Reptilmon", TipoBicho.TIERRA);
        bicho = new Bicho(reptilmon, "helloworld");
        nivel = new Nivel(1, 1, 99);
        niveles.add(nivel);
        proveedor = new ProveedorDeNiveles(niveles);
        dojo = new Dojo("Guarderia");
        campeon = new Entrenador("Entrenador", dojo, proveedor);
        fechaInicio = LocalDate.now();
        ficha = new FichaDeCampeon(campeon, bicho, fechaInicio, dojo);
        gestor = new GestorDeFichasDeCampeones();
    }

    @Test
    public void seGuardaUnaNuevaFichaDeCampeonYseVerificaQueFichasNoEsteVacia(){
        gestor.addNuevoCampeon(campeon, bicho, fechaInicio);
        Assert.assertFalse(gestor.getAllFichas().isEmpty());
    }

    @Test
    public void noSeAgregaUnaFichaDeCampeonYaExistente(){
        gestor.addNuevoCampeon(campeon, bicho, fechaInicio);
        gestor.addNuevoCampeon(campeon, bicho, fechaInicio);
        Assert.assertEquals(1, gestor.getAllFichas().size());
    }
    @Test
    public void seSeteaUnaFechaDeFinParaUnCampeon(){
        LocalDate fechaFin = LocalDate.of(2019,10,27);
        gestor.addNuevoCampeon(campeon, bicho, fechaInicio);
        gestor.finDeCampeon(campeon, fechaFin);

        Assert.assertEquals(fechaFin, gestor.getFichaDeCampeon(campeon).getFechaFin());
    }
}
