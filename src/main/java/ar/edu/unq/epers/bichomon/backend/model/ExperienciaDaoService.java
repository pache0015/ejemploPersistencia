package ar.edu.unq.epers.bichomon.backend.model;


import static ar.edu.unq.epers.bichomon.backend.jdbc.service.runner.TransactionRunner.run;

public class ExperienciaDaoService {

    private ExperienciaDao experienciaDao;

    public void setExperiencia(HibernateExperienciaDao experiencia){this.experienciaDao = experiencia;}

    public Experiencia recuperarTabla(Long id_tabla){
        return run(() -> this.experienciaDao.recuperar(id_tabla));
    }
    public void guardarExperiencia(Experiencia tabla) {
        run (() -> { this.experienciaDao.guardar(tabla);});
    }
}
