package bichomon.backend.model.duelo;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.duelo.Duelo;
import ar.edu.unq.epers.bichomon.backend.model.duelo.ResultadoCombate;
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

import static org.junit.Assert.*;

public class DueloTest {
    Entrenador retador;
    Entrenador campeon;
    Bicho bicho;
    LocalDate fechaInicio;
    Especie reptilmon;
    Especie largartomon;
    ProveedorDeNiveles proveedor;
    List niveles = new ArrayList<Nivel>();
    Nivel nivel;
    List fichas = new ArrayList<FichaDeCampeon>();
    FichaDeCampeon ficha;
    GestorDeFichasDeCampeones gestor;
    Bicho bicho1;
    Dojo dojo;
    Duelo duelo;
    @Before
    public void setup(){
        reptilmon = new Especie("Reptilmon", TipoBicho.TIERRA);
        largartomon = new Especie("Lagartomon", TipoBicho.TIERRA, reptilmon, largartomon);
        bicho = new Bicho(reptilmon, "helloworld");
        bicho1 = new Bicho(largartomon, "blabla");
        bicho.setEnergia(1000.00);
        bicho1.setEnergia(1000.00);
        nivel = new Nivel(1, 1, 99);
        niveles.add(nivel);
        proveedor = new ProveedorDeNiveles(niveles);

        campeon = new Entrenador("campeon", new Dojo("Guarderia"), proveedor);
        campeon.setBichoParaDuelo(bicho);

        retador = new Entrenador("Entrenador", new Dojo("Guarderia"), proveedor);
        retador.setBichoParaDuelo(bicho1);
        fechaInicio = LocalDate.now();
        ficha = new FichaDeCampeon(campeon, bicho, fechaInicio);
        gestor = new GestorDeFichasDeCampeones();

        dojo = new Dojo("prueba");
        dojo.declararCampeones(campeon, bicho);
        duelo = new Duelo(retador, dojo);

    }

    @Test
    public void seLePideAlCampeonElBichoParaDuelo(){
        Assert.assertEquals(bicho,campeon.getBichoParaDuelo());
    }
    @Test
    public void alPelearLaListaDeAtaquesTieneAlMenosUnAtaque(){
        duelo.pelear();
        Assert.assertTrue(duelo.cantidadDeAtaques() >0);
    }
    @Test
    public void luegoDeUnaPeleaUnBichoTieneMenosEnergiaQueAntes(){
        duelo.pelear();
        Assert.assertTrue(bicho.getEnergia() < 1000.00);
    }
    @Test
    public void luegoDeUnDueloElObjetoResultadoNoTieneAtributosNulosYLaListDeAtaquesTieneElementos(){
        ResultadoCombate resultado = duelo.pelear();
        Assert.assertFalse(resultado.getAtaques().isEmpty());

    }
    @Test
    public void elResultadoDeUnDueloTieneunBichoYEntrenadorCampeon(){
        ResultadoCombate resultado = duelo.pelear();
        Assert.assertNotNull(resultado.getBichoCampeon());
        Assert.assertNotNull(resultado.getNombreDeEntrenadorCampeon());
    }
    @Test
    public void luegoDeUnDueloElBichoCampeonAunTieneEnergia(){
        ResultadoCombate resultado = duelo.pelear();
        Assert.assertTrue(resultado.getBichoCampeon().puedeSeguir());
    }
}
