package com.justgaleo.dbd.services;

import java.util.List;
import java.util.Optional;

import com.justgaleo.dbd.models.entity.PerkCat;

public interface IPerkCatService {
	
	public Iterable<PerkCat> findAll();
	
	public Optional<PerkCat> findById(Long id);
	
	public PerkCat save(PerkCat perkCat);
	
	public void deleteById(Long id);
	
	public List<PerkCat> getRandomly(int perks);
	
	public void deleteAll();
	
	public void createDB();
	
	public PerkCat findByName(String name);

}
