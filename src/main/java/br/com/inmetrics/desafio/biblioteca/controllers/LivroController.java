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

import br.com.inmetrics.desafio.biblioteca.models.entities.LivroModel;
import br.com.inmetrics.desafio.biblioteca.models.repositories.LivroRepository;

@RestController
@RequestMapping(path = "/api/v1/livro")
public class LivroController {
	
	@Autowired
	private LivroRepository livroRepository;
	
	@GetMapping
	public ResponseEntity<List<LivroModel>> obterLivros() {
		
		List<LivroModel> livroList = livroRepository.findAll();
		
		if(livroList.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(livroList, HttpStatus.OK);
		}
	}
	
	@GetMapping("/busca/id")
	public ResponseEntity<LivroModel> obterLivroId(@RequestParam(name = "id") long id) {
		
		Optional<LivroModel> livro = livroRepository.findById(id);
		
		if (livro.isPresent()) {
			return new ResponseEntity<>(livro.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/busca/nome")
	public ResponseEntity<LivroModel> obterLivroPorNome(@RequestParam(name = "nome") String nome) {
		
		List<LivroModel> livroList = livroRepository.findAll();
		
		for(LivroModel livro: livroList) {
			if(livro.getNome().equals(nome)) {
				return new ResponseEntity<>(livro, HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/busca/autor")
	public ResponseEntity<LivroModel> obterLivroPorAutor(@RequestParam(name = "autor") String autor) {
		
		// criar
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/busca/editora")
	public ResponseEntity<LivroModel> obterLivroPorEditora(@RequestParam(name = "autor") String autor) {
		
		// criar
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@PostMapping("/salvar")
	public ResponseEntity<LivroModel> salvarLivro(@RequestBody @Validated LivroModel livro) {
		return new ResponseEntity<>(livroRepository.save(livro), HttpStatus.CREATED);
	}
	
	@DeleteMapping("/delete/id")
	public ResponseEntity<LivroModel> deletarLivroPorId(@RequestParam(name = "id") long id) {
		Optional<LivroModel> desenvolvedorO = livroRepository.findById(id);

		if (desenvolvedorO.isPresent()) {
			livroRepository.delete(desenvolvedorO.get());
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/delete/nome")
	public ResponseEntity<LivroModel> deletarLivroPorNome(@RequestParam(name = "nome") String nome) {
		ResponseEntity<LivroModel> livro = obterLivroPorNome(nome);

		if (livro.getBody().getNome().contains(nome)) {
			livroRepository.delete(livro.getBody());
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping("/alterar")
	public ResponseEntity<LivroModel> alterarLivro(@RequestBody @Validated LivroModel livro) {
		Optional<LivroModel> livroO = livroRepository.findById(livro.getId());

		if (livroO.isPresent()) {
			livroO.get().setNome(livro.getNome());
			return new ResponseEntity<>(livroRepository.save(livroO.get()), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
