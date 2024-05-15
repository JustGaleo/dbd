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
import org.springframework.web.bind.annotation.RestController;

import com.justgaleo.dbd.models.entity.MapaCache;
import com.justgaleo.dbd.services.IMapaCacheService;

import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(value = "/mapCache")
public class MapaCacheController {
	
	@Autowired
	protected IMapaCacheService service;
	
	@GetMapping("/transferir")
	public ResponseEntity<?> transfer(){
		service.transfer();
		return ResponseEntity.ok(Map.of("message", "Transferencia realizada"));
	}
	
	@GetMapping("/getRandom")
	public ResponseEntity<?> getRandom(){
		if(service.count() != 0) {
			Optional<MapaCache> o = Optional.ofNullable(service.getRandomly());
			if(o.isEmpty()) {
				return ResponseEntity.ok(Map.of("nombre", "NULO"));
			}
			return ResponseEntity.ok().body(o.get());
		}else {
			return ResponseEntity.ok(Map.of("nombre", "NULO"));
		}
	}
	
	@GetMapping("/count")
	public ResponseEntity<?> count(){
		return ResponseEntity.ok(Map.of("length", service.count()));
	}
	
	@GetMapping
	public ResponseEntity<Iterable<MapaCache>> readAll(){
		return ResponseEntity.ok().body(service.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<MapaCache> readById(@PathVariable Long id){
		Optional<MapaCache> o = service.findById(id);
		if(o.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(o.get());
	}
	
	@PostMapping
	public ResponseEntity<?> create(@Valid @RequestBody MapaCache entity, BindingResult result){
		if(result.hasErrors()) {
			return this.validate(result);
		}
		MapaCache entityDb = service.save(entity);
		return ResponseEntity.status(HttpStatus.CREATED).body(entityDb);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id){
		service.deleteById(id);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> modifyMapaCache(@Valid @RequestBody MapaCache mapaCache, BindingResult result,  @PathVariable Long id){
		if(result.hasErrors()) {
			return this.validate(result);
		}
		Optional<MapaCache> o = service.findById(id);
		if(o.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		MapaCache mapaCacheDb = o.get();
		mapaCacheDb.setNombre(mapaCache.getNombre());
		
		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(mapaCacheDb));
	}
	
	protected ResponseEntity<?> validate(BindingResult result) {
		Map<String, Object> errors = new HashMap<>();
		result.getFieldErrors().forEach(err -> errors.put(err.getField(), err.getDefaultMessage()));
		return ResponseEntity.badRequest().body(errors);
	}

}
