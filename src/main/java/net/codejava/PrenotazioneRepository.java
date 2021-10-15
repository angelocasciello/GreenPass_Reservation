package net.codejava;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface PrenotazioneRepository extends JpaRepository<Prenotazione, Long> {
	@Query("SELECT p FROM Prenotazione p WHERE p.email_cliente = ?1") 
	List<Prenotazione> findByEmail(String email_cliente);
	
	@Query("SELECT p FROM Prenotazione p WHERE p.codice_evento = ?1") 
	List<Prenotazione> findByCodiceEvento(Long codice_evento);
	
	@Query("SELECT p FROM Prenotazione p WHERE p.codice_evento = ?1 AND p.email_cliente = ?2")
	Prenotazione findExistingPrenotazione(Long codice_evento, String email_cliente);

	@Modifying
	@Transactional
	@Query("DELETE FROM Prenotazione p WHERE p.codice_evento = ?1 AND p.email_cliente = ?2")
	void deleteExistingPrenotazione(Long codice_evento, String email_cliente);
	
	@Modifying
	@Transactional
	@Query("DELETE FROM Prenotazione p WHERE p.codice_evento = ?1")
	void deleteByCodiceEvento(Long codice_evento);
	
	@Modifying
	@Transactional
	@Query("UPDATE Prenotazione p SET p.nome_evento = ?1 WHERE p.codice_evento =?2")
	void updateNomeEvento(String nome_evento, Long codice_evento);
	
	@Modifying
	@Transactional
	@Query("UPDATE Prenotazione p SET p.luogo_evento = ?1 WHERE p.codice_evento =?2")
	void updateLuogoEvento(String luogo_evento, Long codice_evento);
	
	@Modifying
	@Transactional
	@Query("UPDATE Prenotazione p SET p.data_evento = ?1 WHERE p.codice_evento =?2")
	void updateDataEvento(String data_evento, Long codice_evento);
	
	@Modifying
	@Transactional
	@Query("UPDATE Prenotazione p SET p.orario_evento = ?1 WHERE p.codice_evento =?2")
	void updateOrarioEvento(String orario_evento, Long codice_evento);
	
}
