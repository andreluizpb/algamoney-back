package com.example.algamoney.api.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.example.algamoney.api.model.Pessoa;
import com.example.algamoney.api.repository.PessoaRepository;

//para permitir a injeção pelo Spring
@Service
public class PessoaService {
	
	@Autowired
	private PessoaRepository pessoaRepository;

	public Pessoa atualizar(Long codigo, Pessoa pessoa) {
		
		return pessoaRepository.save(buscarPessoaPeloCodigo(codigo));
		
	}

	public void atualizarPropriedadeAtivo(Long codigo, Boolean ativo) {

		Pessoa pessoaExistente = buscarPessoaPeloCodigo(codigo);
		pessoaExistente.setAtivo(ativo);
		pessoaRepository.save(pessoaExistente);
		
	}
	
	private Pessoa buscarPessoaPeloCodigo(Long codigo) {
		Optional<Pessoa> pessoaExistente = pessoaRepository.findById(codigo);
		if(pessoaExistente.isEmpty()) {
			throw new EmptyResultDataAccessException(1);
		}
		return pessoaExistente.get();
	}

}
