package com.example.algamoney.api.resource;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.algamoney.api.event.RecursoCriadoEvent;
import com.example.algamoney.api.model.Pessoa;
import com.example.algamoney.api.repository.PessoaRepository;
import com.example.algamoney.api.service.PessoaService;

@RestController
@RequestMapping("/pessoas")
public class PessoaResource {
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	//publica os handlers de eventos
	@Autowired
	private ApplicationEventPublisher publisher;
	
	//componente de regras de negócios de pessoas
	@Autowired
	private PessoaService pessoaService;
	
	@GetMapping
	public List<Pessoa> listar(){
		return pessoaRepository.findAll();
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<Pessoa> buscarPorCodigo(@PathVariable Long codigo){
		Optional<Pessoa> pessoaExistente = pessoaRepository.findById(codigo);
		return pessoaExistente.isPresent() ? ResponseEntity.ok(pessoaExistente.get()) : ResponseEntity.notFound().build();
	}

	@PostMapping
	public ResponseEntity<Pessoa> criar(@Valid @RequestBody Pessoa pessoa, HttpServletResponse response){

		Optional<Pessoa> pessoaExistente = pessoaRepository.findByNome(pessoa.getNome());
		
		if(pessoaExistente.isPresent()) {
			
			//publica o evento de recurso cricado, para ser capturado pelo Listener
			publisher.publishEvent(new RecursoCriadoEvent(this, response, pessoaExistente.get().getCodigo()));

			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
				"Pessoa " + pessoaExistente.get().getNome() + " já existente no cadastro");
			
		} else {
			
			Pessoa pessoaCriada = pessoaRepository.save(pessoa);
			publisher.publishEvent(new RecursoCriadoEvent(this, response, pessoaCriada.getCodigo()));
			return ResponseEntity.status(HttpStatus.CREATED).body(pessoaCriada);
			
		}
				
	}
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long codigo) {
		pessoaRepository.deleteById(codigo);
	}
	
	@PutMapping("/{codigo}")
	public ResponseEntity<Pessoa> atualizar(@PathVariable Long codigo, @Valid @RequestBody Pessoa pessoa){
		Pessoa pessoaAtualizada = pessoaService.atualizar(codigo, pessoa);
		return ResponseEntity.ok(pessoaAtualizada);
	}
	
	@PutMapping("/{codigo}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizarPropriedadeAtivo(@PathVariable Long codigo, @RequestBody Boolean ativo) {
		pessoaService.atualizarPropriedadeAtivo(codigo, ativo);
	}
}
