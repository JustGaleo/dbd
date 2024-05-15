package com.justgaleo.dbd.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.justgaleo.dbd.models.entity.Ronda;
import com.justgaleo.dbd.services.IRondaService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(value = "/ronda")
public class RondaController {

	@Autowired
	protected IRondaService service;
	
	@GetMapping
	public ResponseEntity<Iterable<Ronda>> readAll(){
		return ResponseEntity.ok().body(service.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Ronda> readById(@PathVariable Long id){
		Optional<Ronda> o = service.findById(id);
		if(o.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(o.get());
	}
	
	@GetMapping("/create")
	public ResponseEntity<?> create(){
		Ronda entityDb = service.createDefault();
		return ResponseEntity.status(HttpStatus.CREATED).body(entityDb);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id){
		service.deleteById(id);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/modify/{id}")
	public ResponseEntity<?> modifyMapaCat(@PathVariable Long id){

		Optional<Ronda> o = service.findById(id);
		if(o.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		Ronda rondaDb = o.get();
		rondaDb.setFin(true);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(rondaDb));
	}

}
