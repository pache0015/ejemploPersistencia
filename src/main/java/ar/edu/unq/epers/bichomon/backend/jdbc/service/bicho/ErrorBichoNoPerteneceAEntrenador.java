package ar.edu.unq.epers.bichomon.backend.jdbc.service.bicho;

public class ErrorBichoNoPerteneceAEntrenador extends RuntimeException {

    ErrorBichoNoPerteneceAEntrenador(String errorMessage) {
        super(errorMessage);
    }
}
