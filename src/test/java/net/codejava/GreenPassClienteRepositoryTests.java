package net.codejava;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
public class GreenPassClienteRepositoryTests {

	@Autowired
	private GreenPassClienteRepository repo_gp_client;
	
	@Autowired
	private GreenPassRepository repo_gp;
	
	@Autowired
	private ClienteRepository repo_client;
	
	@Test
	public void testCreateGreenPassCliente( ) {
		//Precondizione: il cliente deve essere registrato alla piattaforma
		String email_cliente = "ang.casciello@gmail.com";
		Cliente cliente = repo_client.findByEmail(email_cliente);
		//Variabili che identificano il green pass cliente
		String Codice_fiscale = cliente.getCodice_fiscale();
		String Uci_gp = "ZUQ2FPDLQYQM3RCHIXLZ44X03GQ24DWGJ9GYP3";
		//Codice indipendente dalle variabili
		GreenPass existingGreenPass = repo_gp.findByUciGpAndCodiceFiscale(Codice_fiscale, Uci_gp);
		assertNotNull(existingGreenPass);		//controlla che il green pass reale esista e corrisponda al codice fiscale del cliente
		String Data_scadenza = existingGreenPass.getData_scadenza();
		GreenPassCliente green_pass_cliente = new GreenPassCliente();
		green_pass_cliente.setUci_gp(Uci_gp);
		green_pass_cliente.setData_scadenza(Data_scadenza);
		green_pass_cliente.setCodice_fiscale(Codice_fiscale);
		repo_gp_client.save(green_pass_cliente);

	}
	
	@Test
	public void testDeleteGreenPassCliente( ) {
		//Precondizione: il GP cliente deve essere presente nella piattaforma
		String email_cliente = "ang.casciello@gmail.com";
		Cliente cliente = repo_client.findByEmail(email_cliente);
		String Codice_fiscale = cliente.getCodice_fiscale();
		repo_gp_client.deleteByCodiceFiscale(Codice_fiscale);
	}
	
}
