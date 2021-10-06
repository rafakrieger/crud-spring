package br.com.rafael.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.rafael.entity.Pedido;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepositorio extends JpaRepository<Pedido, Long>{
	
	

}
