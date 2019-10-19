package br.com.inforio.modelo;

import java.math.BigDecimal;

public class TransacaoPorCategoria {
	
	private String categoria;
	private BigDecimal percentual;
	
	public String getCategoria() {
		return categoria;
	}
	
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	
	public BigDecimal getPercentual() {
		return percentual;
	}
	
	public void setPercentual(BigDecimal percentual) {
		this.percentual = percentual;
	}
}
