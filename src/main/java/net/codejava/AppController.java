package net.codejava;

import java.time.LocalDate;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class AppController {

	//Repositories
	
	@Autowired
	private ClienteRepository repo_client;				//repository clienti
	
	@Autowired
	private GestoreRepository repo_gest;				//repository gestori
	
	@Autowired
	private EventoRepository repo_event;				//repository eventi
	
	@Autowired
	private PrenotazioneRepository repo_pren;			//repository prenotazioni
	
	@Autowired
	private GreenPassRepository repo_gp;				//repository green pass (DB Green Pass)
		
	@Autowired
	private GreenPassClienteRepository repo_gp_client;	//repository green pass clienti
	
	//Funzioni "Show" -> funzioni che restituiscono qualcosa che l'utente vuole visualizzare 
	
	@GetMapping("/home")
	public String ShowHomePage() {
		return "index"; 						//restituisce la home page
	}
	
	@GetMapping("/form_registrazione_clienti")
	public String ShowFormRegistrazioneCliente(Model model) {
		model.addAttribute("cliente", new Cliente());
		return "form_registrazione_clienti"; 	//restituisce il form di registrazione clienti
	}
	
	@GetMapping("/form_registrazione_gestori")
	public String ShowFormRegistrazioneGestore(Model model) {
		model.addAttribute("gestore", new Gestore());
		return "form_registrazione_gestori";	 //restituisce il form di registrazione gestori
	}
	
	@GetMapping("/dashboard_cliente")
	public String ShowDashboardClienti() {		
		return "dashboard_cliente";						//il controllo d'accesso è gestito da WebSecurityConfig
	}
	
	@GetMapping("/dashboard_gestore")
	public String ShowDashboardGestori() {
		return "dashboard_gestore";						//il controllo d'accesso è gestito da WebSecurityConfig
	}
	
	@GetMapping("/pagina_inaccessibile")
	public String ShowPaginaInaccessibile() {
		return "pagina_inaccessibile";
	}
	
	@GetMapping("/form_creazione_evento")
	public String ShowFormCreazioneEvento(Model model, HttpServletRequest request) {
		if (request.isUserInRole("ROLE_GESTORE")) {				//se la richiesta proviene da un gestore
		model.addAttribute("evento", new Evento());
		return "form_creazione_evento";							//restituisce il form di creazione evento
		}
		else {
			return "pagina_inaccessibile";						//altrimenti restituisce pagina inaccessibile
		}
	}
	
	@GetMapping("/form_inserimento_gp")
	public String ShowFormInserimentoGP(Model model, HttpServletRequest request) {
		if (request.isUserInRole("ROLE_CLIENTE")) {						//se la richiesta proviene da un cliente
		model.addAttribute("GPcliente", new GreenPassCliente());
		return "form_inserimento_gp";									//mostra il form di inserimento green_pass
		}
		else {
			return "pagina_inaccessibile";								//altrimenti restituisce pagina inaccessibile
		}
	}	
	
	@GetMapping("/visualizza_gp")
	public String ShowGP(Model model, HttpServletRequest request, Authentication authentication) {
		if (request.isUserInRole("ROLE_CLIENTE")) {														//se la richiesta proviene da un cliente
		CustomClienteDetails cliente_details = (CustomClienteDetails) authentication.getPrincipal();	//acquisisce le informazioni del cliente
		String email = cliente_details.getUsername();
	    Cliente cliente = repo_client.findByEmail(email);												//cerca il cliente nel DB mediante l'email
	    String codice_fiscale = cliente.getCodice_fiscale();
	    GreenPassCliente green_pass_cliente = repo_gp_client.findByCodiceFiscale(codice_fiscale);		//cerca il green pass cliente nel DB mediante il codice fiscale
	    if (green_pass_cliente == null) {																//se il cliente non ha un GP
	    	return "inserisci_gp";																		//restituisce pagina inserimento GP
	    }																	
	    model.addAttribute("green_pass_cliente", green_pass_cliente);									
		return "gp_cliente";																			//restituisce pagina GP
		}else {
			return "pagina_inaccessibile";																//altrimenti restituisce pagina inaccessibile
		}
	}

	@GetMapping("/visualizza_eventi_cliente")
	public String ShowEventiCliente(Model model, HttpServletRequest request, Authentication authentication) {
		if (request.isUserInRole("ROLE_CLIENTE")) {															//se la richiesta proviene da un cliente
		CustomClienteDetails cliente_details = (CustomClienteDetails) authentication.getPrincipal();		//acquisisce le informazioni del cliente
		String email = cliente_details.getUsername();
	    List<Evento> listEventi = repo_event.findAll();														//crea la lista degli eventi a cui l'utente può prenotarsi (a cui vi sono ancora posti disponibili e per i quali non si è già prenotato)
	    if(listEventi.isEmpty()) {																			//se la lista è vuota (non esistono eventi)
	    	return "nessun_evento_cliente";																		//restituisce pagina nessun evento disponibile
	    }
	    Iterator<Evento> iter = listEventi.iterator();														//rimuove dalla lista tutti gli eventi pieni o a cui il cliente è già prenotato
	    while (iter.hasNext()) {
	        Evento evento = iter.next();
	        if(evento.getNum_posti_disponibili()==0) {
	        	iter.remove();
	        } else {
	        Long codice_evento = evento.getCodice_evento();
	        Prenotazione prenotazione = repo_pren.findExistingPrenotazione(codice_evento, email);
	        if(prenotazione != null) {
	        	iter.remove();
	        }
	      }
	    }
	    if(listEventi.isEmpty()) {																				//se la lista è vuota
	    	return "nessun_evento_cliente";																		//restituisce pagina nessun evento disponibile
	    }
	    model.addAttribute("listEventi", listEventi);														
		return "eventi_cliente";																			//restituisce lista degli eventi disponibili
		}else {
			return "pagina_inaccessibile";																	//altrimenti restituisce pagina inaccessibile
		}
	}
	
	@GetMapping("/visualizza_eventi_gestore")
	public String ShowEventiGestore(Model model,HttpServletRequest request, Authentication authentication) {
		if (request.isUserInRole("ROLE_GESTORE")) {																//se la richiesta proviene da un gestore
		CustomGestoreDetails gestore_details = (CustomGestoreDetails) authentication.getPrincipal();			//acquisisce le informazioni del gestore
		String email = gestore_details.getUsername();
	    List<Evento> listEventi = repo_event.findByEmail(email);												//cerca nel DB gli eventi del gestore mediante l'email
	    if(listEventi.isEmpty()) {																				//se la lista è vuota
	    	return "nessun_evento_gestore";																		//restituisce pagina nessun evento disponibile
	    } 
	    model.addAttribute("listEventi", listEventi);
		return "eventi_gestore";																			//restituisce lista eventi gestore
		} else {
			return "pagina_inaccessibile";																	//altrimenti restituisce pagina inaccessibile
		}
	}
	
	@GetMapping("/form_modifica_evento")
	public String ShowFormModificaEvento(		
		@RequestParam(value = "codice_evento", required = false) String codice_evento, Model model, HttpServletRequest request) {
		if (request.isUserInRole("ROLE_GESTORE")) {
		Long codice_evento_fixed = Long.parseLong(codice_evento, 10);
	    Evento evento = repo_event.findByCodice(codice_evento_fixed);
	    model.addAttribute("evento", evento);
		return "form_modifica_evento";
		}
		else {
			return "pagina_inaccessibile";
		}
	}
	
	@GetMapping("/visualizza_prenotazioni_cliente")
	public String ShowPrenotazioniCliente(Model model, HttpServletRequest request, Authentication authentication) {
		if (request.isUserInRole("ROLE_CLIENTE")) {
		CustomClienteDetails cliente_details = (CustomClienteDetails) authentication.getPrincipal();
		String email = cliente_details.getUsername();
		List<Prenotazione> listPrenotazioni = repo_pren.findByEmail(email);
		 if(listPrenotazioni.isEmpty()) {
		    	return "nessuna_prenotazione_cliente";
		    } else {
	    model.addAttribute("listPrenotazioni", listPrenotazioni);
		return "prenotazioni_cliente";
		    }
		}else {
			return "pagina_inaccessibile";
		}
	}
	
	@GetMapping("/visualizza_prenotazioni_evento")
	public String ShowPrenotazioniEvento(
		@RequestParam(value = "codice_evento", required = false) String codice_evento,
		Model model, HttpServletRequest request) {
		if (request.isUserInRole("ROLE_GESTORE")) {
		Long codice_evento_fixed = Long.parseLong(codice_evento, 10);
		List<Prenotazione> listPrenotazioni = repo_pren.findByCodiceEvento(codice_evento_fixed);
		 if(listPrenotazioni.isEmpty()) {
		    	return "nessuna_prenotazione_evento";
		    } else {
	    model.addAttribute("listPrenotazioni", listPrenotazioni);
		return "prenotazioni_evento";
		    }
		}else {
			return "pagina_inaccessibile";
		}
	}
	
	//Funzioni "Create" -> funzioni che effettuano l'inserimento di un oggetto nel DB

	@PostMapping("/registrazione_cliente")
	public String CreateCliente(Cliente cliente) {
		String email = cliente.getEmail();
		String codice_fiscale = cliente.getCodice_fiscale();
		Cliente cliente_same_email = repo_client.findByEmail(email);				//cerca un cliente con la stessa email nel DB
		Cliente cliente_same_cf = repo_client.findByCf(codice_fiscale);				//cerca un cliente con stesso codice fiscale nel DB
		if (cliente_same_email != null) {											//se l'email inserita è già esistente
			return "registrazione_cliente_fallita_1"; 								//restituisce pagina di errore 
		}
		if(cliente_same_cf != null) {												//se il codice fiscale inserito è già esistente
				return "registrazione_cliente_fallita_2";							//restituisce pagina di errore
		}
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();		
		String encodedPassword = encoder.encode(cliente.getPassword());				//cripta la password con la funzione di hashing bcrypt
		cliente.setPassword(encodedPassword);
		repo_client.save(cliente);													//inserisce il cliente nel DB
		return "registrazione_avvenuta";											//restituisce pagina di successo
	}

	
	@PostMapping("/registrazione_gestore")
	public String CreateGestore(Gestore gestore) {
		String email = gestore.getEmail();
		Long partita_iva = gestore.getPartita_iva();
		String organizzazione = gestore.getOrganizzazione();
		Gestore gestore_same_email = repo_gest.findByEmail(email);									//cerca un gestore con stessa email nel DB
		Gestore gestore_same_partita_iva = repo_gest.findByPartitaIva(partita_iva);					//cerca un gestore con stessa partita IVA nel DB
		Gestore gestore_same_organizzazione = repo_gest.findByOrganizzazione(organizzazione);		//cerca un gestore con stesso nome organizzazione del DB
		if(gestore_same_email != null) {															//se l'email inserita è già esistente
			return "registrazione_gestore_fallita_1";												//restituisce pagina di errore 
			}
		if(gestore_same_organizzazione != null) {													//se il nome dell'organizzazione inserito è già esistente
			return "registrazione_gestore_fallita_2";												//restituisce pagina di errore
			}
		if(gestore_same_partita_iva != null) {														//se la partita IVA inserita è già esistente
			return "registrazione_gestore_fallita_3";												//restituisce pagina di errore
			}
	    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	    String encodedPassword = passwordEncoder.encode(gestore.getPassword());						//cripta la password con la funzione di hashing bcrypt
	    gestore.setPassword(encodedPassword);	
	    repo_gest.save(gestore);
	    return "registrazione_avvenuta";																//restituisce pagina di successo
	}

	
	@PostMapping("/creazione_evento")
	public String CreateEvento(Evento evento, HttpServletRequest request, Authentication authentication) {
		if (request.isUserInRole("ROLE_GESTORE")) {														//se la richiesta proviene da un gestore
		CustomGestoreDetails gestore_details = (CustomGestoreDetails) authentication.getPrincipal();	//acquisisce le informazioni del gestore
		String email_gestore = gestore_details.getUsername();											
		Gestore gestore = repo_gest.findByEmail(email_gestore);											//cerca il gestore nel DB mediante l'email
		String organizzazione = gestore.getOrganizzazione();
		evento.setEmail_gestore(email_gestore);
		evento.setOrganizzazione(organizzazione);
		repo_event.save(evento);																		//inserisce l'evento nel DB
		return "evento_creato";
		} else {
			return "pagina_inaccessibile";																//altrimenti restituisce pagina inaccessibile
		}
	}
	
	@PostMapping("/creazione_gp")
	public String CreateGP(@RequestParam String uci_gp, HttpServletRequest request, Authentication authentication) throws Exception { 
		if (request.isUserInRole("ROLE_CLIENTE")) {														//se la richiesta proviene da un cliente
		CustomClienteDetails cliente_details = (CustomClienteDetails) authentication.getPrincipal();	//acquisisce le informazioni del cliente
		String email = cliente_details.getUsername();													
		Cliente cliente = repo_client.findByEmail(email);												//cerca il cliente nel DB mediante l'email
		String codice_fiscale = cliente.getCodice_fiscale();
		GreenPass green_pass_same_uci_gp = repo_gp.findByUciGp(uci_gp);									//cerca il GP nel database dei Green Pass mediante l'uci gp
		if(green_pass_same_uci_gp == null) {															//se il GP non è presente in tale DB 
			return "gp_non_valido_1";																	//restituisce pagina di errore
		}
		GreenPass green_pass = repo_gp.findByUciGpAndCodiceFiscale(codice_fiscale, uci_gp);
		if(green_pass == null) {
			return "gp_non_valido_2";
		}
		GreenPassCliente green_pass_cliente = new GreenPassCliente();
		String data_scadenza = green_pass.getData_scadenza();
		green_pass_cliente.setCodice_fiscale(codice_fiscale);
		green_pass_cliente.setData_scadenza(data_scadenza);
		green_pass_cliente.setUci_gp(uci_gp);															
		repo_gp_client.save(green_pass_cliente);														//inserisce il GP nel DB
		return "gp_creato";																				//restituisce pagina di successo
		}else {
			return "pagina_inaccessibile";																//altrimenti restituisce pagina inaccessibile
		}
	}
	
	@PostMapping("/creazione_prenotazione")
	public String CreatePrenotazione(@RequestParam String codice_evento, HttpServletRequest request, Authentication authentication) throws Exception {
		if (request.isUserInRole("ROLE_CLIENTE")) {
		CustomClienteDetails cliente_details = (CustomClienteDetails) authentication.getPrincipal();
		String email_cliente = cliente_details.getUsername();	
		Long codice_evento_fixed = Long.parseLong(codice_evento, 10);
		Cliente cliente = repo_client.findByEmail(email_cliente);
		Evento evento = repo_event.findByCodice(codice_evento_fixed);
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
		if(gp_cliente == null) {
			return "prenotazione_fallita_errore_gp";
		} 
		String scadenza_gp_cliente = gp_cliente.getData_scadenza();
		LocalDate data_scadenza = LocalDate.parse(scadenza_gp_cliente); 
		LocalDate giorno_evento = LocalDate.parse(data_evento);
		if(data_scadenza.isBefore(giorno_evento)) {
			return "prenotazione_fallita_errore_gp";
			}
		if (num_posti_disponibili > 0) {
				Prenotazione prenotazione = new Prenotazione();
				prenotazione.setCodice_evento(codice_evento_fixed);
				prenotazione.setNome_evento(nome_evento);
				prenotazione.setOrganizzazione(organizzazione);
				prenotazione.setData_evento(data_evento);
				prenotazione.setOrario_evento(orario_evento);
				prenotazione.setLuogo_evento(luogo_evento);
				prenotazione.setEmail_cliente(email_cliente);
				prenotazione.setNome_cliente(nome_cliente);
				prenotazione.setCognome_cliente(cognome_cliente);
				prenotazione.setNum_telefono_cliente(num_telefono_cliente);
				num_posti_disponibili -= 1;
				repo_event.updateNumPosti(num_posti_disponibili, codice_evento_fixed);
				repo_pren.save(prenotazione);
				return "prenotazione_creata";	
			}else {
				return "prenotazione_fallita_evento_pieno";
			}
		}else {
			return "pagina_inaccessibile";
		}
	}
	
	//Funzioni "Delete" -> funzioni che effettuano la rimozione di un oggetto dal DB

	
	@PostMapping("/elimina_prenotazione")
	public String DeletePrenotazione(
		@RequestParam(value = "codice_evento", required = false) String codice_evento, HttpServletRequest request, Authentication authentication) {
		if (request.isUserInRole("ROLE_CLIENTE")) {
		CustomClienteDetails cliente_details = (CustomClienteDetails) authentication.getPrincipal();
		String email_cliente = cliente_details.getUsername();
		Long codice_evento_fixed = Long.parseLong(codice_evento, 10);
	    repo_pren.deleteExistingPrenotazione(codice_evento_fixed, email_cliente);
	    Evento evento = repo_event.findByCodice(codice_evento_fixed);
	    Long num_posti_disponibili = evento.getNum_posti_disponibili();
	    Long new_num_posti_disponibili = num_posti_disponibili+1;
	    repo_event.updateNumPosti(new_num_posti_disponibili, codice_evento_fixed);
		return "prenotazione_eliminata";
		}else {
			return "pagina_inaccessibile";
		}
	}
	
	@PostMapping("/elimina_evento")
	public String DeleteEvento(
		@RequestParam(value = "codice_evento", required = false) String codice_evento, HttpServletRequest request) {
		if (request.isUserInRole("ROLE_GESTORE")) {
		Long codice_evento_fixed = Long.parseLong(codice_evento, 10);
	    repo_event.deleteByCodice(codice_evento_fixed);
	    repo_pren.deleteByCodiceEvento(codice_evento_fixed);
		return "evento_eliminato";
		} else {
			return "pagina_inaccessibile";
		}
	}
	
	@PostMapping("/elimina_gp")
	public String DeleteGP(HttpServletRequest request, Authentication authentication) {
		if (request.isUserInRole("ROLE_CLIENTE")) {
		CustomClienteDetails cliente_details = (CustomClienteDetails) authentication.getPrincipal();
		String email = cliente_details.getUsername();
		Cliente cliente = repo_client.findByEmail(email);
		String codice_fiscale = cliente.getCodice_fiscale();
		repo_gp_client.deleteByCodiceFiscale(codice_fiscale);
		return "gp_eliminato";
		} else {
			return "pagina_inaccessibile";
		}
	}
	
	//Funzioni "Update" -> funzioni che effettuano l'aggiornamento di un oggetto nel DB
	
	
	@PostMapping("/modifica_nome_evento")
	public String UpdateNomeEvento(		
		@RequestParam Map<String,String> requestParams, HttpServletRequest request) throws Exception {
		if (request.isUserInRole("ROLE_GESTORE")) {
		String codice_evento = requestParams.get("codice_evento");
		Long codice_evento_fixed = Long.parseLong(codice_evento, 10);
		String nome = requestParams.get("nome");
		List<Prenotazione> prenotazioni = repo_pren.findByCodiceEvento(codice_evento_fixed);
		for(Prenotazione prenotazione: prenotazioni) {
			if(prenotazione.getCodice_evento() == codice_evento_fixed) {
				repo_pren.updateNomeEvento(nome, codice_evento_fixed);
			}
		}
		repo_event.updateNome(nome, codice_evento_fixed);
		return "evento_modificato";
		}else {
			return "pagina_inaccessibile";
		}
	}
	
	@PostMapping("/modifica_data_evento")
	public String UpdateDataEvento(		
		@RequestParam Map<String,String> requestParams, HttpServletRequest request) throws Exception {
		if (request.isUserInRole("ROLE_GESTORE")) {
		String codice_evento = requestParams.get("codice_evento");
		Long codice_evento_fixed = Long.parseLong(codice_evento, 10);
		String data = requestParams.get("date");
		List<Prenotazione> prenotazioni = repo_pren.findByCodiceEvento(codice_evento_fixed);
		for(Prenotazione prenotazione: prenotazioni) {
			if(prenotazione.getCodice_evento() == codice_evento_fixed) {
				repo_pren.updateDataEvento(data, codice_evento_fixed);
			}
		}
		repo_event.updateData(data, codice_evento_fixed);
		return "evento_modificato";
		}else {
			return "pagina_inaccessibile";
		}
	}
	
	@PostMapping("/modifica_orario_evento")
	public String UpdateOrarioEvento(		
		@RequestParam Map<String,String> requestParams, HttpServletRequest request) throws Exception {
		if (request.isUserInRole("ROLE_GESTORE")) {
		String codice_evento = requestParams.get("codice_evento");
		Long codice_evento_fixed = Long.parseLong(codice_evento, 10);
		String orario = requestParams.get("orario");
		List<Prenotazione> prenotazioni = repo_pren.findByCodiceEvento(codice_evento_fixed);
		for(Prenotazione prenotazione: prenotazioni) {
			if(prenotazione.getCodice_evento() == codice_evento_fixed) {
				repo_pren.updateOrarioEvento(orario, codice_evento_fixed);
			}
		}
		repo_event.updateOrario(orario, codice_evento_fixed);
		return "evento_modificato";
		}else {
			return "pagina_inaccessibile";
		}
	}
	
	@PostMapping("/modifica_luogo_evento")
	public String UpdateLuogoEvento(		
		@RequestParam Map<String,String> requestParams, HttpServletRequest request) throws Exception {
		if (request.isUserInRole("ROLE_GESTORE")) {
		String codice_evento = requestParams.get("codice_evento");
		Long codice_evento_fixed = Long.parseLong(codice_evento, 10);
		String luogo = requestParams.get("luogo");
		List<Prenotazione> prenotazioni = repo_pren.findByCodiceEvento(codice_evento_fixed);
		for(Prenotazione prenotazione: prenotazioni) {
			if(prenotazione.getCodice_evento() == codice_evento_fixed) {
				repo_pren.updateLuogoEvento(luogo, codice_evento_fixed);
			}
		}
		repo_event.updateLuogo(luogo, codice_evento_fixed);
		return "evento_modificato";
		}else {
			return "pagina_inaccessibile";
		}
	}
	
	@PostMapping("/modifica_num_posti_evento")
	public String UpdateNumPostiEvento(		
		@RequestParam Map<String,String> requestParams, HttpServletRequest request) throws Exception {
		if (request.isUserInRole("ROLE_GESTORE")) {
		String codice_evento = requestParams.get("codice_evento");
		Long codice_evento_fixed = Long.parseLong(codice_evento, 10);
		String num_posti_disponibili = requestParams.get("num_posti_disponibili");
		Long num_posti_disponibili_fixed = Long.parseLong(num_posti_disponibili, 10);
		repo_event.updateNumPosti(num_posti_disponibili_fixed, codice_evento_fixed);
		return "evento_modificato";
		}else {
		return "pagina_inaccessibile";
		}
	}

}