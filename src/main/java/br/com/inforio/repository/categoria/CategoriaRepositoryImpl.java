package br.com.inforio.repository.categoria;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import br.com.inforio.modelo.Categoria;
import br.com.inforio.modelo.Categoria_;
import br.com.inforio.repository.filter.CategoriaFilter;

public class CategoriaRepositoryImpl implements CategoriaRepositoryQuery {
	
	@PersistenceContext
	private EntityManager manager;

	@Override
	public Page<Categoria> pesquisar(CategoriaFilter filter, Pageable page) {
		CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
		CriteriaQuery<Categoria> criteria = criteriaBuilder.createQuery(Categoria.class);
		Root<Categoria> root = criteria.from(Categoria.class);

		Predicate[] predicates = criarRestricoes(filter, criteriaBuilder, root); 
		criteria.where(predicates);

		TypedQuery<Categoria> query = manager.createQuery(criteria);
		adicionarRestricoesPaginacao(query, page);
		
		return new PageImpl<>(query.getResultList(), page, calcularTotalRegistro(filter));
	}

	private Predicate[] criarRestricoes(CategoriaFilter filter, CriteriaBuilder criteriaBuilder, Root<Categoria> root) {
		List<Predicate> predicates = new ArrayList<>();
		
		if (filter != null) {
			if (!StringUtils.isEmpty(filter.getNome())) {
				predicates.add(criteriaBuilder.like(criteriaBuilder.upper(root.get(Categoria_.nome)),  "%" + filter.getNome().toUpperCase() + "%"));
			}
		}
		
		return predicates.toArray(new Predicate[predicates.size()]);
	}

	private void adicionarRestricoesPaginacao(TypedQuery<Categoria> query, Pageable page) {
		int paginaAtual = page.getPageNumber();
		int totalRegistroPorPagina = page.getPageSize();
		int primeiroRegistroDaPagina = paginaAtual * totalRegistroPorPagina;
		
		query.setFirstResult(primeiroRegistroDaPagina);
		query.setMaxResults(totalRegistroPorPagina);
	}
	
	private Long calcularTotalRegistro(CategoriaFilter filter) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Categoria> root = criteria.from(Categoria.class);
		
		Predicate[] predicates = criarRestricoes(filter, builder, root);
		criteria.where(predicates);
		
		criteria.select(builder.count(root));
		
		return manager.createQuery(criteria).getSingleResult();
	}	
}
