package com.justgaleo.dbd.services;

import java.util.Optional;

import com.justgaleo.dbd.models.entity.CastigoCache;

public interface ICastigoCacheService {
	
	public void transfer();
	
	public CastigoCache getRandomly();
	
	public Iterable<CastigoCache> findAll();
	
	public Optional<CastigoCache> findById(Long id);
	
	public CastigoCache save(CastigoCache castigoCache);
	
	public void deleteById(Long id);
	
	public long count();

}
