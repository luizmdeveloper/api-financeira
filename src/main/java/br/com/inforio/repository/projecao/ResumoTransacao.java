package br.com.inforio.repository.projecao;

import java.math.BigDecimal;
import java.time.LocalDate;

import br.com.inforio.modelo.TipoTransacao;

public class ResumoTransacao {

	private Long codigo;
	private String categoria;
	private String conta;
	private LocalDate emissao;
	private BigDecimal valor;
	private TipoTransacao tipoTransacao;
	private Boolean conciliado;
		
	public ResumoTransacao(Long codigo, String categoria, String conta, LocalDate emissao, BigDecimal valor,
			TipoTransacao tipoTransacao, Boolean conciliado) {
		super();
		this.codigo = codigo;
		this.categoria = categoria;
		this.conta = conta;
		this.emissao = emissao;
		this.valor = valor;
		this.tipoTransacao = tipoTransacao;
		this.conciliado = conciliado;
	}

	public Long getCodigo() {
		return codigo;
	}
	
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	
	public String getCategoria() {
		return categoria;
	}
	
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	
	public String getConta() {
		return conta;
	}
	
	public void setConta(String conta) {
		this.conta = conta;
	}
	
	public LocalDate getEmissao() {
		return emissao;
	}
	
	public void setEmissao(LocalDate emissao) {
		this.emissao = emissao;
	}
	
	public BigDecimal getValor() {
		return valor;
	}
	
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	
	public TipoTransacao getTipoTransacao() {
		return tipoTransacao;
	}
	
	public void setTipoTransacao(TipoTransacao tipoTransacao) {
		this.tipoTransacao = tipoTransacao;
	}
	
	public Boolean getConciliado() {
		return conciliado;
	}
	
	public void setConciliado(Boolean conciliado) {
		this.conciliado = conciliado;
	}
}
