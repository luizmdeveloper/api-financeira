package br.com.inforio.repository.conta;

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

import br.com.inforio.modelo.Conta;
import br.com.inforio.modelo.Conta_;
import br.com.inforio.repository.filter.ContaFilter;

public class ContaRepositoryImpl implements ContaRepositoryQuery {
	
	@PersistenceContext
	private EntityManager manager;

	@Override
	public List<Conta> pesquisar(ContaFilter filter) {
		CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
		CriteriaQuery<Conta> criteria = criteriaBuilder.createQuery(Conta.class);
		Root<Conta> root = criteria.from(Conta.class);
		
		
		Predicate[] predicates = criarRestricoes(filter, criteriaBuilder, root); 
		criteria.where(predicates);

		TypedQuery<Conta> query = manager.createQuery(criteria);
		return query.getResultList();
	}

	private Predicate[] criarRestricoes(ContaFilter filter, CriteriaBuilder criteriaBuilder, Root<Conta> root) {
		List<Predicate> predicates = new ArrayList<>();
		
		if (filter != null) {
			if (!StringUtils.isEmpty(filter.getNome())) {
				predicates.add(criteriaBuilder.like(criteriaBuilder.upper(root.get(Conta_.nome)), "%" + filter.getNome().toUpperCase() + "%"));
			}
		}
		
		return predicates.toArray(new Predicate[predicates.size()]);
	}
}
