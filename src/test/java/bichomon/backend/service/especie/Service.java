package bichomon.backend.service.especie;

import ar.edu.unq.epers.bichomon.backend.dao.impl.HibernateDAO;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;

import static ar.edu.unq.epers.bichomon.backend.service.runner.TransactionRunner.run;

public class Service{
    private HibernateDAO bicho;

    public Service(HibernateDAO<Bicho> bichoDao) {
        this.bicho = bichoDao;
    }

    public void guardarBicho(Bicho bicho){
        run(() -> {
        this.bicho.guardar(bicho);
        });
    }
    public void recuperarBicho(Long id_bicho){
        run(()->{
           bicho.recuperar(id_bicho);
        });
    }
}
