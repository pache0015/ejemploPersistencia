package ar.edu.unq.epers.bichomon.backend.jdbc.service.bicho;

public class ErrorBichoNoPerteneceAEntrenador extends RuntimeException {

    public static final String BICHO_NO_PERTENCE_AL_ENTRENADOR = "El bicho no pertence al entrenador";

    ErrorBichoNoPerteneceAEntrenador() {
        super(BICHO_NO_PERTENCE_AL_ENTRENADOR);
    }
}
