package net.codejava;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface GreenPassClienteRepository extends JpaRepository<GreenPassCliente, Long> {
	@Query("SELECT g FROM GreenPassCliente g WHERE g.uci_gp = ?1") 
	GreenPassCliente findByUciGp(String uci_gp);
	
	@Query("SELECT g FROM GreenPassCliente g WHERE g.codice_fiscale = ?1") 
	GreenPassCliente findByCodiceFiscale(String codice_fiscale);
	
	@Modifying
	@Transactional
	@Query("UPDATE GreenPassCliente g SET g.uci_gp = ?1 WHERE g.codice_fiscale =?2")
	void updateUciGp(String uci_gp, String codice_fiscale);
	
	@Modifying
	@Transactional
	@Query("UPDATE GreenPassCliente g SET g.data_scadenza = ?1 WHERE g.uci_gp =?2")
	void updateDataScadenza(String data_scadenza, String uci_gp);
	
	@Modifying
	@Transactional
	@Query("DELETE FROM GreenPassCliente g WHERE g.codice_fiscale = ?1")
	void deleteByCodiceFiscale(String codice_fiscale);
}
