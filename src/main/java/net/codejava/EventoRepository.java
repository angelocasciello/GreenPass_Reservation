package net.codejava;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface EventoRepository extends JpaRepository<Evento, Long> {
		@Query("SELECT e FROM Evento e WHERE e.email_gestore = ?1") //?1 indica il primo parametro dato alla funzione findByEmail
		 List<Evento> findByEmail(String email);
		
		@Query("SELECT e FROM Evento e WHERE e.codice_evento = ?1") 
		Evento findByCodice(Long codice_evento);

		@Modifying
		@Transactional
		@Query("DELETE FROM Evento e WHERE e.codice_evento = ?1")
		void deleteByCodice(Long codice_evento);
		
		@Modifying
		@Transactional
		@Query("UPDATE Evento e SET e.nome = ?1 WHERE e.codice_evento =?2")
		void updateNome(String nome, Long codice_evento);
		
		@Modifying
		@Transactional
		@Query("UPDATE Evento e SET e.data = ?1 WHERE e.codice_evento =?2")
		void updateData(String data, Long codice_evento);
		
		@Modifying
		@Transactional
		@Query("UPDATE Evento e SET e.orario = ?1 WHERE e.codice_evento =?2")
		void updateOrario(String orario, Long codice_evento);
		
		@Modifying
		@Transactional
		@Query("UPDATE Evento e SET e.luogo = ?1 WHERE e.codice_evento =?2")
		void updateLuogo(String luogo, Long codice_evento);
		
		@Modifying
		@Transactional
		@Query("UPDATE Evento e SET e.num_posti_disponibili = ?1 WHERE e.codice_evento =?2")
		void updateNumPosti(Long num_posti_disponibili, Long codice_evento);
	}


