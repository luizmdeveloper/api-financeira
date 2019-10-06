package br.com.inforio.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.com.inforio.modelo.Transacao;
import br.com.inforio.repository.TransacaoRepository;
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
		Optional<Transacao> optionalTrancasao = transacaoRepository.findById(codigo);

		if (!optionalTrancasao.isPresent()) {
			throw new TransacaoNaoCadastradaException();
		}
		
		return optionalTrancasao.get();
	}	
}