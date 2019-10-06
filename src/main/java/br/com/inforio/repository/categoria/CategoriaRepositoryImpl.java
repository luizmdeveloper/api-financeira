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

import org.springframework.util.StringUtils;

import br.com.inforio.modelo.Categoria;
import br.com.inforio.modelo.Categoria_;
import br.com.inforio.repository.filter.CategoriaFilter;

public class CategoriaRepositoryImpl implements CategoriaRepositoryQuery {
	
	@PersistenceContext
	private EntityManager manager;

	@Override
	public List<Categoria> pesquisar(CategoriaFilter filter) {
		CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
		CriteriaQuery<Categoria> criteria = criteriaBuilder.createQuery(Categoria.class);
		Root<Categoria> root = criteria.from(Categoria.class);

		Predicate[] predicates = criarRestricoes(filter, criteriaBuilder, root); 
		criteria.where(predicates);

		TypedQuery<Categoria> query = manager.createQuery(criteria);
		return query.getResultList();
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

}
