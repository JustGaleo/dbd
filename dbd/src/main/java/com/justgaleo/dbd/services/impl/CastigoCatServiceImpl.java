package com.justgaleo.dbd.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.justgaleo.dbd.ldn.ImageProcessorCastigos;
import com.justgaleo.dbd.models.entity.CastigoCat;
import com.justgaleo.dbd.models.repository.ICastigoCatRepository;
import com.justgaleo.dbd.services.ICastigoCatService;

@Service
public class CastigoCatServiceImpl implements ICastigoCatService{
	
	@Value("${app.images.directory}")
	private String imagesDirectory;

	@Autowired(required=true)
	protected ICastigoCatRepository repository;

	@Override
	@Transactional(readOnly = true)
	public Iterable<CastigoCat> findAll() {
		return repository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<CastigoCat> findById(Long id) {
		return repository.findById(id);
	}

	@Override
	@Transactional
	public CastigoCat save(CastigoCat castigoCat) {
		return repository.save(castigoCat);
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
		ImageProcessorCastigos image = new ImageProcessorCastigos();
		List<CastigoCat> list = image.processImages(imagesDirectory+"/castigos");
		repository.saveAll(list);
	}

	@Override
	public CastigoCat findByName(String name) {
		List<CastigoCat> list = (List<CastigoCat>) repository.findAll();
		CastigoCat aux = new CastigoCat();
		for (CastigoCat map : list) {
			if(map.getNombre().equals(name)) {
				aux = map;
				break;
			}
		}
		return aux;
	}

}
