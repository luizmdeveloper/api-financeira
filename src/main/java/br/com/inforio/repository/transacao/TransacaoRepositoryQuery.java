package br.com.inforio.repository.transacao;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.inforio.modelo.Transacao;
import br.com.inforio.modelo.TransacaoPorCategoria;
import br.com.inforio.repository.filter.TransacaoFilter;
import br.com.inforio.repository.projecao.ResumoTransacao;

public interface TransacaoRepositoryQuery {

	public Page<Transacao> pesquisar(TransacaoFilter filter, Pageable page);
	public Page<ResumoTransacao> pesquisarResumido(TransacaoFilter filter, Pageable page);
	public BigDecimal calcularTotalCreditoCarteiraNoAnoMes(int anoMes);
	public BigDecimal calcularTotalDebitoCarteiraNoAnoMes(int anoMes);
	public BigDecimal calcularTotalCreditoBancoNoAnoMes(int anoMes);
	public BigDecimal calcularTotalDebitoBancoNoAnoMes(int anoMes);
	public BigInteger buscarTotalTransacoesNoAnoMes(int anoMes);
	public List<TransacaoPorCategoria> calcularTransacoesPorCategoria(int anoMes);
	 
}
