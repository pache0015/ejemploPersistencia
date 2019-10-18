package ar.edu.unq.epers.bichomon.backend.model.entrenador;

public class LimiteBicho extends RuntimeException {

    public static final String ERROR_LIMITE_DE_BICHOS = "Tu lista esta llena, sube de nivel para capturar mas bichomons";

    LimiteBicho() {
        super(LimiteBicho.ERROR_LIMITE_DE_BICHOS);
    }
}
