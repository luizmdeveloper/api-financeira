package br.com.inforio.resource;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.inforio.modelo.Transacao;
import br.com.inforio.repository.TransacaoRepository;
import br.com.inforio.service.TransacaoService;

@RestController
@RequestMapping("transacoes")
public class TransacaoResource {

	@Autowired
	private TransacaoRepository transacaoRepository;
	
	@Autowired
	private TransacaoService transacaoService;
	
	@GetMapping("{codigo}")
	public ResponseEntity<Transacao> buscarPorCodigo(@PathVariable Long codigo){
		Optional<Transacao> optionalTransacao = transacaoRepository.findById(codigo);
		return optionalTransacao.isPresent() ? ResponseEntity.ok(optionalTransacao.get()) : ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void apagar(@PathVariable Long codigo) {
		transacaoService.apagar(codigo);
	}
	
}
