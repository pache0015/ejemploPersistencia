package ar.edu.unq.epers.bichomon.backend.model.ubicacion;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.util.ArrayList;
import java.util.List;
@Entity
public class Dojo extends Ubicacion {
    @OneToOne
    private Entrenador entrenadorCampeon;
    @OneToOne
    private Bicho bichoCampeon;
    @OneToOne
    private List<Bicho> bichos;


    public Dojo(String nombre) {
        super(nombre);
        bichos = new ArrayList<>();
    }

    @Override
    public boolean puedeDejarAbandonar(Entrenador entrenador) {
        return false;
    }

    @Override
    public void recibirAbandonado(Entrenador entrenador, Bicho bichoAAbandonar) {
        throw new RuntimeException(Ubicacion.ERROR_ABANDONO);
    }

    @Override
    public List<Bicho> bichomonesPara(Entrenador entrenador) {
        return bichos;
    }

    public boolean tieneCampeon() {
        return false;
    }

    public void declararCampeones(Entrenador entrenador, Bicho bicho) {
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
}
