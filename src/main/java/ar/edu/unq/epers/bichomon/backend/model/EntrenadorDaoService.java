package ar.edu.unq.epers.bichomon.backend.model;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;

import static ar.edu.unq.epers.bichomon.backend.jdbc.service.runner.TransactionRunner.run;


public class EntrenadorDaoService {

    private EntrenadorDao entrenadorDao;


    public void setEntrenadorDao(HibernateEntrenadorDao hibernateEntrenadorDao){this.entrenadorDao = hibernateEntrenadorDao;}


    public void guardarEntrenador(Entrenador entrenador){ run(() -> { this.entrenadorDao.guardar(entrenador);});}
    public Entrenador recuperarEntrenador(String nombre_entrenador){
        return run(()-> this.entrenadorDao.recuperar(nombre_entrenador));
    }



}
