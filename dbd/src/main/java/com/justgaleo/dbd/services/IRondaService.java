package com.justgaleo.dbd.services;

import java.util.Optional;

import com.justgaleo.dbd.models.entity.Ronda;

public interface IRondaService {
	
	public Iterable<Ronda> findAll();
	
	public Optional<Ronda> findById(Long id);
	
	public Ronda save(Ronda ronda);
	
	public void deleteById(Long id);
	
	public Ronda createDefault();
}
