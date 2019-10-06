package ar.edu.unq.epers.bichomon.backend.jdbc.service.leaderboard;

import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;

import java.util.List;

public interface LeaderboardService {
    List<Entrenador> campeones();
    Especie especieLider();
}
