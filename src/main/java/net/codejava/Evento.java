package net.codejava;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "evento")
public class Evento {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codice_evento;
	
	@Column(nullable = false, length = 50)
	private String nome;
	
	@Column(nullable = false, length = 10)
	private String data;
	
	@Column(nullable = false, length = 5)
	private String orario;
	
	@Column(nullable = false, length = 50)
	private String luogo;
	
	@Column(nullable = false, length = 30)
	private Long num_posti_disponibili;

	@Column(nullable = false, length = 50)
	private String email_gestore;
	
	@Column(nullable = false, length = 50)
	private String organizzazione;

	public Long getCodice_evento() {
		return codice_evento;
	}

	public String getOrganizzazione() {
		return organizzazione;
	}

	public void setOrganizzazione(String organizzazione) {
		this.organizzazione = organizzazione;
	}

	public void setCodice_evento(Long codice_evento) {
		this.codice_evento = codice_evento;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getLuogo() {
		return luogo;
	}

	public void setLuogo(String luogo) {
		this.luogo = luogo;
	}

	public Long getNum_posti_disponibili() {
		return num_posti_disponibili;
	}

	public void setNum_posti_disponibili(Long num_posti_disponibili) {
		this.num_posti_disponibili = num_posti_disponibili;
	}
	
	public String getEmail_gestore() {
		return email_gestore;
	}

	public void setEmail_gestore(String email_gestore) {
		this.email_gestore = email_gestore;
	}

	public String getOrario() {
		return orario;
	}

	public void setOrario(String orario) {
		this.orario = orario;
	}


}
