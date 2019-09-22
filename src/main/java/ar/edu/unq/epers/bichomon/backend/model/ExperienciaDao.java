package ar.edu.unq.epers.bichomon.backend.model;

public interface ExperienciaDao {

    Experiencia recuperar(Long id);
    void guardar(Experiencia tabla);
}
