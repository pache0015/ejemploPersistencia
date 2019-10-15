package ar.edu.unq.epers.bichomon.backend.model.ubicacion;

public class ErrorAbandonoImposible extends RuntimeException {

    public static String MENSAJE_ERROR = "No se pudo abandonar exitosamente en esta ubicacion";

    public ErrorAbandonoImposible() {
        super(ErrorAbandonoImposible.MENSAJE_ERROR);
    }
}
