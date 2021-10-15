package net.codejava;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface GreenPassRepository extends JpaRepository<GreenPass, Long> {
	@Query("SELECT g FROM GreenPass g WHERE g.uci_gp = ?1") 
	GreenPass findByUciGp(String uci_gp);
	
	@Query("SELECT g FROM GreenPass g WHERE g.codice_fiscale = ?1 AND g.uci_gp = ?2") 
	GreenPass findByUciGpAndCodiceFiscale(String codice_fiscale, String uci_gp);
	
	@Modifying
	@Transactional
	@Query("UPDATE GreenPass g SET g.data_scadenza = ?1 WHERE g.uci_gp =?2")
	void updateDataScadenza(String data_scadenza, String uci_gp);
}
