package br.com.inforio.repository.transacao;

import java.util.List;

import br.com.inforio.modelo.Transacao;
import br.com.inforio.repository.filter.TransacaoFilter;

public interface TransacaoRepositoryQuery {

	public List<Transacao> pesquisar(TransacaoFilter filter);
	
}
