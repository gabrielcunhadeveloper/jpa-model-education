package dev.gabrielcunha.resource;

import java.net.URI;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import dev.gabrielcunha.entity.Curso;
import dev.gabrielcunha.repository.CursoRepo;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/cursos")
public class CursoResource {

	@Autowired
	private CursoRepo cursoRepo;
	
	@ApiOperation(value="Cria ou atualiza um recurso.")	
	@ApiResponses(value = { 
	        @ApiResponse(code = 201, message = "Recurso criado."), 
	        @ApiResponse(code = 400, message = "Bad Request - Recurso Inválido.")})		
	@PostMapping
	public ResponseEntity<Curso> criar(@RequestBody Curso curso) {
		
		Curso cursoCriado = cursoRepo.save(curso);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
											 .path("/{id}")
											 .buildAndExpand(curso.getId()).toUri();
		
		return ResponseEntity.created(uri).body(cursoCriado);
	}	

    @ApiParam(name = "id",
    		  value = "Identificador do recurso a ser encontrado. Não pode estar vazio.",
			  example = "72",
			  required = true)	
	@ApiResponses(value = {
	        @ApiResponse(code = 200, message = "Sucesso.", response = Curso.class),
	        @ApiResponse(code = 404, message = "Recurso não encontrado.") 
	})	
	@GetMapping("/{id}")
	public ResponseEntity<Curso> buscar(@PathVariable Long id) {
		
    	Optional<Curso> curso = cursoRepo.findById(id);
		
		if (curso.isPresent()) {
			return ResponseEntity.ok(curso.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}	
    
    @DeleteMapping("/{id}")
    public void deletar(@PathVariable("id") Long id) {
    	cursoRepo.deleteById(id);
    }    
		
	
}
