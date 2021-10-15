package net.codejava;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private ClienteRepository repo_client;
	
	@Autowired
	private GestoreRepository repo_gest;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Cliente cliente = repo_client.findByEmail(email);
		if (cliente == null) {
			Gestore gestore = repo_gest.findByEmail(email);
			if(gestore == null) {
				throw new UsernameNotFoundException("Username non trovato");
			}
			return new CustomGestoreDetails(gestore);
			}
		return new CustomClienteDetails(cliente);
	}

}
