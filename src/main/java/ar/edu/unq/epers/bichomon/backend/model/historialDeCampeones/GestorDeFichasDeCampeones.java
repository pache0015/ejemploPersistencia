package ar.edu.unq.epers.bichomon.backend.model.historialDeCampeones;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@Entity
public class GestorDeFichasDeCampeones {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;
    @OneToMany(cascade=CascadeType.ALL)
    private List<FichaDeCampeon> fichas;

    public List<FichaDeCampeon> getAllFichas() {return fichas;}

    public GestorDeFichasDeCampeones(){
        this.fichas = new ArrayList<FichaDeCampeon>();
    }
    public void addNuevoCampeon(Entrenador entrenador, Bicho bicho, LocalDate fechaInicio) {
        if(!contieneCampeon(entrenador, fechaInicio)){
            fichas.add(new FichaDeCampeon(entrenador, bicho, LocalDate.now()));
        }
    }
    private boolean contieneCampeon(Entrenador entrenadorCampeon, LocalDate fechaInicio) {
        Boolean contiene = false;
        for (FichaDeCampeon ficha: fichas){
            contiene = (ficha.getCampeon().equals(entrenadorCampeon) &&
                    ficha.getFechaInicio().equals(fechaInicio));
        }
        return contiene;
    }

    public void finDeCampeon(Entrenador entrenadorCampeon, LocalDate fechaFin) {

        for (FichaDeCampeon ficha: fichas){
            if(ficha.getCampeon().equals(entrenadorCampeon)){
                ficha.setFechaFin(fechaFin);
            }
        }
    }

    public Integer getId() {
        return id;
    }

}
