package dev.gabrielcunha.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Data;

@Data
@Entity
public class Disciplina {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	private Long id;
	
	private String nome;
	
	@ManyToOne
	@JoinColumn(name = "turma_id")
	private Turma turma;
	
	@OneToMany(mappedBy = "disciplina", cascade = CascadeType.ALL)
	private List<MatriculaDisciplinaPessoa> matriculas = new ArrayList<>();
	
}
