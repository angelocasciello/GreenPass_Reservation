package net.codejava;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoder {				//questa classe codifica una password attraverso la funzione di hasing bcrypt

	public static void main(String[] args) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String rawPassword = "abc12345"; //inserire qui la password da codificare
		String encodedPassword = encoder.encode(rawPassword);
		
		System.out.println(encodedPassword);	//verrà stampata a video la password codificata
	}
}
