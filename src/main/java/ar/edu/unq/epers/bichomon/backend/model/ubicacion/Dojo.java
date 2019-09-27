package ar.edu.unq.epers.bichomon.backend.model.ubicacion;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.historialDeCampeones.GestorDeFichasDeCampeones;

import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Dojo extends Ubicacion {

    @OneToOne
    private Entrenador entrenadorCampeon;
    @OneToOne
    private Bicho bichoCampeon;
    @OneToMany
    private List<Bicho> bichos;
    private GestorDeFichasDeCampeones gestor;


    public Dojo(String nombre) {
        super(nombre);
        bichos = new ArrayList<>();
        this.gestor = new GestorDeFichasDeCampeones();

    }

    public Dojo() {
    }

    @Override
    public Boolean puedeDejarAbandonar(Entrenador entrenador) {
        return false;
    }

    @Override
    public void recibirAbandonado(Entrenador entrenador, Bicho bichoAAbandonar) {
        throw new UbicacionIncorrectaException();
    }

    @Override
    public List<Bicho> bichomonesPara(Entrenador entrenador) {
        return bichos;
    }

    public boolean tieneCampeon() {
        return false;
    }

    public void declararCampeones(Entrenador entrenador, Bicho bicho) {
        gestor.finDeCampeon(entrenadorCampeon, LocalDate.now());
        gestor.addNuevoCampeon(entrenador, bicho, LocalDate.now());

        entrenadorCampeon = entrenador;
        bichoCampeon = bicho;
        llenarDojo(bicho.getEspecieRaiz());

    }


    private void llenarDojo(Especie especie) {
        for (int i = 0; i < 10; i++) {
            Bicho nuevoBicho = new Bicho(especie, "Hije del campeón");
            bichos.add(nuevoBicho);
        }
    }

    public Entrenador getEntrenadorCampeon(){
        return entrenadorCampeon;
    }

    public Bicho getBichoCampeon() {
        return bichoCampeon;
    }

    public void setBichoCampeon(Bicho bichoCampeon) {
        this.bichoCampeon = bichoCampeon;
    }
}
