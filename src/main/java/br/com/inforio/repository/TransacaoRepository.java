package br.com.inforio.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.inforio.modelo.Transacao;
import br.com.inforio.repository.transacao.TransacaoRepositoryQuery;

public interface TransacaoRepository extends JpaRepository<Transacao, Long>, TransacaoRepositoryQuery {

}