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
public class GreenPassRepositoryTests {

	@Autowired
	private GreenPassRepository repo_gp;
	
	@Autowired
	private GreenPassClienteRepository repo_gp_client;
	
	@Test
	public void testCreateGreenPass( ) {
		//Variabili che identificano il green pass
		/*IMPORTANTE -> per riempire il db dei Green Pass eseguire le seguenti operazioni:
		- modificare le variabili sottostanti, inserendo i dati del GP del cittadino che si intende memorizzare
		- runnare la classe di test 
		N.B.
		l'uci gp e il codice fiscale devono essere unici per ogni cittadino
		*/
		String Uci_gp = "ZUQ2FPDLQYQM3RCHIXLZ44X03GQ24DWGJ9GYP3";
		String codice_fiscale = "CCCAAA98G01F123Y";
		String Data_scadenza = "2030-07-12";
		//Codice indipendente dalle variabili
		GreenPass green_pass = new GreenPass();
		green_pass.setUci_gp(Uci_gp);
		green_pass.setCodice_fiscale(codice_fiscale);
		green_pass.setData_scadenza(Data_scadenza);
		GreenPass existingGreenPass = repo_gp.findByUciGp(Uci_gp);
		assertNull(existingGreenPass);
		repo_gp.save(green_pass);
	}
	
//	@Test
//	public void testUpdateGreenPass( ) {
//		//Precondizione: il GP deve essere presente nel DB dei Green Pass
//		String new_data_scadenza = "2030-07-12";
//		String uci_gp = "01IT1EE7EFDA2870472284E03718D54A296F#9";
//		repo_gp.updateDataScadenza(new_data_scadenza, uci_gp);
//		//Postcondizione: il GP cliente (locale) deve essere anch'esso aggiornato
//		repo_gp_client.updateDataScadenza(new_data_scadenza, uci_gp); //aggiorno anche la copia locale del GP
//		
//	}
	
}
