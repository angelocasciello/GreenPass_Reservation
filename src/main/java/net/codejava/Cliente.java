package net.codejava;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "cliente")
public class Cliente {
	
	@Id
	@Column(nullable = false, unique = true, length = 16)		//identifica il cliente
	private String codice_fiscale;
	
	@Column(nullable = false, unique = true, length = 50)
	private String email;
	
	@Column(nullable = false, length = 64)
	private String password;
	
	@Column(nullable = false, length = 50)
	private String nome;
	
	@Column(nullable = false, length = 50)
	private String cognome;
	
	@Column(nullable = false, length = 10)
	private String data_nascita;
	
	@Column(nullable = false, length = 50)
	private String luogo_nascita;
	
	@Column(nullable = false, length = 10)
	private Long num_telefono;
	
	public String getCodice_fiscale() {
		return codice_fiscale;
	}
	public void setCodice_fiscale(String codice_fiscale) {
		this.codice_fiscale = codice_fiscale;
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

}
