package ar.edu.unq.epers.bichomon.backend.model.ubicacion;

public class UbicacionIncorrectaException extends RuntimeException{
    public static String MENSAJE_ERROR = "No se encuentra en la ubicacion correcta para hacer esta accion";
    public UbicacionIncorrectaException() {
        super(UbicacionIncorrectaException.MENSAJE_ERROR);
    }

}
