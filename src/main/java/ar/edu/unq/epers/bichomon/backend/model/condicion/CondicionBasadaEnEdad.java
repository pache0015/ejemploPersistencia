package ar.edu.unq.epers.bichomon.backend.model.condicion;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.time.LocalDate;
import java.time.temporal.TemporalAmount;

@Entity
public class CondicionBasadaEnEdad extends Condicion {
    @Column
    private LocalDate fechaActual;
    @Column
    private Long tiempoEnDias;

    public CondicionBasadaEnEdad(Long tiempoEnDias, LocalDate date) {
        this.tiempoEnDias = tiempoEnDias;
        this.fechaActual = date;
    }

    public CondicionBasadaEnEdad() {
    }

    @Override
    public Boolean evaluar(Bicho bicho) {
        return bicho.getFechaDeCaptura().plusDays(tiempoEnDias).isBefore(fechaActual) ||
                bicho.getFechaDeCaptura().plusDays(tiempoEnDias).isEqual(fechaActual);
    }
}
