package com.justgaleo.dbd.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.justgaleo.dbd.ldn.ImageProcessorMapas;
import com.justgaleo.dbd.models.entity.MapaCat;
import com.justgaleo.dbd.models.repository.IMapaCatRepository;
import com.justgaleo.dbd.services.IMapaCatService;

@Service
public class MapaCatServiceImpl implements IMapaCatService {
	
	@Value("${app.images.directory}")
	private String imagesDirectory;

	@Autowired(required = true)
	protected IMapaCatRepository repository;

	@Override
	public Iterable<MapaCat> findAll() {
		return repository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<MapaCat> findById(Long id) {
		return repository.findById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public MapaCat findByName(String name) {
		List<MapaCat> list = (List<MapaCat>) repository.findAll();
		MapaCat aux = new MapaCat();
		for (MapaCat map : list) {
			if(map.getNombre().equals(name)) {
				aux = map;
				break;
			}
		}
		return aux;
	}

	@Override
	@Transactional
	public MapaCat save(MapaCat mapCat) {
		return repository.save(mapCat);
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		repository.deleteById(id);

	}

	@Override
	public void deleteAll() {
		repository.deleteAll();

	}

	@Override
	public void createDB() {
		ImageProcessorMapas image = new ImageProcessorMapas();
		List<MapaCat> list = image.processImages(imagesDirectory+"/mapas");
		repository.saveAll(list);
	}
	
	@Override
	public long count() {
		return repository.count();
	}

}
