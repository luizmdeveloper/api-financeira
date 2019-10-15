package br.com.inforio.repository.filter;

import java.time.LocalDate;

public class TransacaoFilter {
	
	private Long categoria;
	private Long conta;
	
	private LocalDate emissaoDe;
	
	private LocalDate emissaoAte;
	
	private String observacao;

	public Long getCategoria() {
		return categoria;
	}

	public void setCategoria(Long categoria) {
		this.categoria = categoria;
	}

	public Long getConta() {
		return conta;
	}

	public void setConta(Long conta) {
		this.conta = conta;
	}

	public LocalDate getEmissaoDe() {
		return emissaoDe;
	}

	public void setEmissaoDe(LocalDate emissaoDe) {
		this.emissaoDe = emissaoDe;
	}

	public LocalDate getEmissaoAte() {
		return emissaoAte;
	}

	public void setEmissaoAte(LocalDate emissaoAte) {
		this.emissaoAte = emissaoAte;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
}
