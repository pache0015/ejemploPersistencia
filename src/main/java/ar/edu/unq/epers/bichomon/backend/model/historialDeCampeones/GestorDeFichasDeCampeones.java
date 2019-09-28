package ar.edu.unq.epers.bichomon.backend.model.historialDeCampeones;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
            fichas.add(new FichaDeCampeon(entrenador, bicho, fechaInicio));
        }
    }

    private Boolean contieneCampeon(Entrenador entrenadorCampeon, LocalDate fechaInicio) {
        return fichas.stream().anyMatch(ficha -> ficha.getCampeon().equals(entrenadorCampeon) &&
                ficha.getFechaInicio().equals(fechaInicio));
    }

    public void finDeCampeon(Entrenador entrenadorCampeon, LocalDate fechaFin) {
        fichas.stream().filter(each -> each.getCampeon().equals(entrenadorCampeon))
                .collect(Collectors.toList())
                .stream().forEach(ficha -> ficha.setFechaFin(fechaFin));
    }

    public FichaDeCampeon getFichaDeCampeon(Entrenador entrenadorCampeon) {
        return fichas.stream().filter(each -> each.getCampeon().equals(entrenadorCampeon))
                .collect(Collectors.toList())
                .stream().findFirst().get();
    }

    public Integer getId() {
        return id;
    }

}
