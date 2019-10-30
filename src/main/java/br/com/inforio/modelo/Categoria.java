package br.com.inforio.modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name="categorias")
@DynamicUpdate
public class Categoria {
	
	@Id
	@SequenceGenerator(name="sequence_categorias",
    				   sequenceName="sequence_categorias",
    				   allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="sequence_categorias")
	private Long codigo;
	
	@NotBlank
	private String nome;
	
	public Long getCodigo() {
		return codigo;
	}
	
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}	
}
