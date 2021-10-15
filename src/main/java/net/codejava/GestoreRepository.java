package net.codejava;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface GestoreRepository extends JpaRepository<Gestore, Long> {
	@Query("SELECT g FROM Gestore g WHERE g.email = ?1") //?1 indica il primo parametro dato alla funzione findByEmail
	Gestore findByEmail(String email);
	
	@Query("SELECT g FROM Gestore g WHERE g.partita_iva = ?1") 
	Gestore findByPartitaIva(Long partita_iva);
	
	@Query("SELECT g FROM Gestore g WHERE g.organizzazione = ?1") 
	Gestore findByOrganizzazione(String organizzazione);
}
