package br.com.rafael.controller;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import br.com.rafael.entity.Cliente;
import br.com.rafael.repository.ClienteRepositorio;

@RestController
@RequestMapping("/clientes")
public class ClienteControle {
	
	@Autowired
	private ClienteRepositorio clienteRepositorio;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cliente salvar(@RequestBody Cliente cliente) {
		return clienteRepositorio.save(cliente);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Cliente> atualizar(@PathVariable Long id, @RequestBody Cliente cliente) {
		if(!clienteRepositorio.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		cliente.setId(id);
		clienteRepositorio.save(cliente);
		return ResponseEntity.ok(cliente);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Cliente> excluir(@PathVariable Long id) {
		if(!clienteRepositorio.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		clienteRepositorio.deleteById(id);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/todos")
	public List<Cliente> pesquisarTudo() {
		return clienteRepositorio.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Cliente> pesquisarPorId(@PathVariable Long id) {
		Optional<Cliente> cliente = clienteRepositorio.findById(id);
		if (cliente.isPresent()) {
			return ResponseEntity.ok(cliente.get());
		}		
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("/pesquisarPorNome/{nome}")
	public List<Cliente> pesquisarPorNome(@PathVariable String nome) {
		return clienteRepositorio.pesquisarPorNome(nome);
	}
	
		
}
