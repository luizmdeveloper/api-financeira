package br.com.inforio.repository.categoria;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.inforio.modelo.Categoria;
import br.com.inforio.repository.filter.CategoriaFilter;

public interface CategoriaRepositoryQuery {

	public Page<Categoria> pesquisar(CategoriaFilter filter, Pageable page);
	
}
