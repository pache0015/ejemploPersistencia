package ar.edu.unq.epers.bichomon.backend.jdbc.service.especie;

import java.util.List;

import ar.edu.unq.epers.bichomon.backend.jdbc.dao.EspecieDao;
import ar.edu.unq.epers.bichomon.backend.jdbc.dao.GuarderiaDao;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Guarderia;

import static ar.edu.unq.epers.bichomon.backend.jdbc.service.runner.TransactionRunner.run;


public class EspecieServiceImpl implements EspecieService {

	private EspecieDao especieDao;
	private GuarderiaDao guarderiaDao;

	public EspecieServiceImpl(EspecieDao especieDao, GuarderiaDao guarderiaDao){
		this.especieDao = especieDao;
		this.guarderiaDao = guarderiaDao;
	}
	

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

	@Override
	public void guardarGuarderia(Guarderia guarderia) {
		run(() -> {
			this.guarderiaDao.guardar(guarderia);
		});
	}

}
