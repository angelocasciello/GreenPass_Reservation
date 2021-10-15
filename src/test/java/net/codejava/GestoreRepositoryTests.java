package net.codejava;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

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
public class GestoreRepositoryTests {

	@Autowired
	private GestoreRepository repo_gest;
	
	@Test
	public void testCreateGestore( ) {
		//Variabili che identificano il gestore
		String Email = "vivaticket@gmail.com";
		//N.B. la password è codificata mediante la funzione di hashing crittografica "bcrypt";
		//il motivo di ciò è che Spring Security non consente la memorizzazione di password in chiaro.
		//Per codificare una password con questa tecnica recarsi nella classe "PasswordEncoder.java" del package src/main/java,
		//inserire la password in chiaro nella stringa "rawPassword" ed eseguire la classe. La password codificata verrà stampata a video.
		String Password = "$2a$12$03YCiRkAjvE7orsWnLti8uFzLVNix3nNC.zupHCQ547yzDxevl/TW";
		String Nome = "Mario";
		String Cognome = "Rossi";
		String Organizzazione = "Vivaticket";
		String Data_nascita = "1980-07-10";
		String Luogo_nascita = "Milano";
		Long Num_telefono = 3331112244L;
		Long Partita_iva = 12345678912L;
		//Codice indipendente dalle variabili
		Gestore gestore = new Gestore();
		gestore.setEmail(Email);
		gestore.setPassword(Password);
		gestore.setNome(Nome);
		gestore.setCognome(Cognome);
		gestore.setOrganizzazione(Organizzazione);
		gestore.setData_nascita(Data_nascita);
		gestore.setLuogo_nascita(Luogo_nascita);
		gestore.setNum_telefono(Num_telefono);
		gestore.setPartita_iva(Partita_iva);
		Gestore GestoreSameEmail = repo_gest.findByEmail(Email);
		Gestore GestoreSamePartitaIva = repo_gest.findByPartitaIva(Partita_iva);
		Gestore GestoreSameOrganizzazione = repo_gest.findByOrganizzazione(Organizzazione);
		assertNull(GestoreSameEmail);			//verifica che non esista un gestore con stessa email
		assertNull(GestoreSamePartitaIva);		//verifica che non esista un gestore con stessa partita iva
		assertNull(GestoreSameOrganizzazione);	//verifica che non esista un gestore con stessa organizzazione
		repo_gest.save(gestore);
	}
	
}