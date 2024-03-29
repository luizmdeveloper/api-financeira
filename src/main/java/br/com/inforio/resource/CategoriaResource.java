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
import br.com.inforio.modelo.Categoria;
import br.com.inforio.repository.CategoriaRepository;
import br.com.inforio.repository.filter.CategoriaFilter;
import br.com.inforio.service.CategoriaService;

@RestController
@RequestMapping("categorias")
public class CategoriaResource {
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private CategoriaService categoriaService;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@GetMapping
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_CATEGORIA') and #oauth2.hasScope('read')")
	public Page<Categoria> buscarTodos(CategoriaFilter filter, Pageable page){
		return categoriaRepository.pesquisar(filter, page);
	}

	@GetMapping("{codigo}")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_CATEGORIA') and #oauth2.hasScope('read')")
	public ResponseEntity<Categoria> buscarPorCodigo(@PathVariable Long codigo){
		Categoria categoria = categoriaRepository.findOne(codigo);
		return categoria != null ? ResponseEntity.ok(categoria) : ResponseEntity.notFound().build();
	}
	
	@PostMapping
	@PreAuthorize("hasAuthority('ROLE_SALVAR_CATEGORIA') and #oauth2.hasScope('write')")
	public ResponseEntity<?> salvar(@Valid @RequestBody Categoria categoria, HttpServletResponse response){
		Categoria categoriaSalva =  categoriaRepository.save(categoria);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, categoria.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(categoriaSalva);
	}
	
	@PutMapping("{codigo}")
	@PreAuthorize("hasAuthority('ROLE_SALVAR_CATEGORIA') and #oauth2.hasScope('write')")
	public ResponseEntity<?> alterar(@PathVariable Long codigo, @Valid @RequestBody Categoria categoria){
		Categoria categoriaSalva = categoriaService.alterar(codigo, categoria);
		return ResponseEntity.ok(categoriaSalva);
	}
	
	@DeleteMapping("{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAuthority('ROLE_EXCLUIR_CATEGORIA') and #oauth2.hasScope('write')")
	public void excluir(@PathVariable Long codigo){
		categoriaService.apagar(codigo);
	}

}
