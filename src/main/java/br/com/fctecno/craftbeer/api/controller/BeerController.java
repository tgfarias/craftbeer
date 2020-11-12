package br.com.fctecno.craftbeer.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.com.fctecno.craftbeer.domain.exception.NaoEncontradaException;
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
		Beer beer = beerRepository.findById(id)
				.orElseThrow(() -> new NaoEncontradaException("Cerveja não encontrada."));
		
		return ResponseEntity.ok(beer);
		 
	}
	
	@RequestMapping(
		value = "/{id}", 
		produces = "application/json",
		method = {RequestMethod.PUT, RequestMethod.PATCH}
	)
	public ResponseEntity<Beer> atualizar(@PathVariable Long id, @Valid @RequestBody Beer beer) {
		
		
		beerRepository.findById(id).orElseThrow(() -> new NaoEncontradaException("Cerveja não encontrada."));
		
//		if(!beerRepository.existsById(id)) {
//			new NaoEncontradaException("Cerveja não encontrada.");
//			return ResponseEntity.notFound().build();
//		}
		
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
