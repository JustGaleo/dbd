package com.justgaleo.dbd.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import org.springframework.web.multipart.MultipartFile;

import com.justgaleo.dbd.models.entity.PerkCat;
import com.justgaleo.dbd.services.IPerkCatService;

import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(value = "/perkCat")
public class PerkCatController {

	@Autowired
	protected IPerkCatService service;

	@GetMapping
	public ResponseEntity<Iterable<PerkCat>> readAll() {
		return ResponseEntity.ok().body(service.findAll());
	}

	@GetMapping("/getRandom/{perks}")
	public ResponseEntity<?> getRandom(@PathVariable int perks) {
		return ResponseEntity.ok().body(service.getRandomly(perks));
	}

	@GetMapping("/{id}")
	public ResponseEntity<PerkCat> readById(@PathVariable Long id) {
		Optional<PerkCat> o = service.findById(id);
		if (o.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(o.get());
	}

	@PostMapping
	public ResponseEntity<?> create(@Valid @RequestBody PerkCat entity, BindingResult result) {
		if (result.hasErrors()) {
			return this.validate(result);
		}
		PerkCat entityDb = service.save(entity);
		return ResponseEntity.status(HttpStatus.CREATED).body(entityDb);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		service.deleteById(id);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping
	public ResponseEntity<?> delete(){
		service.deleteAll();
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/create")
	public ResponseEntity<?> createDB(){
		service.createDB();
		return ResponseEntity.status(HttpStatus.CREATED).body(service.findAll());
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> modifyMapaCat(@Valid @RequestBody PerkCat perkCat, BindingResult result,
			@PathVariable Long id) {
		if (result.hasErrors()) {
			return this.validate(result);
		}
		Optional<PerkCat> o = service.findById(id);
		if (o.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		PerkCat perkCatDb = o.get();
		perkCatDb.setNombre(perkCat.getNombre());

		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(perkCatDb));
	}

	@PostMapping("/create-with-pic")
	public ResponseEntity<?> createWithPicture(@Valid PerkCat perkCat, BindingResult result,
			@RequestParam MultipartFile archive) throws IOException {
		if (!archive.isEmpty()) {
			perkCat.setPicture(archive.getBytes());
		}
		return create(perkCat, result);
	}

	@PutMapping("/modify-with-pic/{id}")
	public ResponseEntity<?> modifyFunkoWithPicture(@Valid PerkCat perkCat, BindingResult result, @PathVariable Long id,
			@RequestParam MultipartFile archive) throws IOException {
		if (result.hasErrors()) {
			return this.validate(result);
		}
		Optional<PerkCat> o = service.findById(id);
		if (o.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		PerkCat perkCatDb = o.get();
		perkCatDb.setNombre(perkCat.getNombre());
		if (!archive.isEmpty()) {
			perkCatDb.setPicture(archive.getBytes());
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(perkCatDb));
	}

	@GetMapping("/uploads/img/{id}")
	public ResponseEntity<?> viewPic(@PathVariable Long id) {
		Optional<PerkCat> o = service.findById(id);
		if (o.isEmpty() || o.get().getPicture() == null) {
			return ResponseEntity.notFound().build();
		}

		Resource img = new ByteArrayResource(o.get().getPicture());

		return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(img);
	}
	
	@GetMapping("/uploads/imgName/{name}")
	public ResponseEntity<?> viewPic(@PathVariable String name){
		PerkCat o = service.findByName(name);
		if(o.getPicture() == null) {
			return ResponseEntity.notFound().build();
		}
		
		Resource img = new ByteArrayResource(o.getPicture());
		
		return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(img);
	}

	protected ResponseEntity<?> validate(BindingResult result) {
		Map<String, Object> errors = new HashMap<>();
		result.getFieldErrors().forEach(err -> errors.put(err.getField(), err.getDefaultMessage()));
		return ResponseEntity.badRequest().body(errors);
	}

}
