package br.com.inmetrics.desafio.biblioteca.models.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TB_LIVRO")
public class LivroModel implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String nome;
	private AutorModel autor;
	private EditoraModel editora;
	
	public LivroModel() {
	}

	public LivroModel(String nome, AutorModel autor, EditoraModel editora) {
		super();
		this.nome = nome;
		this.autor = autor;
		this.editora = editora;
	}

	public LivroModel(long id, String nome, AutorModel autor, EditoraModel editora) {
		super();
		this.id = id;
		this.nome = nome;
		this.autor = autor;
		this.editora = editora;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public AutorModel getAutor() {
		return autor;
	}

	public void setAutor(AutorModel autor) {
		this.autor = autor;
	}

	public EditoraModel getEditora() {
		return editora;
	}

	public void setEditora(EditoraModel editora) {
		this.editora = editora;
	}
}
