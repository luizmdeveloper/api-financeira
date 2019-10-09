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

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import br.com.inforio.modelo.Categoria_;
import br.com.inforio.modelo.Conta_;
import br.com.inforio.modelo.Transacao;
import br.com.inforio.modelo.Transacao_;
import br.com.inforio.repository.filter.TransacaoFilter;
import br.com.inforio.repository.projecao.ResumoTransacao;

public class TransacaoRepositoryImpl implements TransacaoRepositoryQuery {

	@PersistenceContext
	private EntityManager manager;
		
	@Override
	public Page<Transacao> pesquisar(TransacaoFilter filter, Pageable page) {
		CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
		CriteriaQuery<Transacao> criteria = criteriaBuilder.createQuery(Transacao.class);
		Root<Transacao> root = criteria.from(Transacao.class);

		Predicate[] predicates = criarRestricoes(filter, criteriaBuilder, root); 
		criteria.where(predicates);
		TypedQuery<Transacao> query = manager.createQuery(criteria);
		adicionarRestricoesPaginacao(query, page);
		
		return new PageImpl<>(query.getResultList(), page, calcularTotal(filter, page));
	}
	
	@Override
	public Page<ResumoTransacao> pesquisarResumido(TransacaoFilter filter, Pageable page) {
		CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
		CriteriaQuery<ResumoTransacao> criteriaQuery = criteriaBuilder.createQuery(ResumoTransacao.class);
		Root<Transacao> root = criteriaQuery.from(Transacao.class);
		
		criteriaQuery.select(criteriaBuilder.construct(ResumoTransacao.class, root.get(Transacao_.codigo), 
																			  root.get(Transacao_.categoria).get(Categoria_.nome),
																			  root.get(Transacao_.conta).get(Conta_.nome),
																			  root.get(Transacao_.data),
																			  root.get(Transacao_.valor),
																			  root.get(Transacao_.tipo),
																			  root.get(Transacao_.conciliado)));
		
		Predicate[] predicates = criarRestricoes(filter, criteriaBuilder, root); 
		criteriaQuery.where(predicates);
		TypedQuery<ResumoTransacao> query = manager.createQuery(criteriaQuery);
		adicionarRestricoesPaginacao(query, page);
		
		return new PageImpl<>(query.getResultList(), page, calcularTotal(filter, page));
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
	
	private void adicionarRestricoesPaginacao(TypedQuery<?> query, Pageable page) {
		int paginaAtual = page.getPageNumber();
		int totalRegistroPorPagina = page.getPageSize();
		int primeiroRegistroDaPagina = paginaAtual * totalRegistroPorPagina;
		
		query.setFirstResult(primeiroRegistroDaPagina);
		query.setMaxResults(totalRegistroPorPagina);
	}

	private Long calcularTotal(TransacaoFilter filter, Pageable page) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Transacao> root = criteria.from(Transacao.class);
		
		Predicate[] predicates = criarRestricoes(filter, builder, root);
		criteria.where(predicates);
		
		criteria.select(builder.count(root));		
		return manager.createQuery(criteria).getSingleResult();
	}
}
