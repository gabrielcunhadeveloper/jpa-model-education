package dev.gabrielcunha.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;

import org.hibernate.validator.constraints.br.CPF;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Pessoa {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	@EqualsAndHashCode.Include		
	private Long id;	
	
	@CPF(message = "CPF inválido")
	@Column(unique = true)
	private String cpf;
	
	@Email(message = "E-mail inválido")
	@Column(unique = true)
	private String email;
	
	private String nome;
	
}
