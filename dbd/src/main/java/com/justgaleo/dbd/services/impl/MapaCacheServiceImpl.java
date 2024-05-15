package com.justgaleo.dbd.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.justgaleo.dbd.models.entity.MapaCache;
import com.justgaleo.dbd.models.entity.MapaCat;
import com.justgaleo.dbd.models.repository.IMapaCacheRepository;
import com.justgaleo.dbd.services.IMapaCacheService;
import com.justgaleo.dbd.services.IMapaCatService;

@Service
public class MapaCacheServiceImpl implements IMapaCacheService{
	
	@Autowired
	protected IMapaCacheRepository repository;
	
	@Autowired
	protected IMapaCatService service;

	@Override
	public void transfer() {
		Iterable<MapaCat> listCat = service.findAll();
		for (MapaCat mapaCat : listCat) {
			MapaCache aux = new MapaCache();
			aux.setId(mapaCat.getId());
			aux.setNombre(mapaCat.getNombre());
			repository.save(aux);
		}
	}

	@Override
	public Iterable<MapaCache> findAll() {
		return repository.findAll();
	}

	@Override
	public Optional<MapaCache> findById(Long id) {
		return repository.findById(id);
	}

	@Override
	public MapaCache save(MapaCache mapCache) {
		return repository.save(mapCache);
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
	public MapaCache getRandomly() {
		List<MapaCache> list = (List<MapaCache>) repository.findAll();
		Random random = new Random();
        int randomIndex = random.nextInt(list.size());
        long id = list.get(randomIndex).getId();
        repository.deleteById(id);
		return list.get(randomIndex);
	}

}
