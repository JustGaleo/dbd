package com.justgaleo.dbd.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.justgaleo.dbd.models.entity.Partida;
import com.justgaleo.dbd.models.repository.IPartidaRepository;
import com.justgaleo.dbd.services.IPartidaService;

@Service
public class PartidaServiceImpl implements IPartidaService{

	@Autowired
	private IPartidaRepository repository;

	@Override
	@Transactional(readOnly = true)
	public Iterable<Partida> findAll() {
		return repository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Partida> findById(Long id) {
		return repository.findById(id);
	}

	@Override
	@Transactional
	public Partida save(Partida partida) {
		return repository.save(partida);
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		repository.deleteById(id);
	}

	@Override
	public Iterable<Partida> findByMap(String id) {
		List<Partida> result = (List<Partida>) repository.findAll();
		return result.stream().filter(obj -> obj.getIdMapa() == id)
        .collect(Collectors.toList());
	}

	@Override
	public Iterable<Partida> findByRonda(Long id) {
		List<Partida> result = (List<Partida>) repository.findAll();
		return result.stream().filter(obj -> obj.getIdRonda() == id)
        .collect(Collectors.toList());
	}

	@Override
	public Iterable<Partida> findByCastigo() {
		List<Partida> result = (List<Partida>) repository.findAll();
		return result.stream().filter(obj -> obj.isCastigo())
        .collect(Collectors.toList());
	}

}
