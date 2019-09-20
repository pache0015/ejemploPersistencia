package bichomon.backend.service.especie;

import ar.edu.unq.epers.bichomon.backend.dao.impl.BichoDao;
import ar.edu.unq.epers.bichomon.backend.dao.impl.HibernateDAO;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;

import static ar.edu.unq.epers.bichomon.backend.service.runner.TransactionRunner.run;

public class Service{
    private BichoDao dao;

    public Service(BichoDao bichoDao) {
        this.dao = bichoDao;
    }

    public void guardarBicho(Bicho bicho){
        run(() -> {
        this.dao.guardar(bicho);
        });
    }
    public Bicho recuperarBicho(Long id_bicho){
       return run(()-> this.dao.recuperar(id_bicho));
    }
}
