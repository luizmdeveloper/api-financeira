package br.com.inforio.service;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.inforio.modelo.Conta;
import br.com.inforio.repository.ContaRepository;
import br.com.inforio.service.exception.ContaNaoCadastradaException;

@Service
public class ContaService {
	
	@Autowired
	private ContaRepository contaRepository;

	public Conta alterar(Long codigo, @Valid Conta conta) {
		Conta contaSalva = buscarContaPorCodigo(codigo);
		BeanUtils.copyProperties(conta, contaSalva, "codigo");
		return contaRepository.save(contaSalva);
	}
	
	public void excluir(Long codigo) {
		Conta contaExclui = buscarContaPorCodigo(codigo);
		contaRepository.delete(contaExclui);		
	}
	
	private Conta buscarContaPorCodigo(Long codigo) {
		Optional<Conta> optionalConta = contaRepository.findById(codigo);
		
		if (!optionalConta.isPresent()) {
			throw new ContaNaoCadastradaException();
		}
		
		return optionalConta.get();
	}
}
