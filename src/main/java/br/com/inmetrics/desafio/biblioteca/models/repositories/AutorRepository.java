package br.com.inmetrics.desafio.biblioteca.models.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.inmetrics.desafio.biblioteca.models.entities.AutorModel;

public interface AutorRepository extends JpaRepository<AutorModel, Long>{

}
