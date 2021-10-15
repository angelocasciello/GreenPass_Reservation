package net.codejava;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "gestore")
public class Gestore {

	@Id
	@Column(nullable = false, unique = true, length = 38)
	private Long partita_iva;									//identifica il gestore
	
	@Column(nullable = false, unique = true, length = 50)
	private String email;
	
	@Column(nullable = false, length = 64)
	private String password;
	
	@Column(nullable = false, length = 50)
	private String nome;
	
	@Column(nullable = false, length = 50)
	private String cognome;
	
	@Column(nullable = false, unique = true, length = 50)
	private String organizzazione;
	
	@Column(nullable = false, length = 10)
	private String data_nascita;
	
	@Column(nullable = false, length = 50)
	private String luogo_nascita;
	
	@Column(nullable = false, length = 10)
	private Long num_telefono;	
	
	public Long getPartita_iva() { 				
		return partita_iva;
	}
	public void setPartita_iva(Long partita_iva) {
		this.partita_iva = partita_iva;
	}
	
	public String getData_nascita() {
		return data_nascita;
	}
	public void setData_nascita(String data_nascita) {
		this.data_nascita = data_nascita;
	}
	public String getLuogo_nascita() {
		return luogo_nascita;
	}
	public void setLuogo_nascita(String luogo_nascita) {
		this.luogo_nascita = luogo_nascita;
	}
	public Long getNum_telefono() {
		return num_telefono;
	}
	public void setNum_telefono(Long num_telefono) {
		this.num_telefono = num_telefono;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getOrganizzazione() {
		return organizzazione;
	}
	public void setOrganizzazione(String organizzazione) {
		this.organizzazione = organizzazione;
	}

}
