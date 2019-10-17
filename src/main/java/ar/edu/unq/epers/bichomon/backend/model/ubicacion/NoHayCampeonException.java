package ar.edu.unq.epers.bichomon.backend.model.ubicacion;

public class NoHayCampeonException extends RuntimeException {
    public static String MENSAJE_ERROR = "No hay un campeon asignado";
    public NoHayCampeonException() {
        super(MENSAJE_ERROR);
    }
}
