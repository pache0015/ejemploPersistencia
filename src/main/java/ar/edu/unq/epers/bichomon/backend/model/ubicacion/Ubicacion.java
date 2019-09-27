package ar.edu.unq.epers.bichomon.backend.model.ubicacion;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Ubicacion {
    @Id
    protected String nombre;

    private Entrenador entrenadorCampeon;

    public Ubicacion(String nombre) {
        this.nombre = nombre;
    }

    public abstract boolean puedeDejarAbandonar(Entrenador entrenador);

    public abstract void recibirAbandonado(Entrenador entrenador, Bicho bichoAAbandonar);

    public abstract List<Bicho> bichomonesPara(Entrenador entrenador);

    public Bicho bichomonPara(Entrenador entrenador) {
        return bichomonesPara(entrenador).get(0);
    }

    public abstract Entrenador getEntrenadorCampeon();
}
