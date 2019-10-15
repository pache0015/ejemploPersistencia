package bichomon.backend.model.bicho;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.condicion.*;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Nivel;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.ProveedorDeNiveles;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.especie.TipoBicho;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Guarderia;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class BichoTest {
    Especie reptilmon;
    Especie largartomon;
    Bicho bicho;

    Nivel nivel;
    ProveedorDeNiveles proveedor;
    Entrenador entrenador;
    Nivel nivelUno = new Nivel(1, 1, 99);
    List<Nivel> niveles = new ArrayList<>();


    @Before
    public void setUp() {
        reptilmon = new Especie("Reptilmon", TipoBicho.TIERRA);
        largartomon = new Especie("Lagartomon", TipoBicho.TIERRA, reptilmon);
        bicho = new Bicho(largartomon, "helloworld");
        List niveles = new ArrayList<Nivel>();
        nivel = new Nivel(1, 1, 99);
        niveles.add(nivel);
        proveedor = new ProveedorDeNiveles(niveles);
        entrenador = new Entrenador("Entrenador", new Guarderia("Guarderia"), proveedor);
    }
    @Test
    public void cuandoCumpleUnaCondicionBasadaEnEnergiaEvolucionaYCambiaSuEspecie(){
        bicho.setEnergia(1d);

        largartomon.setCondicionDeEvolucion(condicionDeEnergia(0));
        Assert.assertEquals(bicho.getEspecie(), largartomon);
        bicho.evolucionar();
        Assert.assertEquals(bicho.getEspecie(), reptilmon);
    }

    @Test
    public void cuandoNoCumpleUnaCondicionBasadaEnEnergiaNoEvoluciona(){
        bicho.setEnergia(0d);

        largartomon.setCondicionDeEvolucion(condicionDeEnergia(0));
        Assert.assertEquals(bicho.getEspecie(), largartomon);
        bicho.evolucionar();
        Assert.assertEquals(bicho.getEspecie(), largartomon);
    }

    @Test
    public void cuandoCumpleUnaCondicionBasadaEnVictoriasEvolucionaYCambiaSuEspecie(){
        bicho.setVictorias(3);

        largartomon.setCondicionDeEvolucion(condicionDeVictorias(2));
        Assert.assertEquals(bicho.getEspecie(), largartomon);
        bicho.evolucionar();
        Assert.assertEquals(bicho.getEspecie(), reptilmon);
    }

    private CondicionBasadaEnVictorias condicionDeVictorias(Integer victorias) {
        return new CondicionBasadaEnVictorias(victorias);
    }

    @Test
    public void cuandoCumpleUnaCondicionBasadaEnEdadEvolucionaYCambiaSuEspecie(){
        bicho.setFechaDeCaptura(LocalDate.of(2019, Month.JANUARY,1));

        LocalDate localDate = LocalDate.of(2019, Month.JANUARY, 2);
        Long tiempoEnDias = (long)1;
        largartomon.setCondicionDeEvolucion(condicionDeEdad(localDate, tiempoEnDias));
        Assert.assertEquals(bicho.getEspecie(), largartomon);
        bicho.evolucionar();
        Assert.assertEquals(bicho.getEspecie(), reptilmon);
    }

    private CondicionBasadaEnEdad condicionDeEdad(LocalDate localDate, Long tiempoEnDias) {
        return new CondicionBasadaEnEdad(tiempoEnDias, localDate);
    }

    @Test
    public void cuandoCumpleMasDeUnaCondicionEvolucionaYCambiaSuEspecie(){
        ArrayList<Condicion> condiciones = new ArrayList<>();
        condiciones.add(condicionDeEnergia(2));
        condiciones.add(condicionDeVictorias(1));

        bicho.setVictorias(3);bicho.setEnergia(3d);
        largartomon.setCondicionDeEvolucion(new CondicionMultiple(condiciones));
        bicho.evolucionar();
        Assert.assertEquals(bicho.getEspecie(), reptilmon);
    }

    private CondicionBasadaEnEnergia condicionDeEnergia(Integer cantidadDeEnergia) {
        return new CondicionBasadaEnEnergia(cantidadDeEnergia);
    }

    @Test
    public void cuandoSuEntrenadorLLeguaAUnDeterminadoNivelEvoluciona() {
        bicho.setEntrenador(entrenador);
        Condicion condicionDeNivel = getCondicionDeNivel(1);
        largartomon.setCondicionDeEvolucion(condicionDeNivel);
        Assert.assertEquals(bicho.getEspecie(), largartomon);
        bicho.evolucionar();
        Assert.assertEquals(bicho.getEspecie(), reptilmon);
    }

    public CondicionBasadaEnNivelEntrenador getCondicionDeNivel(Integer nivelNcesarioParaEvolucionar) {
        return new CondicionBasadaEnNivelEntrenador(nivelNcesarioParaEvolucionar);
    }
}
