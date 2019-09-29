package br.com.inforio.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.inforio.modelo.Transacao;

public interface TransacaoRepository extends JpaRepository<Transacao, Long> {

}