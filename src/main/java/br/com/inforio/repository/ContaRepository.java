package br.com.inforio.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.inforio.modelo.Conta;

public interface ContaRepository extends JpaRepository<Conta, Long> {

}
