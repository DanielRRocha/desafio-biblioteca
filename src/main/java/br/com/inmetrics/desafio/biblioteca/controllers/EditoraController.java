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

import br.com.inmetrics.desafio.biblioteca.models.entities.EditoraModel;
import br.com.inmetrics.desafio.biblioteca.models.repositories.EditoraRepository;

@RestController
@RequestMapping(path = "/api/v1/editora")
public class EditoraController {
	
	@Autowired
	private EditoraRepository editoraRepository;
	
	@GetMapping
	public ResponseEntity<List<EditoraModel>> obterAutores() {
		
		List<EditoraModel> autorList = editoraRepository.findAll();
		
		if(autorList.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(autorList, HttpStatus.OK);
		}
	}
	
	@GetMapping("/busca/id")
	public ResponseEntity<EditoraModel> obterAutorId(@RequestParam(name = "id") long id) {
		
		Optional<EditoraModel> autor = editoraRepository.findById(id);
		
		if (autor.isPresent()) {
			return new ResponseEntity<>(autor.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/busca/nome")
	public ResponseEntity<EditoraModel> obterAutorPorNome(@RequestParam(name = "nome") String nome) {
		
		List<EditoraModel> autorList = editoraRepository.findAll();
		
		for(EditoraModel autor: autorList) {
			if(autor.getNome().equals(nome)) {
				return new ResponseEntity<>(autor, HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@PostMapping("/salvar")
	public ResponseEntity<EditoraModel> salvarAutor(@RequestBody @Validated EditoraModel autor) {
		return new ResponseEntity<>(editoraRepository.save(autor), HttpStatus.CREATED);
	}
	
	@DeleteMapping("/delete/id")
	public ResponseEntity<EditoraModel> deletarAutorPorId(@RequestParam(name = "id") long id) {
		Optional<EditoraModel> desenvolvedorO = editoraRepository.findById(id);

		if (desenvolvedorO.isPresent()) {
			editoraRepository.delete(desenvolvedorO.get());
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/delete/nome")
	public ResponseEntity<EditoraModel> deletarAutorPorNome(@RequestParam(name = "nome") String nome) {
		ResponseEntity<EditoraModel> autor = obterAutorPorNome(nome);

		if (autor.getBody().getNome().contains(nome)) {
			editoraRepository.delete(autor.getBody());
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping("/alterar")
	public ResponseEntity<EditoraModel> alterarAutor(@RequestBody @Validated EditoraModel autor) {
		Optional<EditoraModel> autorO = editoraRepository.findById(autor.getId());

		if (autorO.isPresent()) {
			autorO.get().setNome(autor.getNome());
			return new ResponseEntity<>(editoraRepository.save(autorO.get()), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
