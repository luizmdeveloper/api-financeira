package br.com.inforio.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.inforio.modelo.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

}
