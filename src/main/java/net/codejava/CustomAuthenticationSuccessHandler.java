package net.codejava;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;


@Configuration
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {


    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {

    	boolean gestore = false;
    	boolean cliente = false;
    	
        for (GrantedAuthority auth : authentication.getAuthorities()) {
            if ("ROLE_GESTORE".equals(auth.getAuthority())){
            	gestore = true;
            }
            else if ("ROLE_CLIENTE".equals(auth.getAuthority())){
            	cliente = true;
            }
        }
        
        if(gestore){
        	 httpServletResponse.sendRedirect("/dashboard_gestore");
        }else if(cliente){
        	 httpServletResponse.sendRedirect("/dashboard_cliente");
        }
	}
}