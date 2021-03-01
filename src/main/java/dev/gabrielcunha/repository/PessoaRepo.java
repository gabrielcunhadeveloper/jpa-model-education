package dev.gabrielcunha.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.gabrielcunha.entity.Pessoa;

public interface PessoaRepo extends JpaRepository<Pessoa, Long>{

	Pessoa findByCpf(String cpf);
	
}
