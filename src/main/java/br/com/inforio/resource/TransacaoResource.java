package br.com.inforio.resource;

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

import br.com.inforio.evento.RecursoCriadoEvent;
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
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@GetMapping("{codigo}")
	public ResponseEntity<Transacao> buscarPorCodigo(@PathVariable Long codigo){
		Optional<Transacao> optionalTransacao = transacaoRepository.findById(codigo);
		return optionalTransacao.isPresent() ? ResponseEntity.ok(optionalTransacao.get()) : ResponseEntity.notFound().build();
	}
	
	@PostMapping
	public ResponseEntity<?> salvar(@Valid @RequestBody Transacao transacao, HttpServletResponse response){
		Transacao transacaoSalva =  transacaoService.salvar(transacao);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, transacaoSalva.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(transacaoSalva);		
	}

	@PutMapping("{codigo}")
	public ResponseEntity<?> alterar(@PathVariable Long codigo, @Valid @RequestBody Transacao transacao){
		Transacao transacaoSalva = transacaoService.alterar(codigo, transacao);
		return ResponseEntity.ok(transacaoSalva);
	}
	
	@PutMapping("/{codigo}/conciliado")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atuaizarPropriedadeAtivo(@PathVariable Long codigo, @RequestBody Boolean ativo){		
		transacaoService.atualizarTransacaoConciliado(codigo, ativo);
	}
	
	@DeleteMapping("{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void apagar(@PathVariable Long codigo) {
		transacaoService.apagar(codigo);
	}
}
