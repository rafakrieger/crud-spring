package br.com.rafael.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import br.com.rafael.entity.Cliente;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepositorio extends JpaRepository<Cliente, Long>{
		
	@Query("from Cliente where nome like %:nome%") 
	List<Cliente> pesquisarPorNome(String nome);
	
}
