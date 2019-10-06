package br.com.inforio.resource;

import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
import br.com.inforio.modelo.Conta;
import br.com.inforio.repository.ContaRepository;
import br.com.inforio.repository.filter.ContaFilter;
import br.com.inforio.service.ContaService;

@RestController
@RequestMapping("contas")
public class ContaResource {
	
	@Autowired
	private ContaRepository contaRepository;
	
	@Autowired
	private ContaService contaService;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@GetMapping
	public Page<Conta> buscar(ContaFilter filter, Pageable page){
		return contaRepository.pesquisar(filter, page);
	}
	
	@GetMapping("{codigo}")
	public ResponseEntity<Conta> buscarPorCodigo(@PathVariable Long codigo){
		Optional<Conta> optionalConta = contaRepository.findById(codigo); 
		return optionalConta.isPresent() ? ResponseEntity.ok(optionalConta.get()) : ResponseEntity.notFound().build();
	}
	
	@PostMapping
	public ResponseEntity<?> salvar(@Valid @RequestBody Conta conta, HttpServletResponse response){
		Conta contaSalva =  contaRepository.save(conta);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, contaSalva.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(contaSalva);
	}
	
	@PutMapping("{codigo}")
	public ResponseEntity<?> alterar(@PathVariable Long codigo, @Valid @RequestBody Conta conta){
		Conta contaSalva = contaService.alterar(codigo, conta);
		return ResponseEntity.ok(contaSalva);
	}
	
	@DeleteMapping("{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long codigo) {
		contaService.excluir(codigo);
	}
}