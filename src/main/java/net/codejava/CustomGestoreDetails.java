package net.codejava;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomGestoreDetails implements UserDetails {
	
	private Gestore gestore;
	
	public CustomGestoreDetails(Gestore user) {
		super();
		this.gestore = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();

        list.add(new SimpleGrantedAuthority("ROLE_GESTORE"));

        return list;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return gestore.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return gestore.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	public String getFullName() {
		return gestore.getNome() + " " + gestore.getCognome();
	}
}
