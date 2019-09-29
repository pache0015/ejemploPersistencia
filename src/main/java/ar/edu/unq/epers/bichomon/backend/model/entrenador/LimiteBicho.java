package ar.edu.unq.epers.bichomon.backend.model.entrenador;

public class LimiteBicho extends RuntimeException {

    LimiteBicho(String errorMessage) {
        super(errorMessage);
    }
}
