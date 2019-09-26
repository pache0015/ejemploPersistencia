package ar.edu.unq.epers.bichomon.backend.model.condicion;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;

import javax.persistence.Entity;
import java.time.LocalDate;
import java.time.temporal.TemporalAmount;

@Entity
public class CondicionBasadaEnEdad extends Condicion {
    private final LocalDate fechaActual;
    private final TemporalAmount tiempo;

    public CondicionBasadaEnEdad(TemporalAmount temporalAmount, LocalDate date) {
        this.tiempo = temporalAmount;
        this.fechaActual = date;
    }

    @Override
    public Boolean evaluar(Bicho bicho) {
        return bicho.getFechaDeCaptura().plus(tiempo).isBefore(fechaActual) ||
                bicho.getFechaDeCaptura().plus(tiempo).isEqual(fechaActual);
    }
}
