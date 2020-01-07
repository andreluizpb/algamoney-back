package com.example.algamoney.api.exceptionhandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.flywaydb.core.internal.util.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/*
 * Excessões gerais do sistema
 */
@ControllerAdvice
public class AlgamoneyExceptionHandler extends ResponseEntityExceptionHandler{

	/*
	 * associa o conteúdo do arquivo messages.proterties ao objeto
	 */
	@Autowired
	private MessageSource messageSource;
	
	/*
	 * Capturam as exceptions originais (passadas como parâmetros para o método) e customizam o response
	 */
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
		HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		String mensagemUsuario = messageSource
			.getMessage("mensagem.invalida", null, LocaleContextHolder.getLocale());
		String mensagemDev = ex.getCause() == null ? ex.toString() : ex.getCause().toString();
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDev));
		
		return handleExceptionInternal(ex, erros, headers, HttpStatus.BAD_REQUEST, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
		HttpHeaders headers, HttpStatus status, WebRequest request) {

		List<Erro> erros = criaListaErros(ex.getBindingResult());
		return handleExceptionInternal(ex, erros, headers, HttpStatus.BAD_REQUEST, request);
	}
	
	/*
	 * Handler criado manualmente, para tratar recurso não encontrado
	 */
	@ExceptionHandler({EmptyResultDataAccessException.class})
	public ResponseEntity<Object> handlerEmptyResultDataAccessException(EmptyResultDataAccessException ex, WebRequest request) {
		String mensagemUsuario = messageSource
			.getMessage("recurso.nao-encontrado", null, LocaleContextHolder.getLocale());
		String mensagemDev = ex.toString(); //Essa Exception não tem o método getCause, pegando texto direto da Exception
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDev));
		
		/*
		 * Como o método não foi herdado, precisa instanciar um novo elemento HttpHeaders
		 */
		return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}
	
	/*
	 * Tratar erros de integridade do banco de dados, chaves de relacionamento
	 */
	@ExceptionHandler({DataIntegrityViolationException.class})
	public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex, WebRequest request){
		String mensagemUsuario = messageSource
			.getMessage("operacao.nao-permitida", null, LocaleContextHolder.getLocale());
		String mensagemDev = ExceptionUtils.getRootCause(ex).getMessage();
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDev));
		return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}
	
	private List<Erro> criaListaErros(BindingResult bindingResult){
		List<Erro> erros = new ArrayList<>();
		
		for(FieldError fieldError : bindingResult.getFieldErrors()) {
			String mensagemUsuario = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
			String mensagemDev = fieldError.toString();
			erros.add(new Erro(mensagemUsuario, mensagemDev));
		}
		
		return erros;
	}
	
	public static class Erro {
		private String mensagemUsuario;
		private String mensagemDev;
		public Erro(String mensagemUsuario, String mensagemDev) {
			super();
			this.mensagemUsuario = mensagemUsuario;
			this.mensagemDev = mensagemDev;
		}
		public String getMensagemUsuario() {
			return mensagemUsuario;
		}
		public String getMensagemDev() {
			return mensagemDev;
		}
	}
	
}
