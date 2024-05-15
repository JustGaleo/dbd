package com.justgaleo.dbd.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.justgaleo.dbd.models.entity.CastigoCache;
import com.justgaleo.dbd.models.entity.CastigoCat;
import com.justgaleo.dbd.models.repository.ICastigoCacheRepository;
import com.justgaleo.dbd.services.ICastigoCacheService;
import com.justgaleo.dbd.services.ICastigoCatService;

@Service
public class CastigoCacheServiceImpl implements ICastigoCacheService{

	@Autowired
	protected ICastigoCacheRepository repository;
	
	@Autowired
	protected ICastigoCatService service;

	@Override
	public void transfer() {
		Iterable<CastigoCat> listCat = service.findAll();
		for (CastigoCat castigoCat : listCat) {
			CastigoCache aux = new CastigoCache();
			aux.setId(castigoCat.getId());
			aux.setNombre(castigoCat.getNombre());
			repository.save(aux);
		}
	}

	@Override
	public Iterable<CastigoCache> findAll() {
		return repository.findAll();
	}

	@Override
	public Optional<CastigoCache> findById(Long id) {
		return repository.findById(id);
	}

	@Override
	public CastigoCache save(CastigoCache castigoCache) {
		return repository.save(castigoCache);
	}

	@Override
	public void deleteById(Long id) {
		repository.deleteById(id);
		
	}

	@Override
	public long count() {
		return repository.count();
	}

	@Override
	public CastigoCache getRandomly() {
		if(count() <= 0) {
			transfer();
		}
		List<CastigoCache> list = (List<CastigoCache>) repository.findAll();
		Random random = new Random();
        int randomIndex = random.nextInt(list.size());
        long id = list.get(randomIndex).getId();
        repository.deleteById(id);
		return list.get(randomIndex);
	}
}
