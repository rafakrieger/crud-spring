package br.com.rafael.controller;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import br.com.rafael.entity.Produto;
import br.com.rafael.repository.ProdutoRepositorio;

@RestController
@RequestMapping("/produtos")
public class ProdutoControle {
	
	@Autowired
	private ProdutoRepositorio produtoRepositorio;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Produto salvar(@RequestBody Produto produto) {
		return produtoRepositorio.save(produto);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Produto> atualizar(@PathVariable Long id, @RequestBody Produto produto) {
		if(!produtoRepositorio.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		produto.setId(id);
		produtoRepositorio.save(produto);
		return ResponseEntity.ok(produto);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Produto> excluir(@PathVariable Long id) {
		if(!produtoRepositorio.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		produtoRepositorio.deleteById(id);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/todos")
	public List<Produto> pesquisarTudo() {
		return produtoRepositorio.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Produto> pesquisarPorId(@PathVariable Long id) {
		Optional<Produto> produto = produtoRepositorio.findById(id);
		if (produto.isPresent()) {
			return ResponseEntity.ok(produto.get());
		}		
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("/pesquisarPorNome/{nome}")
	public List<Produto> pesquisarPorNome(@PathVariable String nome) {
		return produtoRepositorio.pesquisarPorNome(nome);
	}	
	
}
