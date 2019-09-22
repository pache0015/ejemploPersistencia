package ar.edu.unq.epers.bichomon.backend.model;

import ar.edu.unq.epers.bichomon.backend.dao.impl.BichoDao;
import ar.edu.unq.epers.bichomon.backend.dao.impl.HibernateBichoDao;
import ar.edu.unq.epers.bichomon.backend.dao.impl.HibernateDAO;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Nivel;
import ar.edu.unq.epers.bichomon.backend.service.runner.TransactionRunner;


import static ar.edu.unq.epers.bichomon.backend.service.runner.TransactionRunner.run;

public class Service {
    private BichoDao bichoDao;
    private EntrenadorDao entrenadorDao;
    private NivelDao nivelDao;
    private ExperienciaDao experienciaDao;


    public void setBichoDao(HibernateBichoDao hibernateBichoDao) {
        this.bichoDao = hibernateBichoDao;
    }
    public void setEntrenadorDao(HibernateEntrenadorDao hibernateEntrenadorDao){this.entrenadorDao = hibernateEntrenadorDao;}
    public void setNivelDao(HibernateNivelDao hibernateNivelDao){this.nivelDao = hibernateNivelDao;}
    public void setExperiencia(HibernateExperienciaDao experiencia){this.experienciaDao = experiencia;}

    public void guardarNivel(Nivel nivel){
        run(() -> {
            this.nivelDao.guardar(nivel);
        });
    }
    public Nivel recuperarNivel(Long id_nivel){
        return run(() -> this.nivelDao.recuperar(id_nivel));
    }
    public void guardarEntrenador(Entrenador entrenador){
        run(() -> {
            this.entrenadorDao.guardar(entrenador);
        });
    }
    public Nivel getNivelDeEntrenador(Integer puntosDeExperiencia){
        return TransactionRunner.run(() -> this.entrenadorDao.getNivel(puntosDeExperiencia));
    }
    public Entrenador recuperarEntrenador(Long id_entrenador){
        return TransactionRunner.run(()-> this.entrenadorDao.recuperar(id_entrenador));
    }
    public void guardarBicho(Bicho bicho){
        run(() -> { this.bichoDao.guardar(bicho);});
    }
    public Bicho recuperarBicho(Long id_bicho){
       return run(()-> this.bichoDao.recuperar(id_bicho));
    }
    public Experiencia recuperarTabla(Long id_tabla) { return run(() -> this.experienciaDao.recuperar(id_tabla));
    }

    public void guardarExperiencia(Experiencia tabla) {
        run (() -> { this.experienciaDao.guardar(tabla);});
    }
}
