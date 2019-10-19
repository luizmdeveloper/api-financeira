package br.com.inforio.modelo;

import java.math.BigDecimal;
import java.math.BigInteger;

public class TransacoesNoMesAno {

	private BigDecimal totalCarteira;
	private BigDecimal totalBanco;
	private BigInteger quantidadeTransacoes;
	
	public BigDecimal getTotalCarteira() {
		return totalCarteira;
	}
	
	public void setTotalCarteira(BigDecimal totalCarteira) {
		this.totalCarteira = totalCarteira;
	}
	
	public BigDecimal getTotalBanco() {
		return totalBanco;
	}
	
	public void setTotalBanco(BigDecimal totalBanco) {
		this.totalBanco = totalBanco;
	}
	
	public BigInteger getQuantidadeTransacoes() {
		return quantidadeTransacoes;
	}
	
	public void setQuantidadeTransacoes(BigInteger quantidadeTransacoes) {
		this.quantidadeTransacoes = quantidadeTransacoes;
	}
}
