package br.com.inforio.resource.v1;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.inforio.modelo.Transacao;
import br.com.inforio.repository.TransacaoRepository;

@RestController
@RequestMapping("v1/transacoes")
public class TransacaoRessource {

	@Autowired
	private TransacaoRepository transacaoRepository;	
	
	@GetMapping
	public ResponseEntity<List<Transacao>> buscarTodos(){
		List<Transacao> transacoes = transacaoRepository.findAll();
		return ResponseEntity.ok(transacoes);
	}
	
}
