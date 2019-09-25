package bichomon.backend.model;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Condicion;
import ar.edu.unq.epers.bichomon.backend.model.bicho.CondicionBasadaEnEnergia;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.especie.TipoBicho;
import org.junit.Assert;
import org.junit.Test;

import java.time.*;
import java.util.ArrayList;

public class BichoTest {

    @Test
    public void cuandoCumpleUnaCondicionBasadaEnEnergiaEvolucionaYCambiaSuEspecie(){
        Especie reptilmon = new Especie("Reptilmon", TipoBicho.TIERRA);
        Especie lagartomon = new Especie("Lagartomon", TipoBicho.TIERRA, reptilmon);

        Bicho bicho =  new Bicho(lagartomon, "helloworld");
        bicho.setEnergia(1d);

        bicho.setCondicionDeEvolucion(condicionDeEnergia(0));
        bicho.evolucionar();
        Assert.assertEquals(bicho.getEspecie(), reptilmon);
    }

    @Test
    public void cuandoNoCumpleUnaCondicionBasadaEnEnergiaNoEvoluciona(){
        Especie reptilmon = new Especie("Reptilmon", TipoBicho.TIERRA);
        Especie lagartomon =new Especie("Lagartomon", TipoBicho.TIERRA, reptilmon);

        Bicho bicho = new Bicho(lagartomon, "helloworld");
        bicho.setEnergia(0d);

        bicho.setCondicionDeEvolucion(condicionDeEnergia(0));
        bicho.evolucionar();
        Assert.assertEquals(bicho.getEspecie(), lagartomon);
    }

    @Test
    public void cuandoCumpleUnaCondicionBasadaEnVictoriasEvolucionaYCambiaSuEspecie(){
        Especie reptilmon = new Especie("Reptilmon", TipoBicho.TIERRA);
        Especie lagartomon = new Especie("Lagartomon", TipoBicho.TIERRA, reptilmon);

        Bicho bicho = new Bicho(lagartomon, "helloworld");
        bicho.setVictorias(3);

        bicho.setCondicionDeEvolucion(condicionDeVictorias(2));
        bicho.evolucionar();
        Assert.assertEquals(bicho.getEspecie(), reptilmon);
    }

    private CondicionBasadaEnVictorias condicionDeVictorias(Integer victorias) {
        return new CondicionBasadaEnVictorias(victorias);
    }

    @Test
    public void cuandoCumpleUnaCondicionBasadaEnEdadEvolucionaYCambiaSuEspecie(){
        Especie reptilmon = new Especie("Reptilmon", TipoBicho.TIERRA);
        Especie lagartomon = new Especie("Lagartomon", TipoBicho.TIERRA, reptilmon);

        Bicho bicho = new Bicho(lagartomon, "helloworld");
        bicho.setFechaDeCaptura(LocalDate.of(2019, Month.JANUARY,1));

        LocalDate localDate = LocalDate.of(2019, Month.JANUARY, 2);
        Period temporalAmount = Period.ofDays(1);
        bicho.setCondicionDeEvolucion(condicionDeEdad(localDate, temporalAmount));
        bicho.evolucionar();
        Assert.assertEquals(bicho.getEspecie(), reptilmon);
    }

    private CondicionBasadaEnEdad condicionDeEdad(LocalDate localDate, Period temporalAmount) {
        return new CondicionBasadaEnEdad(temporalAmount, localDate);
    }

    @Test
    public void cuandoCumpleMasDeUnaCondicionEvolucionaYCambiaSuEspecie(){
        Especie reptilmon = new Especie("Reptilmon", TipoBicho.TIERRA);
        Especie lagartomon = new Especie("Lagartomon", TipoBicho.TIERRA, reptilmon);
        ArrayList<Condicion> condiciones = new ArrayList<>();
        condiciones.add(condicionDeEnergia(2));
        condiciones.add(condicionDeVictorias(1));

        Bicho bicho = new Bicho(lagartomon, "helloworld");
        bicho.setVictorias(3);bicho.setEnergia(3d);
        bicho.setCondicionDeEvolucion(new CondicionMultiple(condiciones));
        bicho.evolucionar();
        Assert.assertEquals(bicho.getEspecie(), reptilmon);
    }

    private CondicionBasadaEnEnergia condicionDeEnergia(Integer cantidadDeEnergia) {
        return new CondicionBasadaEnEnergia(cantidadDeEnergia);
    }
}
