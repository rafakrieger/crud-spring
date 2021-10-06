package br.com.rafael.controller;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import br.com.rafael.entity.Pedido;
import br.com.rafael.entity.Produto;
import br.com.rafael.repository.ClienteRepositorio;
import br.com.rafael.repository.PedidoRepositorio;
import br.com.rafael.repository.ProdutoRepositorio;

@RestController
@RequestMapping("/pedidos")
public class PedidoControle {
	
	@Autowired
	private PedidoRepositorio pedidoRepositorio;
	@Autowired
	private ClienteRepositorio clienteRepositorio;
	@Autowired
	private ProdutoRepositorio produtoRepositorio;
		
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Pedido salvar(@RequestBody Pedido pedido) {
		return pedidoRepositorio.save(pedido);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Pedido> atualizar(@PathVariable Long id, @RequestBody Pedido pedido) {
		if(!pedidoRepositorio.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		pedido.setId(id);
		pedidoRepositorio.save(pedido);
		return ResponseEntity.ok(pedido);
	}
	
	@PutMapping("/{id}/cliente/adicionar/{idCliente}")
	public ResponseEntity<Pedido> adicionarCliente(@PathVariable Long id, @PathVariable Long idCliente, @RequestBody Pedido pedido) {
		if(!clienteRepositorio.existsById(idCliente)) {
			return ResponseEntity.notFound().build();		
		} else {
			pedido.setCliente(clienteRepositorio.getById(idCliente));
		}
		this.atualizar(id, pedido);
		return ResponseEntity.ok(pedido);
	}
	
	@PutMapping("/{id}/cliente/excluir")
	public ResponseEntity<Pedido> excluirCliente(@PathVariable Long id, @RequestBody Pedido pedido) {
		pedido.setCliente(null);
		this.atualizar(id, pedido);
		return ResponseEntity.ok(pedido);
	}
	
	@PutMapping("/{id}/produtos/adicionar/{idProduto}")
	public ResponseEntity<Pedido> adicionarProduto(@PathVariable Long id, @PathVariable Long idProduto, @RequestBody Pedido pedido) {
		if (!produtoRepositorio.existsById(idProduto)) {
			return ResponseEntity.notFound().build();
		} else {
			List<Produto> produtosPedido = pedidoRepositorio.findAll().get(id.intValue()-1).getProdutos();
			produtosPedido.add(produtoRepositorio.getById(idProduto));
			pedido.setProdutos(produtosPedido);
		}		
		this.atualizar(id, pedido);
		return ResponseEntity.ok(pedido);
	}
	
	@PutMapping("/{id}/produtos/excluir/{idProduto}")
	public ResponseEntity<Pedido> excluirProduto(@PathVariable Long id, @PathVariable Long idProduto, @RequestBody Pedido pedido) {
		if (!produtoRepositorio.existsById(idProduto)) {
			return ResponseEntity.notFound().build();
		} else {
			List<Produto> produtosPedido = pedidoRepositorio.findAll().get(id.intValue()-1).getProdutos();
			for (int i = 0; i < produtosPedido.size(); i++) {
				if (produtosPedido.get(i).getId() == idProduto) {
					produtosPedido.remove(i);
				}
			}
			pedido.setProdutos(produtosPedido);
		}		
		this.atualizar(id, pedido);
		return ResponseEntity.ok(pedido);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Pedido> excluir(@PathVariable Long id) {
		if(!pedidoRepositorio.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		pedidoRepositorio.deleteById(id);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/todos")
	public List<Pedido> pesquisarTudo() {
		return pedidoRepositorio.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Pedido> pesquisarPorId(@PathVariable Long id) {
		Optional<Pedido> pedido = pedidoRepositorio.findById(id);
		if (pedido.isPresent()) {
			return ResponseEntity.ok(pedido.get());
		}		
		return ResponseEntity.notFound().build();
	}

}
