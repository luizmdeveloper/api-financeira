package br.com.inforio.repository.conta;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.inforio.modelo.Conta;
import br.com.inforio.repository.filter.ContaFilter;

public interface ContaRepositoryQuery {
	
	public Page<Conta> pesquisar(ContaFilter filter, Pageable page);

}
