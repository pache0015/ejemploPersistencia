package ar.edu.unq.epers.bichomon.backend.jdbc.service.leaderboard;

import ar.edu.unq.epers.bichomon.backend.jdbc.service.Service;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;

import java.util.List;

import static ar.edu.unq.epers.bichomon.backend.jdbc.service.runner.TransactionRunner.run;

public class LeaderboardServiceImpl extends Service implements LeaderboardService {

    public List<Entrenador> campeones() {
        return run(() -> {
            return entrenadorDao.recuperarCampeones();
        });
    }

    @Override
    public Especie especieLider() {
        return run(() -> {
            return especieDao.recuperarEspecieLider();
        });
    }

    @Override
    public List<Entrenador> lideres() {
        return run(() -> {
            return entrenadorDao.recuperarLideres();
        });
    }
}