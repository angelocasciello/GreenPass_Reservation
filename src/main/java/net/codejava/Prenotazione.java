package net.codejava;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "prenotazione")
public class Prenotazione {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codice_prenotazione;
	
	@Column(nullable = false, length = 50)
	private String email_cliente;
	
	@Column(nullable = false, length = 50)
	private String nome_cliente;
	
	@Column(nullable = false, length = 50)
	private String cognome_cliente;
	
	@Column(nullable = false, length = 10)
	private Long num_telefono_cliente;
	
	@Column(nullable = false, length = 50)
	private String nome_evento;
	
	@Column(nullable = false, length = 50)
	private String luogo_evento;

	@Column(nullable = false, length = 10)
	private String data_evento;
	
	@Column(nullable = false, length = 5)
	private String orario_evento;

	@Column(nullable = false)
	private Long codice_evento;
	
	@Column(nullable = false, length = 50)
	private String organizzazione;

	public Long getCodice_prenotazione() {
		return codice_prenotazione;
	}

	public void setCodice_prenotazione(Long codice_prenotazione) {
		this.codice_prenotazione = codice_prenotazione;
	}

	public String getOrganizzazione() {
		return organizzazione;
	}

	public void setOrganizzazione(String organizzazione) {
		this.organizzazione = organizzazione;
	}

	public String getEmail_cliente() {
		return email_cliente;
	}


	public void setEmail_cliente(String email_cliente) {
		this.email_cliente = email_cliente;
	}

	public String getNome_cliente() {
		return nome_cliente;
	}

	public void setNome_cliente(String nome_cliente) {
		this.nome_cliente = nome_cliente;
	}

	public String getCognome_cliente() {
		return cognome_cliente;
	}

	public void setCognome_cliente(String cognome_cliente) {
		this.cognome_cliente = cognome_cliente;
	}

	public Long getNum_telefono_cliente() {
		return num_telefono_cliente;
	}

	public void setNum_telefono_cliente(Long num_telefono_cliente) {
		this.num_telefono_cliente = num_telefono_cliente;
	}

	public String getNome_evento() {
		return nome_evento;
	}

	public void setNome_evento(String nome_evento) {
		this.nome_evento = nome_evento;
	}

	public String getData_evento() {
		return data_evento;
	}

	public void setData_evento(String data_evento) {
		this.data_evento = data_evento;
	}
	
	public String getLuogo_evento() {
		return luogo_evento;
	}

	public void setLuogo_evento(String luogo_evento) {
		this.luogo_evento = luogo_evento;
	}


	public Long getCodice_evento() {
		return codice_evento;
	}

	public void setCodice_evento(Long codice_evento) {
		this.codice_evento = codice_evento;
	}
	
	
	public String getOrario_evento() {
		return orario_evento;
	}

	public void setOrario_evento(String orario_evento) {
		this.orario_evento = orario_evento;
	}

	
}
