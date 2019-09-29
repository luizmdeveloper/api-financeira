package br.com.inforio.execption.handler;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.inforio.service.exception.CategoriaNaoCadastradaException;

@ControllerAdvice
public class ApiFinanceiraExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler({CategoriaNaoCadastradaException.class})
	public ResponseEntity<Object> handlePessoaInexistenOuInativaException(CategoriaNaoCadastradaException ex, WebRequest request){
		String mensagemUsuario = "Categoria não cadastrada!";
		String mensagemDesenvolvedor =  ex.toString();
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));
		
		return ResponseEntity.badRequest().body(erros);
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
