package br.com.inforio.modelo;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name ="transacoes")
public class Transacao {
	
	@Id
	@SequenceGenerator(name="sequence_transacoes",
    				   sequenceName="sequence_transacoes",
    				   allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="sequence_transacoes")		
	private Long codigo;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "codigo_conta")
	private Conta conta;
	
	@ManyToOne
	@JoinColumn(name = "codigo_conta_transferencia")
	private Conta transferencia;
	
	@ManyToOne
	@JoinColumn(name = "codigo_categoria")
	private Categoria categoria;
	
	@NotNull
	@Column(name="data_emissao")
	private LocalDate data;

	@NotNull
	private BigDecimal valor;
	
	private String observacao;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private TipoTransacao tipo;
	
	@NotNull
	private Boolean conciliado;
	
	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}

	public Conta getTransferencia() {
		return transferencia;
	}

	public void setTransferencia(Conta transferencia) {
		this.transferencia = transferencia;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public TipoTransacao getTipo() {
		return tipo;
	}

	public void setTipo(TipoTransacao tipo) {
		this.tipo = tipo;
	}

	public Boolean getConciliado() {
		return conciliado;
	}

	public void isConciliado(Boolean conciliado) {
		this.conciliado = conciliado;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Transacao other = (Transacao) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}
}