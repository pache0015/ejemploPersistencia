package ar.edu.unq.epers.bichomon.backend.model.historialDeCampeones;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;

import java.time.LocalDate;

public class FichaDeCampeon {

    private Entrenador campeon;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private Bicho bichoCampeon;

    public FichaDeCampeon(Entrenador campeon, Bicho bicho, LocalDate fechaInicio){
        this.campeon = campeon;
        this.bichoCampeon = bicho;
        this.fechaInicio = fechaInicio;
    }

    public void setFechaFin(LocalDate fechaFin){
        this.fechaFin = fechaFin;
    }

    public LocalDate getFechaFin() {return fechaFin; }

    public Entrenador getCampeon() {return campeon;}

    public Bicho getBichoCampeon() {return bichoCampeon;}

    public LocalDate getFechaInicio() {return fechaInicio;}
}
