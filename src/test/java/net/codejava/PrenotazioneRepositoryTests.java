package net.codejava;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertNotNull;

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
public class PrenotazioneRepositoryTests {

	@Autowired
	private PrenotazioneRepository repo_pren;
	
	@Autowired
	private EventoRepository repo_event;
	
	@Autowired
	private ClienteRepository repo_client;
	
	@Autowired
	private GreenPassClienteRepository repo_gp_client;
	
	@Test
	public void testCreatePrenotazione( ) {
		//Precondizioni: il cliente deve essere registrato alla piattaforma e l'evento deve essere presente nella piattaforma
		//Variabili che identificano la prenotazione
		Long codice_evento = 5L;								//codice dell'evento a cui prenotare
		String email_cliente = "ang.casciello@gmail.com";		//email del cliente che deve effettuare la prenotazione
		//Codice indipendente dalle variabili
		Evento evento = repo_event.findByCodice(codice_evento);
		Cliente cliente = repo_client.findByEmail(email_cliente);
		String organizzazione = evento.getOrganizzazione();
		Long num_posti_disponibili = evento.getNum_posti_disponibili();
		String nome_cliente = cliente.getNome();
		String codice_fiscale = cliente.getCodice_fiscale();
		String cognome_cliente = cliente.getCognome();
		Long num_telefono_cliente = cliente.getNum_telefono();
		String nome_evento = evento.getNome();
		String data_evento = evento.getData();
		String luogo_evento = evento.getLuogo();
		String orario_evento = evento.getOrario();
		GreenPassCliente gp_cliente = repo_gp_client.findByCodiceFiscale(codice_fiscale);
		assertNotNull(gp_cliente);										//verifica che il cliente abbia il gp
		String scadenza_gp_cliente = gp_cliente.getData_scadenza();
		LocalDate data_scadenza = LocalDate.parse(scadenza_gp_cliente); 
		LocalDate giorno_evento = LocalDate.parse(data_evento);
		assertThat(data_scadenza.isAfter(giorno_evento));				//verifica che la data di scadenza del gp sia successiva alla data dell'evento
		assertThat(num_posti_disponibili > 0);							//verifica l'evento non sia pieno
		Prenotazione prenotazione = new Prenotazione();
		prenotazione.setCodice_evento(codice_evento);
		prenotazione.setNome_evento(nome_evento);
		prenotazione.setOrganizzazione(organizzazione);
		prenotazione.setData_evento(data_evento);
		prenotazione.setOrario_evento(orario_evento);
		prenotazione.setLuogo_evento(luogo_evento);
		prenotazione.setEmail_cliente(email_cliente);
		prenotazione.setNome_cliente(nome_cliente);
		prenotazione.setCognome_cliente(cognome_cliente);
		prenotazione.setNum_telefono_cliente(num_telefono_cliente);
		repo_pren.save(prenotazione);
		//Postcondizione: il numero di posti disponibili dell'evento deve essere decrementato di 1
		num_posti_disponibili -= 1;
		repo_event.updateNumPosti(num_posti_disponibili, codice_evento);
	}
	
	@Test
	public void testDeletePrenotazione ( ) {
		//Precondizione: la prenotazione deve essere presente nella piattaforma
		Long codice_evento = 5L;								
		String email_cliente = "ang.casciello@gmail.com";
		repo_pren.deleteExistingPrenotazione(codice_evento, email_cliente);
	}
	
}
