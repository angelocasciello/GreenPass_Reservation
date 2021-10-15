package net.codejava;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class EventoRepositoryTests {

	@Autowired
	private EventoRepository repo_event;
	
	@Autowired
	private GestoreRepository repo_gest;
	
	@Autowired
	private PrenotazioneRepository repo_pren;
	
	@Test
	public void testCreateEvento( ) {
		//Precondizione: il gestore deve essere registrato alla piattaforma
		Gestore gestore = repo_gest.findByOrganizzazione("Vivaticket");
		//Variabili che identificano l'evento
		String Nome_evento = "Concerto di Capodanno";
		String Data_evento = "2021-12-31";
		String Orario_evento = "22:00";
		String Luogo_evento = "Piazza del Plebiscito";
		Long Num_posti_disponibili = 1000L;
		String Email_gestore = gestore.getEmail();
		String Organizzazione = gestore.getOrganizzazione();
		//Codice indipendente dalle variabili
		Evento evento = new Evento();
		evento.setNome(Nome_evento);
		evento.setLuogo(Luogo_evento);
		evento.setData(Data_evento);
		evento.setOrario(Orario_evento);
		evento.setNum_posti_disponibili(Num_posti_disponibili);
		evento.setEmail_gestore(Email_gestore);
		evento.setOrganizzazione(Organizzazione);
		repo_event.save(evento);					//qui non faccio nessun controllo perchè vengono fatti già nel form

	}
	
	@Test
	public void testModificaNomeEvento ( ) {
		//Precondizione: l'evento deve essere presente sulla piattaforma
		Long codice_evento = 5L;
		Evento evento = repo_event.findByCodice(codice_evento);
		String nuovo_nome_evento = "Cena di fine anno";
		repo_event.updateNome(nuovo_nome_evento, codice_evento);
		//Postcondizione: le prenotazioni associate all'evento devono essere aggiornate
		List<Prenotazione> prenotazioni = repo_pren.findByCodiceEvento(codice_evento);
		for(Prenotazione prenotazione: prenotazioni) {
			if(prenotazione.getCodice_evento() == codice_evento) {
				repo_pren.updateNomeEvento(nuovo_nome_evento, codice_evento);
			}
		}
	}
	
	@Test
	public void testModificaDataEvento ( ) {
		//Precondizione: l'evento deve essere presente sulla piattaforma
		Long codice_evento = 5L;
		Evento evento = repo_event.findByCodice(codice_evento);
		String nuova_data_evento = "2022-12-31";
		repo_event.updateData(nuova_data_evento, codice_evento);
		//Postcondizione: le prenotazioni associate all'evento devono essere aggiornate
		List<Prenotazione> prenotazioni = repo_pren.findByCodiceEvento(codice_evento);
		for(Prenotazione prenotazione: prenotazioni) {
			if(prenotazione.getCodice_evento() == codice_evento) {
				repo_pren.updateDataEvento(nuova_data_evento, codice_evento);
			}
		}
	}
	
	@Test
	public void testModificaOrarioEvento ( ) {
		//Precondizione: l'evento deve essere presente sulla piattaforma
		Long codice_evento = 5L;
		Evento evento = repo_event.findByCodice(codice_evento);
		String nuovo_orario_evento = "23:00";
		repo_event.updateOrario(nuovo_orario_evento, codice_evento);
		//Postcondizione: le prenotazioni associate all'evento devono essere aggiornate
		List<Prenotazione> prenotazioni = repo_pren.findByCodiceEvento(codice_evento);
		for(Prenotazione prenotazione: prenotazioni) {
			if(prenotazione.getCodice_evento() == codice_evento) {
				repo_pren.updateOrarioEvento(nuovo_orario_evento, codice_evento);
			}
		}
	}
	
	@Test
	public void testModificaLuogoEvento ( ) {
		//Precondizione: l'evento deve essere presente sulla piattaforma
		Long codice_evento = 5L;
		Evento evento = repo_event.findByCodice(codice_evento);
		String nuovo_luogo_evento = "Lungomare Mergellina";
		repo_event.updateLuogo(nuovo_luogo_evento, codice_evento);
		//Postcondizione: le prenotazioni associate all'evento devono essere aggiornate
		List<Prenotazione> prenotazioni = repo_pren.findByCodiceEvento(codice_evento);
		for(Prenotazione prenotazione: prenotazioni) {
			if(prenotazione.getCodice_evento() == codice_evento) {
				repo_pren.updateLuogoEvento(nuovo_luogo_evento, codice_evento);
			}
		}
	}
	
	@Test
	public void testModificaNumPostiEvento ( ) {
		//Precondizione: l'evento deve essere presente sulla piattaforma
		Long codice_evento = 5L;
		Evento evento = repo_event.findByCodice(codice_evento);
		Long nuovo_num_posti_disponibili = 1000L;
		repo_event.updateNumPosti(nuovo_num_posti_disponibili, codice_evento);
	}
	
	@Test
	public void testDeleteEvento ( ) {
		//Precondizione: l'evento deve essere presente sulla piattaforma
		Long codice_evento = 5L;
		repo_event.deleteByCodice(codice_evento);
		//Postcondizione: le prenotazioni associate all'evento devono essere eliminate
		repo_pren.deleteByCodiceEvento(codice_evento);
	}
}
