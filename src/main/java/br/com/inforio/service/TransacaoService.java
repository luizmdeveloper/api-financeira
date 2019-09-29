package br.com.inforio.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.inforio.modelo.Transacao;
import br.com.inforio.repository.TransacaoRepository;
import br.com.inforio.service.exception.TransacaoNaoCadastradaException;

@Service
public class TransacaoService {
	
	@Autowired
	private TransacaoRepository transacaoRepository;

	public void apagar(Long codigo) {
		Transacao transacao = buscarPorCodigo(codigo);
		transacaoRepository.delete(transacao);
	}
	
	private Transacao buscarPorCodigo(Long codigo) {
		Optional<Transacao> optionalTrancasao = transacaoRepository.findById(codigo);
		
		if (!optionalTrancasao.isPresent()) {
			throw new TransacaoNaoCadastradaException();
		}
		
		return optionalTrancasao.get();
	}

}