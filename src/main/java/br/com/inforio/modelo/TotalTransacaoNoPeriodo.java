package br.com.inforio.modelo;

import java.math.BigDecimal;

public class TotalTransacaoNoPeriodo implements Comparable<TotalTransacaoNoPeriodo> {
	
	private int anoMes;
	private String tipoTransacao;
	private BigDecimal valor;
		
	public TotalTransacaoNoPeriodo(Double anoMes, String tipoTransacao, BigDecimal valor) {
		super();
		this.anoMes = anoMes.intValue();
		this.tipoTransacao = tipoTransacao;
		this.valor = valor;
	}

	public int getAnoMes() {
		return anoMes;
	}
	
	public void setAnoMes(int anoMes) {
		this.anoMes = anoMes;
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
	
	private String retornarMes(int mes) {
		String[] meses = {"Jan", "Fev", "Mar", "Abr", "Mai", "Jun", "Jul", "Ago", "Set", "Out", "Nov", "Dez"};
		
		return meses[mes-1];
	}
	
	public String getLabel() {
		int ano = Integer.parseInt(String.valueOf(this.anoMes).substring(0,4));
		int mes = Integer.parseInt(String.valueOf(this.anoMes).substring(4,6));
		
		return ano + "/" + retornarMes(mes);
	}

	@Override
	public int compareTo(TotalTransacaoNoPeriodo total) {
		if (this.anoMes > total.getAnoMes()) { 
			return 1; 
		}
		
		if (this.anoMes < total.getAnoMes()) { 
			return -1; 
		}
		
		return 0; 
	}	
}
