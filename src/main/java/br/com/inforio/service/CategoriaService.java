package br.com.inforio.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.inforio.modelo.Categoria;
import br.com.inforio.repository.CategoriaRepository;
import br.com.inforio.service.exception.CategoriaNaoCadastradaException;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository categoriaRepository;	

	public Categoria alterar(Long codigo, Categoria categoria) {
		Categoria categoriaSalva = buscarCategoriaPorCodigo(codigo);
		BeanUtils.copyProperties(categoria, categoriaSalva, "codigo");
		return categoriaRepository.save(categoriaSalva);
	}

	public void apagar(Long codigo) {
		Categoria categoria = buscarCategoriaPorCodigo(codigo);
		categoriaRepository.delete(categoria);
	}
	
	private Categoria buscarCategoriaPorCodigo(Long codigo) {
		Optional<Categoria> optionalCategoria = categoriaRepository.findById(codigo);
		if (!optionalCategoria.isPresent()) {
			throw new CategoriaNaoCadastradaException();
		}
		return optionalCategoria.get();
	}	
}
