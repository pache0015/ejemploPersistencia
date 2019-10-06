package ar.edu.unq.epers.bichomon.backend.model.historialDeCampeones;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@Entity
public class FichaDeCampeon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;
    @OneToOne
    private Entrenador campeon;
    @Column
    private LocalDate fechaInicio;
    @Column
    private LocalDate fechaFin;
    @OneToOne
    private Bicho bichoCampeon;

    public FichaDeCampeon(Entrenador campeon, Bicho bicho, LocalDate fechaInicio){
        this.campeon = campeon;
        this.bichoCampeon = bicho;
        this.fechaInicio = fechaInicio;
    }

    public FichaDeCampeon() {
    }

    public Integer getId(){ return this.id; }

    public void setFechaFin(LocalDate fechaFin){
        this.fechaFin = fechaFin;
    }

    public LocalDate getFechaFin() {return fechaFin; }

    public Entrenador getCampeon() {return campeon;}

    public Bicho getBichoCampeon() {return bichoCampeon;}

    public LocalDate getFechaInicio() {return fechaInicio;}

    public long duracionComoCampeon(){
        String startDate = "2016 01 02";
        String passedDate = "2016 02 29";

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy MM dd");
        LocalDate date1 = LocalDate.parse(fechaInicio.toString(), formatter);
        LocalDate date2 = LocalDate.parse(fechaFin.toString(), formatter);

        return ChronoUnit.DAYS.between(date1, date2);
    }
}
