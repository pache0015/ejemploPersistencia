package ar.edu.unq.epers.bichomon.backend.model.ubicacion;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.duelo.ResultadoCombate;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import javax.persistence.*;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Ubicacion {
    @Id
    protected String nombre;

    public Ubicacion(String nombre) {
        this.nombre = nombre;
    }

    public abstract Boolean puedeDejarAbandonar(Entrenador entrenador);

    public abstract void recibirAbandonado(Entrenador entrenador, Bicho bichoAAbandonar);

    public abstract List<Bicho> bichomonesPara(Entrenador entrenador);

    protected Ubicacion() {
    }
    public Bicho bichomonPara(Entrenador entrenador) {
        return bichomonesPara(entrenador).get(0);
    }

    public abstract Entrenador getEntrenadorCampeon();
    public abstract Bicho getBichoCampeon();
    public abstract ResultadoCombate comenzarDuelo(Bicho bicho);

    public abstract void declararCampeones(Entrenador entrenador, Bicho bicho);

}
