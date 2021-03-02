package dev.gabrielcunha.resource;

import java.net.URI;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import dev.gabrielcunha.entity.Pessoa;
import dev.gabrielcunha.repository.PessoaRepo;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/pessoas")
public class PessoaResource {
	
	@Autowired
	private PessoaRepo pessoaRepo;
	
	@ApiOperation(value="Cria ou atualiza um recurso.")	
	@ApiResponses(value = { 
	        @ApiResponse(code = 201, message = "Recurso criado."), 
	        @ApiResponse(code = 409, message = "Recurso já existe.")})		
	@PostMapping
	public ResponseEntity<Pessoa> criar(@RequestBody Pessoa pessoa) {
		
		Pessoa pessoaDoBancoDados = pessoaRepo.findByCpf(pessoa.getCpf());
		
		if (!ObjectUtils.isEmpty(pessoaDoBancoDados)) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
		
		Pessoa pessoaCriada = pessoaRepo.save(pessoa);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
											 .path("/{id}")
											 .buildAndExpand(pessoa.getId()).toUri();
		
		return ResponseEntity.created(uri).body(pessoaCriada);
	}	
	
	
	@ApiOperation(value="Endpoint responsável por atualizar um recurso pelo id, enviando no "
			+ "body da requisição o json do recurso com os dados a serem atualizados.")	
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Sucesso."),
            @ApiResponse(code = 400, message = "Requisição do cliente enviada com erro."),
            @ApiResponse(code = 404, message = "Recurso não encontrado.")
    	})	
	@PutMapping("/{id}")
	public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody Pessoa pessoa) {
		
		Optional<Pessoa> pessoaOptional = pessoaRepo.findById(id);
		
		if (!pessoaOptional.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		Pessoa pessoaBanco = pessoaOptional.get();
		
		BeanUtils.copyProperties(pessoa, pessoaBanco, "id");
		
		pessoaRepo.save(pessoaBanco);
		
		return ResponseEntity.ok(pessoaBanco);
	}
		

    @ApiParam(name = "id",  value = "Identificador do recurso a ser encontrado. Não pode ser vazio.",
			  example = "72", required = true)	
	@ApiResponses(value = {
	        @ApiResponse(code = 200, message = "Sucesso.", response = Pessoa.class),
	        @ApiResponse(code = 404, message = "Recurso não encontrado.") 
	})
	@GetMapping("/{id}")
	public ResponseEntity<Pessoa> buscar(@PathVariable Long id) {
		
    	Optional<Pessoa> pessoa = pessoaRepo.findById(id);
		
		if (pessoa.isPresent()) {
			return ResponseEntity.ok(pessoa.get());
		} 
		
		return ResponseEntity.notFound().build();
	}	
    
    @ApiParam(name = "id",
			  value = "Identificador do recurso a ser excluido. Não pode ser vazio.",
			  example = "12",
			  required = true)	
	@ApiResponses(value = {
	        @ApiResponse(code = 200, message = "Sucesso.", response = Pessoa.class),
	        @ApiResponse(code = 404, message = "Recurso não encontrado.") 
	})    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluir(@PathVariable("id") Long id) {
    	
    	Optional<Pessoa> pessoaBanco = pessoaRepo.findById(id);
    	
    	if (!pessoaBanco.isPresent()) {
    		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    	}
    	
    	pessoaRepo.deleteById(id);
    	
    	return ResponseEntity.ok().build();
    }    
	
}
