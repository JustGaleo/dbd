package com.justgaleo.dbd.services.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.justgaleo.dbd.models.entity.Ronda;
import com.justgaleo.dbd.models.repository.IRondaRepository;
import com.justgaleo.dbd.services.IRondaService;

@Service
public class RondaServiceImpl implements IRondaService{
	
	@Autowired
	private IRondaRepository repository;

	@Override
	@Transactional(readOnly = true)
	public Iterable<Ronda> findAll() {
		return repository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Ronda> findById(Long id) {
		return repository.findById(id);
	}

	@Override
	@Transactional
	public Ronda save(Ronda ronda) {
		return repository.save(ronda);
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		repository.deleteById(id);
		
	}
	
	public Ronda createDefault() {
		Ronda ronda = new Ronda();
		ronda.setFin(false);
		return save(ronda);
	}

}
