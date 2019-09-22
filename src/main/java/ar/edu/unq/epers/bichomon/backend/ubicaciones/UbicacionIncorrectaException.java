package ar.edu.unq.epers.bichomon.backend.ubicaciones;

public class UbicacionIncorrectaException extends RuntimeException{


    //private static final long serialVersionUID = 1L;

    public UbicacionIncorrectaException() {
        super("No se encuentra en la ubicacion correcta para hacer esta accion");
    }
}
