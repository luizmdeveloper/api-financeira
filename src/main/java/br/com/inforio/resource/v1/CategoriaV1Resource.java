package br.com.inforio.resource.v1;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.inforio.modelo.Categoria;
import br.com.inforio.repository.CategoriaRepository;

@RestController
@RequestMapping("v1/categorias")
public class CategoriaV1Resource {

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@GetMapping
	public ResponseEntity<List<Categoria>> buscarTodos(){
		List<Categoria> categorias = categoriaRepository.findAll();
		return ResponseEntity.ok(categorias);
	}
}
