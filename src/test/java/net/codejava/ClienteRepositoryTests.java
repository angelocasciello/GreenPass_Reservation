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
public class ClienteRepositoryTests {

	@Autowired
	private ClienteRepository repo_client;
	
	@Test
	public void testCreateCliente( ) {
		//Variabili che identificano il cliente
		String Email = "ang.casciello@gmail.com";
		//N.B. la password è codificata mediante la funzione di hashing crittografica "bcrypt";
		//il motivo di ciò è che Spring Security non consente la memorizzazione di password in chiaro.
		//Per codificare una password con questa tecnica recarsi nella classe "PasswordEncoder.java" del package src/main/java,
		//inserire la password in chiaro nella stringa "rawPassword" ed eseguire la classe. La password codificata verrà stampata a video.
		String Password = "$2a$12$GDzSYMCgZ3oU55YsEb09a.OhfNRG3m5gGgsGG14Afx1FLfMg8a70q"; 
		String Nome = "Angelo";
		String Cognome = "Casciello";
		String Data_nascita = "1998-01-01";
		String Luogo_nascita ="Napoli";
		Long Num_telefono = 3331116423L;
		String Codice_fiscale = "CCCAAA98G01F123Y";
		//Codice indipendente dalle variabili
		Cliente cliente = new Cliente();
		cliente.setEmail(Email);
		cliente.setPassword(Password);
		cliente.setNome(Nome);
		cliente.setCognome(Cognome);
		cliente.setData_nascita(Data_nascita);
		cliente.setLuogo_nascita(Luogo_nascita);
		cliente.setNum_telefono(Num_telefono);
		cliente.setCodice_fiscale(Codice_fiscale);
		Cliente ClienteSameEmail = repo_client.findByEmail(Email);
		Cliente ClienteSameCF = repo_client.findByCf(Codice_fiscale);
		assertNull(ClienteSameEmail);	//verifica che non esista un cliente con stessa email
		assertNull(ClienteSameCF);		//verifica che non esista un cliente con stesso codice fiscale
		repo_client.save(cliente);
	}
	
}
