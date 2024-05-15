package com.justgaleo.dbd.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.justgaleo.dbd.ldn.ImageProcessorPerks;
import com.justgaleo.dbd.models.entity.PerkCat;
import com.justgaleo.dbd.models.repository.IPerkCatRepository;
import com.justgaleo.dbd.services.IPerkCatService;

@Service
public class PerkCatServiceImpl implements IPerkCatService{
	
	@Value("${app.images.directory}")
	private String imagesDirectory;
	
	@Autowired(required=true)
	protected IPerkCatRepository repository;

	@Override
	@Transactional(readOnly = true)
	public Iterable<PerkCat> findAll() {
		return repository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<PerkCat> findById(Long id) {
		return repository.findById(id);
	}

	@Override
	@Transactional
	public PerkCat save(PerkCat perkCat) {
		return repository.save(perkCat);
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		repository.deleteById(id);
		
	}
	@Override
	public List<PerkCat> getRandomly(int perks) {
		List<PerkCat> list = (List<PerkCat>) repository.findAll();
		List<PerkCat> result = new ArrayList<>();
		while(perks > 0) {
			PerkCat aux = new PerkCat();
			Random random = new Random();
	        int randomIndex = random.nextInt(list.size());
	        aux = list.get(randomIndex);
	        list.remove(randomIndex);
	        result.add(aux);
	        perks--;
		}
		return result;
	}

	@Override
	public void deleteAll() {
		repository.deleteAll();
		
	}

	@Override
	public void createDB() {
		ImageProcessorPerks image = new ImageProcessorPerks();
		List<PerkCat> list = image.processImages(imagesDirectory+"/perks");
		repository.saveAll(list);
		
	}

	@Override
	public PerkCat findByName(String name) {
		List<PerkCat> list = (List<PerkCat>) repository.findAll();
		PerkCat aux = new PerkCat();
		for (PerkCat map : list) {
			if(map.getNombre().equals(name)) {
				aux = map;
				break;
			}
		}
		return aux;
	}

}
