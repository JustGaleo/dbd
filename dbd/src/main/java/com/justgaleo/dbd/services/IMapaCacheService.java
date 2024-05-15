package com.justgaleo.dbd.services;

import java.util.Optional;

import com.justgaleo.dbd.models.entity.MapaCache;

public interface IMapaCacheService {
	
	public void transfer();
	
	public MapaCache getRandomly();
	
	public Iterable<MapaCache> findAll();
	
	public Optional<MapaCache> findById(Long id);
	
	public MapaCache save(MapaCache mapCache);
	
	public void deleteById(Long id);
	
	public long count();

}
