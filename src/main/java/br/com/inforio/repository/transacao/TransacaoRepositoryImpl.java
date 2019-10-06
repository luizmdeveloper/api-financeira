package br.com.inforio.repository.transacao;

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

import br.com.inforio.modelo.Transacao;
import br.com.inforio.modelo.Transacao_;
import br.com.inforio.repository.filter.TransacaoFilter;

public class TransacaoRepositoryImpl implements TransacaoRepositoryQuery {

	@PersistenceContext
	private EntityManager manager;
		
	@Override
	public List<Transacao> pesquisar(TransacaoFilter filter) {
		CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
		CriteriaQuery<Transacao> criteria = criteriaBuilder.createQuery(Transacao.class);
		Root<Transacao> root = criteria.from(Transacao.class);

		Predicate[] predicates = criarRestricoes(filter, criteriaBuilder, root); 
		criteria.where(predicates);
		
		TypedQuery<Transacao> query = manager.createQuery(criteria);
		return query.getResultList();
	}


	private Predicate[] criarRestricoes(TransacaoFilter filter, CriteriaBuilder criteriaBuilder, Root<Transacao> root) {
		
		List<Predicate> predicates = new ArrayList<>();
		
		if (filter != null) {
			if (filter.getCategoria() != null) {
				predicates.add(criteriaBuilder.equal(root.get(Transacao_.categoria), filter.getCategoria()));
			}
			
			if (filter.getConta() != null) {
				predicates.add(criteriaBuilder.equal(root.get(Transacao_.conta), filter.getConta()));
			}
			
			if (filter.getEmissaoDe() != null) {
				predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(Transacao_.data), filter.getEmissaoDe()));
			}

			if (filter.getEmissaoAte() != null) {
				predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(Transacao_.data), filter.getEmissaoAte()));
			}
			
			if (!StringUtils.isEmpty(filter.getObservacao())) {
				predicates.add(criteriaBuilder.like(criteriaBuilder.upper(root.get(Transacao_.observacao)), "%" + filter.getObservacao().toUpperCase() + "%"));
			}
		}
		
		return predicates.toArray(new Predicate[predicates.size()]);
	}

}
