package com.justgaleo.dbd.services;

import java.util.Optional;

import com.justgaleo.dbd.models.entity.MapaCat;

public interface IMapaCatService {
	
	public Iterable<MapaCat> findAll();
	
	public Optional<MapaCat> findById(Long id);
	
	public MapaCat save(MapaCat mapCat);
	
	public void deleteById(Long id);
	
	public void deleteAll();
	
	public void createDB();
	
	public MapaCat findByName(String name);
	
	public long count();

}
