package com.justgaleo.dbd.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.justgaleo.dbd.models.entity.Partida;
import com.justgaleo.dbd.services.IPartidaService;

import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(value = "/partida")
public class PartidaController {

	@Autowired
	protected IPartidaService service;
	
	@GetMapping
	public ResponseEntity<Iterable<Partida>> readAll(){
		return ResponseEntity.ok().body(service.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Partida> readById(@PathVariable Long id){
		Optional<Partida> o = service.findById(id);
		if(o.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(o.get());
	}
	
	@GetMapping("/byMapa/{id}")
	public ResponseEntity<Iterable<Partida>> readByMapaId(@PathVariable String id){
		return ResponseEntity.ok().body(service.findByMap(id));
	}
	
	@GetMapping("/byRonda/{id}")
	public ResponseEntity<Iterable<Partida>> readByRondaId(@PathVariable Long id){
		return ResponseEntity.ok().body(service.findByRonda(id));
	}
	
	@GetMapping("/byCastigo")
	public ResponseEntity<Iterable<Partida>> readByCastigo(){
		return ResponseEntity.ok().body(service.findByCastigo());
	}
	
	@PostMapping
	public ResponseEntity<?> create(@Valid @RequestBody Partida entity, BindingResult result){
		if(result.hasErrors()) {
			return this.validate(result);
		}
		Partida entityDb = service.save(entity);
		return ResponseEntity.status(HttpStatus.CREATED).body(entityDb);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id){
		service.deleteById(id);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> modifyMapaCat(@Valid @RequestParam String status, BindingResult result,  @PathVariable Long id){
		if(result.hasErrors()) {
			return this.validate(result);
		}
		Optional<Partida> o = service.findById(id);
		if(o.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		Partida partidaDb = o.get();
		partidaDb.setStatus(status);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(partidaDb));
	}
	
	protected ResponseEntity<?> validate(BindingResult result) {
		Map<String, Object> errors = new HashMap<>();
		result.getFieldErrors().forEach(err -> errors.put(err.getField(), err.getDefaultMessage()));
		return ResponseEntity.badRequest().body(errors);
	}
}
