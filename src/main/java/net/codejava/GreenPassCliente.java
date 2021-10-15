package net.codejava;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "green_pass_cliente")
public class GreenPassCliente {
	
	@Id
	@Column(nullable = false, unique = true, length = 38)
	private String uci_gp;
	
	@Column(nullable = false, length = 15)
	private String data_scadenza;
	
	@Column(nullable = false, unique = true, length = 16)
	private String codice_fiscale;

	public String getCodice_fiscale() {
		return codice_fiscale;
	}

	public void setCodice_fiscale(String codice_fiscale) {
		this.codice_fiscale = codice_fiscale;
	}

	public String getUci_gp() {
		return uci_gp;
	}

	public void setUci_gp(String uci_gp) {
		this.uci_gp = uci_gp;
	}

	public String getData_scadenza() {
		return data_scadenza;
	}

	public void setData_scadenza(String data_scadenza) {
		this.data_scadenza = data_scadenza;
	}
	
}