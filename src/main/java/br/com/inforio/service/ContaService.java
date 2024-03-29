package br.com.inforio.service;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.com.inforio.modelo.Conta;
import br.com.inforio.repository.ContaRepository;
import br.com.inforio.service.exception.ContaNaoCadastradaException;
import br.com.inforio.service.exception.ContaNaoPodeExcluiException;

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
		try {
			contaRepository.delete(contaExclui);			
		} catch (DataIntegrityViolationException e) {
			throw new ContaNaoPodeExcluiException();
		}
	}
	
	public Conta buscarContaPorCodigo(Long codigo) {
		Conta conta = contaRepository.findOne(codigo);		
		if (conta == null) {
			throw new ContaNaoCadastradaException();
		}
		
		return conta;
	}
}
