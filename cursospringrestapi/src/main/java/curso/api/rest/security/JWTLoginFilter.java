package curso.api.rest.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;

import curso.api.rest.model.Usuario;


/*Estabelece nosso gerenciador de TOKEN*/
public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter{

	/*Configurando o gerenciador de autenticacao*/
	protected JWTLoginFilter(String url, AuthenticationManager authenticationManager) {
		
		/*Obrigramos a autenticar URL*/
		super(new AntPathRequestMatcher(url));
		
		/*Gerenciador de autenticacao*/
		setAuthenticationManager(authenticationManager);
	}

	/*Retorna user ao processar autenticaçao*/
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {

		/*Esta pegando o token para validar*/
		Usuario user = new ObjectMapper().
				readValue(request.getInputStream(), Usuario.class);

		/*Retorna o usuario login, senha e acesso*/
		return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(user.getLogin(), user.getSenha()));
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		
		new JWTTokenAutenticacaoService().addAuthentication(response, authResult.getName());
		
		
	}
	
	
}
