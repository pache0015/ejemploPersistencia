package ar.edu.unq.epers.bichomon.backend.model.ubicacion;

public class ErrorDeBusquedaNoExitosa extends RuntimeException {

    public static String MENSAJE_ERROR = "La busqueda no fue exitosa";

    public ErrorDeBusquedaNoExitosa() {
        super(ErrorDeBusquedaNoExitosa.MENSAJE_ERROR);
    }

}
