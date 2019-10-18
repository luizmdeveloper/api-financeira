package br.com.inforio.repository.transacao;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
import br.com.inforio.modelo.TipoTransacao;
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
	
	@Override
	public BigDecimal calcularTotalCreditoCarteiraNoAnoMes(int anoMes) {		
		CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
		CriteriaQuery<BigDecimal> criteria = criteriaBuilder.createQuery(BigDecimal.class);
		Root<Transacao> root = criteria.from(Transacao.class);
		root.join(Transacao_.conta);

		Predicate[] predicates = criarRestricoesCalculoSaldo(criteriaBuilder, root, TipoTransacao.C, false); 
		criteria.select(criteriaBuilder.sum(root.get(Transacao_.valor)));
		criteria.where(predicates);
		TypedQuery<BigDecimal> query = manager.createQuery(criteria);
		
		Optional<BigDecimal> optional = Optional.ofNullable(query.getSingleResult());
		return optional.orElse(BigDecimal.ZERO);		
	}

	@Override
	public BigDecimal calcularTotalDebitoCarteiraNoAnoMes(int anoMes) {
		CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
		CriteriaQuery<BigDecimal> criteria = criteriaBuilder.createQuery(BigDecimal.class);
		Root<Transacao> root = criteria.from(Transacao.class);
		root.join(Transacao_.conta);

		Predicate[] predicates = criarRestricoesCalculoSaldo(criteriaBuilder, root, TipoTransacao.D, false); 
		criteria.select(criteriaBuilder.sum(root.get(Transacao_.valor)));
		criteria.where(predicates);
		TypedQuery<BigDecimal> query = manager.createQuery(criteria);
		
		Optional<BigDecimal> optional = Optional.ofNullable(query.getSingleResult());
		return optional.orElse(BigDecimal.ZERO);		
	}
	
	@Override
	public BigDecimal calcularTotalCreditoBancoNoAnoMes(int anoMes) {	
		CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
		CriteriaQuery<BigDecimal> criteria = criteriaBuilder.createQuery(BigDecimal.class);
		Root<Transacao> root = criteria.from(Transacao.class);
		root.join(Transacao_.conta);

		Predicate[] predicates = criarRestricoesCalculoSaldo(criteriaBuilder, root, TipoTransacao.C, true); 
		criteria.select(criteriaBuilder.sum(root.get(Transacao_.valor)));
		criteria.where(predicates);
		TypedQuery<BigDecimal> query = manager.createQuery(criteria);
		
		Optional<BigDecimal> optional = Optional.ofNullable(query.getSingleResult());
		return optional.orElse(BigDecimal.ZERO);	
	}

	@Override
	public BigDecimal calcularTotalDebitoBancoNoAnoMes(int anoMes) {
		CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
		CriteriaQuery<BigDecimal> criteria = criteriaBuilder.createQuery(BigDecimal.class);
		Root<Transacao> root = criteria.from(Transacao.class);
		root.join(Transacao_.conta);

		Predicate[] predicates = criarRestricoesCalculoSaldo(criteriaBuilder, root, TipoTransacao.D, true); 
		criteria.select(criteriaBuilder.sum(root.get(Transacao_.valor)));
		criteria.where(predicates);
		TypedQuery<BigDecimal> query = manager.createQuery(criteria);
		
		Optional<BigDecimal> optional = Optional.ofNullable(query.getSingleResult());
		return optional.orElse(BigDecimal.ZERO);	
	}
		
	@Override
	public BigInteger buscarTotalTransacoesNoAnoMes(int anoMes) {
		return (BigInteger) manager.createNativeQuery(" SELECT COUNT(transacoes.codigo) FROM transacoes  " + 
													  " WHERE (EXTRACT(YEAR FROM transacoes.data_emissao) * 100) + EXTRACT(MONTH FROM transacoes.data_emissao) = ? ") 
								.setParameter(1, anoMes)
								.getSingleResult();
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
				predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(Transacao_.data),  LocalDate.parse(filter.getEmissaoDe(), DateTimeFormatter.ofPattern("yyyy-MM-dd"))));
			}

			if (filter.getEmissaoAte() != null) {				
				predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(Transacao_.data), LocalDate.parse(filter.getEmissaoAte(), DateTimeFormatter.ofPattern("yyyy-MM-dd"))));
			}
			
			if (!StringUtils.isEmpty(filter.getObservacao())) {
				predicates.add(criteriaBuilder.like(criteriaBuilder.upper(root.get(Transacao_.observacao)), "%" + filter.getObservacao().toUpperCase() + "%"));
			}
		}
		
		return predicates.toArray(new Predicate[predicates.size()]);
	}
	
	private Predicate[] criarRestricoesCalculoSaldo(CriteriaBuilder criteriaBuilder, Root<Transacao> root, TipoTransacao tipo, boolean isBanco) {
		List<Predicate> predicates = new ArrayList<>();
		
		predicates.add(criteriaBuilder.equal(root.get(Transacao_.conta).get(Conta_.banco), isBanco));
		predicates.add(criteriaBuilder.equal(root.get(Transacao_.tipo), tipo));
		
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
