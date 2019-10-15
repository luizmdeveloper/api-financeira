package br.com.inforio.repository.filter;

public class TransacaoFilter {
	
	private Long categoria;
	private Long conta;
	private String emissaoDe;
	private String emissaoAte;
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

	public String getEmissaoDe() {
		return emissaoDe;
	}

	public void setEmissaoDe(String emissaoDe) {
		this.emissaoDe = emissaoDe;
	}

	public String getEmissaoAte() {
		return emissaoAte;
	}

	public void setEmissaoAte(String emissaoAte) {
		this.emissaoAte = emissaoAte;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
}
