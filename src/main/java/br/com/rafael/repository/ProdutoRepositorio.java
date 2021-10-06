package br.com.rafael.repository;

import org.springframework.stereotype.Repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import br.com.rafael.entity.Produto;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface ProdutoRepositorio extends JpaRepository<Produto, Long>{
	
	@Query("from Produto where nome like %:nome%") 
	List<Produto> pesquisarPorNome(String nome);

}
