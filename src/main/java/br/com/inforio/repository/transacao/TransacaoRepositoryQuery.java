package br.com.inforio.repository.transacao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.inforio.modelo.Transacao;
import br.com.inforio.repository.filter.TransacaoFilter;

public interface TransacaoRepositoryQuery {

	public Page<Transacao> pesquisar(TransacaoFilter filter, Pageable page);
	
}
