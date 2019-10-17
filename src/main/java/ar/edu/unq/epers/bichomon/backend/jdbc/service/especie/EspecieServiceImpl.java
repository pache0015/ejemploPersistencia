package ar.edu.unq.epers.bichomon.backend.jdbc.service.especie;

import java.util.List;

import ar.edu.unq.epers.bichomon.backend.jdbc.service.Service;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import static ar.edu.unq.epers.bichomon.backend.jdbc.service.runner.TransactionRunner.run;

public class EspecieServiceImpl extends Service implements EspecieService {

	@Override
	public void crearEspecie(Especie especie){
		especieDao.guardar(especie);
	}

	@Override
	public Especie getEspecie(String nombreEspecie){
		Especie especie = especieDao.recuperar(nombreEspecie);
		if(especie == null){
			throw new EspecieNoExistente(nombreEspecie);
		}
		return especie;
	}

	@Override
	public List<Especie> getAllEspecies(){
		return especieDao.recuperarTodos();
	}

	@Override
	public Bicho crearBicho(String nombreEspecie, String nombreBicho){
		Especie especie = especieDao.recuperar(nombreEspecie);
		Bicho bicho = especie.crearBicho(nombreBicho);
		especieDao.actualizar(especie);
		return bicho;
	}

	@Override
	public List<Especie> especiesMasPopulares() {
		return run(() -> {
			return this.especieDao.recuperarEspeciesMasPopulares();
		});
	}

	@Override
	public List<Especie> especiesMenosPopulares() {
		return run(() -> {
			return this.especieDao.recuperarEspeciesMenosPopulares();
		});
	}
}
