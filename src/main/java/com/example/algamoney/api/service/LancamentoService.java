package com.example.algamoney.api.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.algamoney.api.model.Lancamento;
import com.example.algamoney.api.model.Pessoa;
import com.example.algamoney.api.repository.LancamentoRepository;
import com.example.algamoney.api.repository.PessoaRepository;
import com.example.algamoney.api.repository.filter.LancamentoFilter;
import com.example.algamoney.api.service.exception.PessoaInexistenteOuInativaException;

@Service
public class LancamentoService {
	
	@Autowired
	private LancamentoRepository lancamentoRepository;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	public Page<Lancamento> pesquisar(LancamentoFilter lancamentoFilter, Pageable pageable){
		return lancamentoRepository.filtrar(lancamentoFilter, pageable);

	}

	public Lancamento buscarPorCodigo(Long codigo) {
		Optional<Lancamento> lancamentoExistente = lancamentoRepository.findById(codigo);
		if(lancamentoExistente.isEmpty()) {
			throw new EmptyResultDataAccessException(1); //Recurso não encontrado
		}
		return lancamentoExistente.get();

	}
	
	public Lancamento incluir(Lancamento lancamento) {
		Pessoa pessoaDoLancamento = pessoaRepository.findById(lancamento.getPessoa().getCodigo()).get();
		
		if(pessoaDoLancamento == null || pessoaDoLancamento.isInativo()) {
			throw new PessoaInexistenteOuInativaException();
		}
		
		return lancamentoRepository.save(lancamento);
	}

	public void excluir(Long codigo) {
		Lancamento lancamentoParaExcluir = buscarPorCodigo(codigo);
		lancamentoRepository.delete(lancamentoParaExcluir);
	}
	
}
