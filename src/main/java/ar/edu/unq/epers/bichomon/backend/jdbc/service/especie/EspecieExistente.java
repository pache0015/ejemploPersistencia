package ar.edu.unq.epers.bichomon.backend.jdbc.service.especie;

/**
 * Situación excepcional en que una especie buscada no es
 * encontrada.
 */
public class EspecieExistente extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public EspecieExistente(String especie) {
        super("La especie [" + especie + "]" + " ya se encuentra en el sistema");
    }

}
