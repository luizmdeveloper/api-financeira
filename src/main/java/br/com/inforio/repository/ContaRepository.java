package br.com.inforio.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.inforio.modelo.Conta;
import br.com.inforio.repository.conta.ContaRepositoryQuery;

public interface ContaRepository extends JpaRepository<Conta, Long>, ContaRepositoryQuery {

}
