package br.com.inforio.resource.v1;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.inforio.modelo.Conta;
import br.com.inforio.repository.ContaRepository;

@RestController
@RequestMapping("v1/contas")
public class ContaV1Resource {
	
	@Autowired
	private ContaRepository contaRepository;
	
	@GetMapping
	public ResponseEntity<List<Conta>> buscarTodos(){
		List<Conta> contas = contaRepository.findAll();
		return ResponseEntity.ok(contas);
	}

}
