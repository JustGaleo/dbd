package com.justgaleo.dbd.services;

import java.util.Optional;

import com.justgaleo.dbd.models.entity.Partida;

public interface IPartidaService {
	
	public Iterable<Partida> findAll();
	
	public Optional<Partida> findById(Long id);
	
	public Iterable<Partida> findByMap(String id);
	
	public Iterable<Partida> findByRonda(Long id);
	
	public Iterable<Partida> findByCastigo();
	
	public Partida save(Partida partida);
	
	public void deleteById(Long id);

}
