package br.com.inforio.modelo;

import java.math.BigDecimal;
import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Transacao.class)
public abstract class Transacao_ {

	public static volatile SingularAttribute<Transacao, Long> codigo;
	public static volatile SingularAttribute<Transacao, String> observacao;
	public static volatile SingularAttribute<Transacao, TipoTransacao> tipo;
	public static volatile SingularAttribute<Transacao, LocalDate> data;
	public static volatile SingularAttribute<Transacao, Categoria> categoria;
	public static volatile SingularAttribute<Transacao, Conta> conta;
	public static volatile SingularAttribute<Transacao, BigDecimal> valor;
	public static volatile SingularAttribute<Transacao, Boolean> conciliado;

}

