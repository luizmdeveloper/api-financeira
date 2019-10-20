package br.com.inforio.repository.transacao;

import java.math.BigDecimal;

public class TotalTransacaoNoPeriodo {
	
	private int ano;
	private int mes;
	private String tipoTransacao;
	private BigDecimal valor;
	
	public int getAno() {
		return ano;
	}
	
	public void setAno(int ano) {
		this.ano = ano;
	}
	
	public int getMes() {
		return mes;
	}
	
	public void setMes(int mes) {
		this.mes = mes;
	}
	
	public String getTipoTransacao() {
		return tipoTransacao;
	}
	
	public void setTipoTransacao(String tipoTransacao) {
		this.tipoTransacao = tipoTransacao;
	}
	
	public BigDecimal getValor() {
		return valor;
	}
	
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
}
