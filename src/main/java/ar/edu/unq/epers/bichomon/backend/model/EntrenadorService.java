package ar.edu.unq.epers.bichomon.backend.model;

import ar.edu.unq.epers.bichomon.backend.dao.impl.BichoDao;
import ar.edu.unq.epers.bichomon.backend.dao.impl.HibernateBichoDao;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.service.runner.TransactionRunner;
import static ar.edu.unq.epers.bichomon.backend.service.runner.TransactionRunner.run;

public class EntrenadorService {

    private EntrenadorDao entrenadorDao;


    public void setEntrenadorDao(HibernateEntrenadorDao hibernateEntrenadorDao){this.entrenadorDao = hibernateEntrenadorDao;}


    public void guardarEntrenador(Entrenador entrenador){ run(() -> { this.entrenadorDao.guardar(entrenador);});}
    public Entrenador recuperarEntrenador(Long id_entrenador){
        return TransactionRunner.run(()-> this.entrenadorDao.recuperar(id_entrenador));
    }



}
