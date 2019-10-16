package ar.edu.unq.epers.bichomon.backend.jdbc.service.experiencia;

import ar.edu.unq.epers.bichomon.backend.model.entrenador.Experiencia;

public interface ExperienciaDao {

    Experiencia recuperar(Long id);
    void guardar(Experiencia experiencia);
}
