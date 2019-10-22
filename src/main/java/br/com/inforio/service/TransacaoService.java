package br.com.inforio.service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.com.inforio.modelo.TotalTransacaoNoPeriodo;
import br.com.inforio.modelo.Transacao;
import br.com.inforio.modelo.TransacoesNoMesAno;
import br.com.inforio.repository.TransacaoRepository;
import br.com.inforio.repository.filter.TotalTransacaoFilter;
import br.com.inforio.service.exception.TransacaoNaoCadastradaException;
import br.com.inforio.service.exception.TransacaoNaoPodeExcluiException;

@Service
public class TransacaoService {
	
	@Autowired
	private TransacaoRepository transacaoRepository;
		
	@Autowired
	private CategoriaService categoriaService;
	
	@Autowired
	private ContaService contaService;
	
	public Transacao salvar(Transacao transacao) {

		buscarContaPorCodigo(transacao);
		buscaCategoriaPorCodigo(transacao);
		
		return transacaoRepository.save(transacao);
	}
		
	public Transacao alterar(Long codigo, Transacao transacao) {
		Transacao transacaoSalva = buscarTransacaoPorCodigo(codigo);		
		BeanUtils.copyProperties(transacao, transacaoSalva, "codigo");
		return transacaoRepository.save(transacaoSalva);
	}


	public void apagar(Long codigo) {
		Transacao transacao = buscarTransacaoPorCodigo(codigo);
		try {
			transacaoRepository.delete(transacao);	
		} catch (DataIntegrityViolationException e) {
			throw new TransacaoNaoPodeExcluiException();
		}
	}

	public void atualizarTransacaoConciliado(Long codigo, Boolean conciliado) {
		Transacao transacaoSalva = buscarTransacaoPorCodigo(codigo);
		transacaoSalva.setConciliado(conciliado);
		transacaoRepository.save(transacaoSalva);
	}
	
	private void buscaCategoriaPorCodigo(Transacao transacao) {
		if (transacao.getCategoria() != null) {
			categoriaService.buscarCategoriaPorCodigo(transacao.getCategoria().getCodigo());
		}
	}

	private void buscarContaPorCodigo(Transacao transacao) {
		if (transacao.getConta() != null) {
			contaService.buscarContaPorCodigo(transacao.getConta().getCodigo());
		}
	}
		
	private Transacao buscarTransacaoPorCodigo(Long codigo) {
		Transacao trancasao = transacaoRepository.findOne(codigo);
		if (trancasao == null) {
			throw new TransacaoNaoCadastradaException();
		}
		return trancasao;
	}

	public TransacoesNoMesAno calcularTotais(int anoMes) {
		TransacoesNoMesAno totais = new TransacoesNoMesAno();
		totais.setQuantidadeTransacoes(transacaoRepository.buscarTotalTransacoesNoAnoMes(anoMes));
		totais.setTotalCarteira(transacaoRepository.calcularTotalCreditoCarteiraNoAnoMes(anoMes).subtract(transacaoRepository.calcularTotalDebitoCarteiraNoAnoMes(anoMes)));
		totais.setTotalBanco(transacaoRepository.calcularTotalCreditoBancoNoAnoMes(anoMes).subtract(transacaoRepository.calcularTotalDebitoBancoNoAnoMes(anoMes)));		
		return totais;
	}
	
	public List<TotalTransacaoNoPeriodo> calcularTotaisNoPeriodo(TotalTransacaoFilter filter){
		List<TotalTransacaoNoPeriodo> totaisTransacoes = transacaoRepository.calcularTotalTransacaoNoIntervalo(filter);

		int anoMesAtual = filter.getAnoMesInicial();
		int ano = Integer.parseInt(String.valueOf(anoMesAtual).substring(0,4));
		int mes = Integer.parseInt(String.valueOf(anoMesAtual).substring(4,6));
		while (anoMesAtual <= filter.getAnoMesFinal()) {
			final int anoMesCompara = anoMesAtual;
			boolean anoMesPresente = totaisTransacoes.stream().filter(t -> t.getAnoMes() == anoMesCompara).findAny().isPresent();
			if (!anoMesPresente) {
				totaisTransacoes.add(new TotalTransacaoNoPeriodo((double) anoMesAtual, "", BigDecimal.ZERO));
			}			
			
			ano = Integer.parseInt(String.valueOf(anoMesAtual).substring(0,4));
			mes = Integer.parseInt(String.valueOf(anoMesAtual).substring(4,6)) + 1;			
			anoMesAtual = (ano * 100) + mes;
		} 
		
		Collections.sort(totaisTransacoes);
		
		return totaisTransacoes;		
	}
}