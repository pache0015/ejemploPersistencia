package bichomon.backend.model;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Condicion;

import java.time.LocalDate;
import java.time.temporal.TemporalAmount;

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
