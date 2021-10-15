package net.codejava;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
	@Query("SELECT c FROM Cliente c WHERE c.email = ?1") //?1 indica il primo parametro dato alla funzione findByEmail
	Cliente findByEmail(String email);
	
	@Query("SELECT c FROM Cliente c WHERE c.codice_fiscale = ?1")
	Cliente findByCf(String codice_fiscale);
	
}
