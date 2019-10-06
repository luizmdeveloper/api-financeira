package br.com.inforio.modelo;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Perfil.class)
public abstract class Perfil_ {

	public static volatile SingularAttribute<Perfil, Long> codigo;
	public static volatile ListAttribute<Perfil, Autorizacao> autorizacoes;
	public static volatile SingularAttribute<Perfil, String> nome;

}

