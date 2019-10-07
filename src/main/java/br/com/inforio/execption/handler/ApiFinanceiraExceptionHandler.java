package br.com.inforio.execption.handler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.inforio.service.exception.CategoriaNaoCadastradaException;
import br.com.inforio.service.exception.CategoriaNaoPodeExcluiException;
import br.com.inforio.service.exception.ContaNaoCadastradaException;
import br.com.inforio.service.exception.ContaNaoPodeExcluiException;
import br.com.inforio.service.exception.TransacaoNaoCadastradaException;
import br.com.inforio.service.exception.TransacaoNaoPodeExcluiException;

@ControllerAdvice
public class ApiFinanceiraExceptionHandler extends ResponseEntityExceptionHandler {
	
	@Autowired
	private MessageSource messageSource;
	
	@ExceptionHandler({ CategoriaNaoCadastradaException.class })
	public ResponseEntity<Object> handleCategoriaNaoCadastradaException(CategoriaNaoCadastradaException ex, WebRequest request){
		String mensagemUsuario = "Categoria não cadastrada!";
		String mensagemDesenvolvedor =  ex.toString();
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));
		
		return ResponseEntity.badRequest().body(erros);
	}

	@ExceptionHandler({ ContaNaoCadastradaException.class })
	public ResponseEntity<Object> handleContaNaoCadastradaException(ContaNaoCadastradaException ex, WebRequest request){
		String mensagemUsuario = "Conta não cadastrada!";
		String mensagemDesenvolvedor =  ex.toString();
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));
		
		return ResponseEntity.badRequest().body(erros);
	}
	
	@ExceptionHandler({ TransacaoNaoCadastradaException.class })
	public ResponseEntity<Object> handleTransacaoNaoCadastradaException(TransacaoNaoCadastradaException ex, WebRequest request){
		String mensagemUsuario = "Transação não cadastrada!";
		String mensagemDesenvolvedor =  ex.toString();
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));
		
		return ResponseEntity.badRequest().body(erros);
	}
	
	@ExceptionHandler({ CategoriaNaoPodeExcluiException.class })
	public ResponseEntity<Object> handleCategoriaNaoPodeExcluiException(CategoriaNaoPodeExcluiException ex, WebRequest request){
		String mensagemUsuario = "Categoria não pode ser excluída";
		String mensagemDesenvolvedor =  ex.toString();
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));
		
		return ResponseEntity.badRequest().body(erros);
	}

	@ExceptionHandler({ ContaNaoPodeExcluiException.class })
	public ResponseEntity<Object> handleContaNaoPodeExcluiException(ContaNaoPodeExcluiException ex, WebRequest request){
		String mensagemUsuario = "Conta não pode ser excluída";
		String mensagemDesenvolvedor =  ex.toString();
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));
		
		return ResponseEntity.badRequest().body(erros);
	}
	
	@ExceptionHandler({ TransacaoNaoPodeExcluiException.class })
	public ResponseEntity<Object> handleContaNaoPodeExcluiException(TransacaoNaoPodeExcluiException ex, WebRequest request){
		String mensagemUsuario = "Transação não pode ser excluída";
		String mensagemDesenvolvedor =  ex.toString();
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));
		
		return ResponseEntity.badRequest().body(erros);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<Erro> erros = criarListaErros(ex.getBindingResult());
		return handleExceptionInternal(ex, erros, headers, HttpStatus.BAD_REQUEST, request);
	}
		
	private List<Erro> criarListaErros(BindingResult bindingResult) {
		List<Erro> erros = new ArrayList<>();
		
		for(FieldError fieldError: bindingResult.getFieldErrors()) {
			System.out.println(">>>>> fieldError " + messageSource.getMessage(fieldError, LocaleContextHolder.getLocale()));
			String mensagemErroUsuario = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
			String mensagemErroDesenvolvedor = fieldError.toString();
		
			erros.add(new Erro(mensagemErroUsuario, mensagemErroDesenvolvedor));
		}
				
		return erros;
	}

	public static class Erro{
		
		private String mensagemUsuario;
		private String mensagemDesenvolvedor;
		
		public Erro(String mensagemUsuario,String mensagemDesenvolvedor) {
			this.mensagemUsuario = mensagemUsuario;
			this.mensagemDesenvolvedor = mensagemDesenvolvedor;
		}
		
		public String getMensagemUsuario() {
			return mensagemUsuario;
		}
		
		public String getMensagemDesenvolvedor() {
			return mensagemDesenvolvedor;
		}
	}

}