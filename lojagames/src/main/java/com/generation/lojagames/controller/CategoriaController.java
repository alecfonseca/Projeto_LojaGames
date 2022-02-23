package com.generation.lojagames.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.generation.lojagames.model.Categoria;
import com.generation.lojagames.repository.CategoriaRepository;

@RestController
@RequestMapping("/categoria")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CategoriaController {
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	
	@GetMapping
	public ResponseEntity<List<Categoria>> getAll() {
		return ResponseEntity.ok(categoriaRepository.findAll());
	}
	
	@PostMapping
	public ResponseEntity<Categoria>postTema(@Valid @RequestBody Categoria categoria){
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(categoriaRepository.save(categoria));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Categoria> getById(@PathVariable Long id){
		return categoriaRepository.findById(id)
				.map(resposta -> ResponseEntity.ok(resposta))
				.orElse(ResponseEntity.notFound().build());
	}
	
	 @GetMapping("/descricao/{descricao}") 
	    public ResponseEntity<List<Categoria>> getByDescricao(@PathVariable String genero) {                    
	        return ResponseEntity.ok(categoriaRepository.findAllByGeneroContainingIgnoreCase(genero));
	    } 
	 
	 @PutMapping
	    public ResponseEntity<Categoria> putTema (@Valid @RequestBody Categoria categoria) {
	        return categoriaRepository.findById(categoria.getId())
	                .map(resposta -> ResponseEntity.status(HttpStatus.OK).body(categoriaRepository.save(categoria)))
	                .orElse(ResponseEntity.notFound().build());
	 }
	 
	 @DeleteMapping("/{id}")
	    	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	    	public void deleteTema(@PathVariable Long id) { 
	    		Optional<Categoria> tema = categoriaRepository.findById(id);
	    		if (tema.isEmpty())
	    				throw new ResponseStatusException(HttpStatus.NOT_FOUND);
	    		
	    		categoriaRepository.deleteById(id);
	 }
}
