package ar.edu.unq.epers.bichomon.backend.jdbc.service.experiencia;

import ar.edu.unq.epers.bichomon.backend.model.entrenador.Experiencia;

public class ExperienciaService {

    private ExperienciaDao experienciaDao;

    public ExperienciaService(ExperienciaDao experienciaDao){
        this.experienciaDao = experienciaDao;
    }

    public void guardar(Experiencia experiencia){
        this.experienciaDao.guardar(experiencia);
    }
    public Experiencia recuperar(Long idExperiencia){
        return  this.experienciaDao.recuperar(idExperiencia);
    }
}
