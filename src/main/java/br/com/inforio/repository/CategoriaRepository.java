package br.com.inforio.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.inforio.modelo.Categoria;
import br.com.inforio.repository.categoria.CategoriaRepositoryQuery;

public interface CategoriaRepository extends JpaRepository<Categoria, Long>, CategoriaRepositoryQuery {

}
