package com.justgaleo.dbd.services;

import java.util.Optional;

import com.justgaleo.dbd.models.entity.CastigoCat;

public interface ICastigoCatService {
	
	public Iterable<CastigoCat> findAll();
	
	public Optional<CastigoCat> findById(Long id);
	
	public CastigoCat save(CastigoCat castigoCat);
	
	public void deleteById(Long id);
	
	public void deleteAll();
	
	public void createDB();
	
	public CastigoCat findByName(String name);


}
