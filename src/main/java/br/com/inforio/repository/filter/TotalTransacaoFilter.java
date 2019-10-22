package br.com.inforio.repository.filter;

public class TotalTransacaoFilter {
	
	private int anoMesInicial;
	private int anoMesFinal;
	
	public int getAnoMesInicial() {
		return anoMesInicial;
	}
	
	public void setAnoMesInicial(int anoMesInicial) {
		this.anoMesInicial = anoMesInicial;
	}
	
	public int getAnoMesFinal() {
		return anoMesFinal;
	}
	
	public void setAnoMesFinal(int anoMesFinal) {
		this.anoMesFinal = anoMesFinal;
	}
}
