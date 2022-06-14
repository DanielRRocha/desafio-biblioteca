package br.com.inmetrics.desafio.biblioteca.models.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.inmetrics.desafio.biblioteca.models.entities.LivroModel;

public interface LivroRepository extends JpaRepository<LivroModel, Long>{

}
