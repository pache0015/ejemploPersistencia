package ar.edu.unq.epers.bichomon.backend.model.ubicacion;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.duelo.ResultadoCombate;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Ubicacion {
    @Id
    protected String nombre;

    public Ubicacion(String nombre) {
        this.nombre = nombre;
    }

    public abstract void recibirAbandonado(Entrenador entrenador, Bicho bichoAAbandonar);

    protected Ubicacion() {
    }

    public abstract Bicho bichomonPara(Entrenador entrenador);

    public abstract Entrenador getEntrenadorCampeon();
    public abstract ResultadoCombate comenzarDuelo(Entrenador entrenador);

    public abstract void declararCampeones(Entrenador entrenador, Bicho bicho);
}
