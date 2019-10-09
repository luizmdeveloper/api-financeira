package br.com.inforio.resource;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_CONTA') and #oauth2.hasScope('read')")
	public Page<Conta> buscar(ContaFilter filter, Pageable page){
		return contaRepository.pesquisar(filter, page);
	}
	
	@GetMapping("{codigo}")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_CONTA') and #oauth2.hasScope('read')")
	public ResponseEntity<Conta> buscarPorCodigo(@PathVariable Long codigo){
		Conta conta = contaRepository.findOne(codigo); 
		return conta != null ? ResponseEntity.ok(conta) : ResponseEntity.notFound().build();
	}
	
	@PostMapping
	@PreAuthorize("hasAuthority('ROLE_SALVAR_CONTA') and #oauth2.hasScope('write')")
	public ResponseEntity<?> salvar(@Valid @RequestBody Conta conta, HttpServletResponse response){
		Conta contaSalva =  contaRepository.save(conta);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, contaSalva.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(contaSalva);
	}
	
	@PutMapping("{codigo}")
	@PreAuthorize("hasAuthority('ROLE_SALVAR_CONTA') and #oauth2.hasScope('write')")
	public ResponseEntity<?> alterar(@PathVariable Long codigo, @Valid @RequestBody Conta conta){
		Conta contaSalva = contaService.alterar(codigo, conta);
		return ResponseEntity.ok(contaSalva);
	}
	
	@DeleteMapping("{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAuthority('ROLE_EXCLUIR_CATEGORIA') and #oauth2.hasScope('write')")
	public void delete(@PathVariable Long codigo) {
		contaService.excluir(codigo);
	}
}