package br.com.inmetrics.desafio.biblioteca.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.inmetrics.desafio.biblioteca.models.entities.AutorModel;
import br.com.inmetrics.desafio.biblioteca.models.repositories.AutorRepository;

@RestController
@RequestMapping(path = "/api/v1/autor")
public class AutorController {
	
	@Autowired
	private AutorRepository autorRepository;
	
	@GetMapping
	public ResponseEntity<List<AutorModel>> obterAutores() {
		
		List<AutorModel> autorList = autorRepository.findAll();
		
		if(autorList.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(autorList, HttpStatus.OK);
		}
	}
	
	@GetMapping("/busca/id")
	public ResponseEntity<AutorModel> obterAutorId(@RequestParam(name = "id") long id) {
		
		Optional<AutorModel> autor = autorRepository.findById(id);
		
		if (autor.isPresent()) {
			return new ResponseEntity<>(autor.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/busca/nome")
	public ResponseEntity<AutorModel> obterAutorPorNome(@RequestParam(name = "nome") String nome) {
		
		List<AutorModel> autorList = autorRepository.findAll();
		
		for(AutorModel autor: autorList) {
			if(autor.getNome().equals(nome)) {
				return new ResponseEntity<>(autor, HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@PostMapping("/salvar")
	public ResponseEntity<AutorModel> salvarAutor(@RequestBody @Validated AutorModel autor) {
		return new ResponseEntity<>(autorRepository.save(autor), HttpStatus.CREATED);
	}
	
	@DeleteMapping("/delete/id")
	public ResponseEntity<AutorModel> deletarAutorPorId(@RequestParam(name = "id") long id) {
		Optional<AutorModel> desenvolvedorO = autorRepository.findById(id);

		if (desenvolvedorO.isPresent()) {
			autorRepository.delete(desenvolvedorO.get());
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/delete/nome")
	public ResponseEntity<AutorModel> deletarAutorPorNome(@RequestParam(name = "nome") String nome) {
		ResponseEntity<AutorModel> autor = obterAutorPorNome(nome);

		if (autor.getBody().getNome().contains(nome)) {
			autorRepository.delete(autor.getBody());
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping("/alterar")
	public ResponseEntity<AutorModel> alterarAutor(@RequestBody @Validated AutorModel autor) {
		Optional<AutorModel> autorO = autorRepository.findById(autor.getId());

		if (autorO.isPresent()) {
			autorO.get().setNome(autor.getNome());
			return new ResponseEntity<>(autorRepository.save(autorO.get()), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
