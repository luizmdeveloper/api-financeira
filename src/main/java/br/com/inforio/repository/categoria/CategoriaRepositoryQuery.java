package br.com.inforio.repository.categoria;

import java.util.List;

import br.com.inforio.modelo.Categoria;
import br.com.inforio.repository.filter.CategoriaFilter;

public interface CategoriaRepositoryQuery {

	public List<Categoria> pesquisar(CategoriaFilter filter);
	
}
