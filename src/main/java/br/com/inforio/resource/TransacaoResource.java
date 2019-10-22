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
import br.com.inforio.modelo.Transacao;
import br.com.inforio.repository.TransacaoRepository;
import br.com.inforio.repository.filter.TotalTransacaoFilter;
import br.com.inforio.repository.filter.TransacaoFilter;
import br.com.inforio.repository.projecao.ResumoTransacao;
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
	
	@GetMapping
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_TRANSACAO') and #oauth2.hasScope('read')")
	public Page<Transacao> pesquisar(TransacaoFilter filter, Pageable page){
		return transacaoRepository.pesquisar(filter, page);
	}
	
	@GetMapping(params="resumo")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_TRANSACAO') and #oauth2.hasScope('read')")
	public Page<ResumoTransacao> pesquisarResumo(TransacaoFilter filter, Pageable page){
		return transacaoRepository.pesquisarResumido(filter, page);
	}
	
	@GetMapping("{codigo}")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_TRANSACAO') and #oauth2.hasScope('read')")
	public ResponseEntity<Transacao> buscarPorCodigo(@PathVariable Long codigo){
		Transacao transacao = transacaoRepository.findOne(codigo);
		return transacao != null ? ResponseEntity.ok(transacao) : ResponseEntity.notFound().build();
	}
	
	@PostMapping
	@PreAuthorize("hasAuthority('ROLE_SALVAR_TRANSACAO') and #oauth2.hasScope('write')")
	public ResponseEntity<?> salvar(@Valid @RequestBody Transacao transacao, HttpServletResponse response){
		Transacao transacaoSalva =  transacaoService.salvar(transacao);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, transacaoSalva.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(transacaoSalva);		
	}

	@PutMapping("{codigo}")
	@PreAuthorize("hasAuthority('ROLE_SALVAR_TRANSACAO') and #oauth2.hasScope('write')")
	public ResponseEntity<?> alterar(@PathVariable Long codigo, @Valid @RequestBody Transacao transacao){
		Transacao transacaoSalva = transacaoService.alterar(codigo, transacao);
		return ResponseEntity.ok(transacaoSalva);
	}
	
	@PutMapping("/{codigo}/conciliado")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAuthority('ROLE_SALVAR_TRANSACAO') and #oauth2.hasScope('write')")
	public void atuaizarPropriedadeAtivo(@PathVariable Long codigo, @RequestBody Boolean conciliado){		
		transacaoService.atualizarTransacaoConciliado(codigo, conciliado);
	}
	
	@DeleteMapping("{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAuthority('ROLE_EXCLUIR_TRANSACAO') and #oauth2.hasScope('write')")
	public void apagar(@PathVariable Long codigo) {
		transacaoService.apagar(codigo);
	}
	
	@GetMapping("noMesAno/{anoMes}")
	@PreAuthorize("#oauth2.hasScope('read')")
	public ResponseEntity<?> buscarTotaisTransacoes(@PathVariable int anoMes){		
		return ResponseEntity.ok(transacaoService.calcularTotais(anoMes));
	}
	
	@GetMapping("porCategoria/{anoMes}")
	@PreAuthorize("#oauth2.hasScope('read')")
	public ResponseEntity<?> buscarGraficoCategoria(@PathVariable int anoMes){
		return ResponseEntity.ok(transacaoRepository.calcularTransacoesPorCategoria(anoMes));
	}
	
	@GetMapping("periodo")
	@PreAuthorize("#oauth2.hasScope('read')")
	public ResponseEntity<?> calcularTotal(TotalTransacaoFilter filter) {
		return ResponseEntity.ok(transacaoService.calcularTotaisNoPeriodo(filter));
	}	
}
