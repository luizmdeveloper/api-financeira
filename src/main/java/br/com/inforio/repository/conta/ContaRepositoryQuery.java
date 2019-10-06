package br.com.inforio.repository.conta;

import java.util.List;

import br.com.inforio.modelo.Conta;
import br.com.inforio.repository.filter.ContaFilter;

public interface ContaRepositoryQuery {
	
	public List<Conta> pesquisar(ContaFilter filter);

}
