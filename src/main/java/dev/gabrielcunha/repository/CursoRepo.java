package dev.gabrielcunha.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.gabrielcunha.entity.Curso;

public interface CursoRepo extends JpaRepository<Curso, Long>{

	Curso findByNome(String nome);
	
}
