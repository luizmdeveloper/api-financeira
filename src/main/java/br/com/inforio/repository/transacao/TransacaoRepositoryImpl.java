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
import javax.persistence.Query;
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
import br.com.inforio.modelo.TotalTransacaoNoPeriodo;
import br.com.inforio.modelo.Transacao;
import br.com.inforio.modelo.TransacaoPorCategoria;
import br.com.inforio.modelo.Transacao_;
import br.com.inforio.repository.filter.TotalTransacaoFilter;
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
		Query query = manager.createNativeQuery(retornarSQLCalculoSaldoConta())
						.setParameter(1, anoMes)
						.setParameter(2, "C")
						.setParameter(3, true)
						.setParameter(4, false);
		
		Optional<BigDecimal> optional = Optional.ofNullable((BigDecimal) query.getSingleResult());
		return optional.orElse(BigDecimal.ZERO);		
	}

	@Override
	public BigDecimal calcularTotalDebitoCarteiraNoAnoMes(int anoMes) {
		Query query = manager.createNativeQuery(retornarSQLCalculoSaldoConta())
						.setParameter(1, anoMes)
						.setParameter(2, "D")
						.setParameter(3, true)
						.setParameter(4, false);
		
		Optional<BigDecimal> optional = Optional.ofNullable((BigDecimal) query.getSingleResult());
		return optional.orElse(BigDecimal.ZERO);		
	}
	
	@Override
	public BigDecimal calcularTotalCreditoBancoNoAnoMes(int anoMes) {	
		Query query = manager.createNativeQuery(retornarSQLCalculoSaldoConta())
				.setParameter(1, anoMes)
				.setParameter(2, "C")
				.setParameter(3, true)
				.setParameter(4, true);

		Optional<BigDecimal> optional = Optional.ofNullable((BigDecimal) query.getSingleResult());
		return optional.orElse(BigDecimal.ZERO);		
	}

	@Override
	public BigDecimal calcularTotalDebitoBancoNoAnoMes(int anoMes) {
		Query query = manager.createNativeQuery(retornarSQLCalculoSaldoConta())
				.setParameter(1, anoMes)
				.setParameter(2, "D")
				.setParameter(3, true)
				.setParameter(4, true);

		Optional<BigDecimal> optional = Optional.ofNullable((BigDecimal) query.getSingleResult());
		return optional.orElse(BigDecimal.ZERO);	
	}
		
	@Override
	public BigInteger buscarTotalTransacoesNoAnoMes(int anoMes) {
		return (BigInteger) manager.createNativeQuery(" SELECT COUNT(transacoes.codigo) FROM transacoes  " + 
													  " WHERE (EXTRACT(YEAR FROM transacoes.data_emissao) * 100) + EXTRACT(MONTH FROM transacoes.data_emissao) = ? ") 
								.setParameter(1, anoMes)
								.getSingleResult();
	}	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TransacaoPorCategoria> calcularTransacoesPorCategoria(int anoMes) {
		List<TransacaoPorCategoria> transacoesPorCategoria = new ArrayList<>();
		Query query = manager.createNativeQuery(retornarSQLGraficoCategoria())
								.setParameter(1, anoMes);
		
		List<Object[]> resultado = query.getResultList();
		
		for(Object[] coluna: resultado) {
			TransacaoPorCategoria transacaoPorCategoria = new TransacaoPorCategoria();
			transacaoPorCategoria.setCategoria(coluna[1].toString());
			transacaoPorCategoria.setPercentual(coluna[2] == "" || coluna[2] == null ? BigDecimal.ZERO : new BigDecimal(coluna[2].toString()));
			transacoesPorCategoria.add(transacaoPorCategoria);
		}
		
		return transacoesPorCategoria;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TotalTransacaoNoPeriodo> calcularTotalTransacaoNoIntervalo(TotalTransacaoFilter filter) {
		List<TotalTransacaoNoPeriodo> totaisTransacao = new ArrayList<TotalTransacaoNoPeriodo>();
		
		Query query = manager.createNativeQuery(retornarSQLGraficoPeriodo())
								.setParameter(1, filter.getAnoMesInicial())
								.setParameter(2, filter.getAnoMesFinal());
		
		List<Object[]> resultado = query.getResultList();
		
		for(Object[] coluna: resultado) {
			TotalTransacaoNoPeriodo totalTransacao = new TotalTransacaoNoPeriodo(((Double) coluna[0]), coluna[1].toString(), (BigDecimal)coluna[2]);
			totaisTransacao.add(totalTransacao);
		}
				
		return totaisTransacao;
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
	
	private String retornarSQLCalculoSaldoConta() {
		return " SELECT SUM(transacoes.valor) FROM transacoes " +
			   " LEFT OUTER JOIN contas ON contas.codigo = transacoes.codigo_conta " + 
			   " WHERE ((EXTRACT(YEAR FROM transacoes.data_emissao) * 100) + EXTRACT(MONTH FROM transacoes.data_emissao)) = ? " +
			   "   AND transacoes.tipo = ? " +
			   "   AND transacoes.conciliado = ? " +
			   "   AND contas.banco = ? ";
	}
	
	private String retornarSQLGraficoCategoria() {
		return " SELECT transacoes.codigo_categoria, " +
			   " 		categorias.nome, " + 
			   "	   (((SELECT SUM(t.valor) FROM transacoes AS t " + 
			   "	      WHERE t.codigo_categoria = transacoes.codigo_categoria) / " + 
			   "	     (SELECT SUM(total.valor) FROM transacoes AS total)) * 100) " + 
			   " FROM transacoes " + 
			   " LEFT OUTER JOIN categorias ON categorias.codigo = transacoes.codigo_categoria " + 
			   " WHERE ((EXTRACT(YEAR FROM transacoes.data_emissao) * 100) + EXTRACT(MONTH FROM transacoes.data_emissao)) = ? " + 
			   " GROUP BY transacoes.codigo_categoria, categorias.nome ";
	}
	
	private String retornarSQLGraficoPeriodo() {
		return " SELECT (EXTRACT(YEAR FROM transacoes.data_emissao) * 100) + EXTRACT(MONTH FROM transacoes.data_emissao) AS ano_mes, " + 
			   "	   tipo, " + 
			   "	   SUM(valor) " + 
			   " FROM public.transacoes " + 
			   " WHERE (EXTRACT(YEAR FROM transacoes.data_emissao) * 100) + EXTRACT(MONTH FROM transacoes.data_emissao) BETWEEN ? AND ?" + 
			   " GROUP BY ano_mes, tipo ";
	}
}
