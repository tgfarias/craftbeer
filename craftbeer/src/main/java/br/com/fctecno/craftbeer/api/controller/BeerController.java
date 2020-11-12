package br.com.fctecno.craftbeer.api.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.com.fctecno.craftbeer.domain.model.Beer;
import br.com.fctecno.craftbeer.domain.repository.BeerRepository;

@RestController
@RequestMapping("/beer")
public class BeerController {
	
	@Autowired
	private BeerRepository beerRepository;
	
	@GetMapping
	public List<Beer> listar() {
		return beerRepository.findAll();
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Beer adicionar(@Valid @RequestBody Beer beer) {
		return beerRepository.save(beer);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Beer> buscar(@PathVariable Long id) {
		Optional<Beer> beer = beerRepository.findById(id);
		
		if (beer.isPresent()) {
			return ResponseEntity.ok(beer.get());
		} 
		
		return ResponseEntity.notFound().build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Beer> atualizar(@PathVariable Long id, @Valid @RequestBody Beer beer) {
		
		if(!beerRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		
		beer.setId(id);
		beer = beerRepository.save(beer);
		
		return ResponseEntity.ok(beer);
		
	}
	
	@PatchMapping("/{id")
	public ResponseEntity<Beer> partial(@PathVariable Long id, @Valid @RequestBody Beer beer) {
		
		if(!beerRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		
		beer.setId(id);
		beer = beerRepository.save(beer);
		
		return ResponseEntity.ok(beer);
		
	}
	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> remover(@PathVariable Long id) {
		
		if(!beerRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}		
		
		beerRepository.deleteById(id);
		
		return ResponseEntity.noContent().build();
		
	}

}