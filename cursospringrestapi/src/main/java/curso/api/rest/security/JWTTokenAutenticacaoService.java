package curso.api.rest.security;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import curso.api.rest.ApplicationContextLoad;
import curso.api.rest.model.Usuario;
import curso.api.rest.repositoy.UsuarioRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
@Component
public class JWTTokenAutenticacaoService {

	/*Tempo de validade do TOKEN (2 Dias)*/
	private static final long EXPIRATION_TIME = 172800000;
	
	/*Senha unica para compor a autenticação e ajudar na segurança */
	private static final String SECRET = "SenhaExtremamenteSecreta";
	
	/*Prrefixo padrao de TOKEN*/
	private static final String TOKEN_PREFIX = "Bearer";
	
	/**/
	private static final String HEADER_STRING = "Authorization";
	
	/*Gerando token de autenticação e adicionar ao cabeçalho e resposta HTTP*/
	public void addAuthentication(HttpServletResponse response, String username) throws IOException{
		
	/*Montagem do TOKEN*/
	String JWT = Jwts.builder()/*Chama o gerador de TOKEN*/
				.setSubject(username) /*Adiciona o usuario*/
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))/*Tempo de expiração*/
				.signWith(SignatureAlgorithm.HS512, SECRET).compact();/*Compactação e geração de senha*/
	
	/*Junto o TOKEN com prefixo.*/
	String token = TOKEN_PREFIX + "" + JWT;
	
	/*Adiciona cabeçalho http*/
	response.addHeader(HEADER_STRING, token);/*Athorization: Baerer hkjbnfjgdjklfgljkdf*/
	
	/*Escreve TOKEN como resposta no HTTP*/
	response.getWriter().write("{\"Athorization\": \""+token+"\"}");	
	
	}
	
	/*Retorna o usuario validado com token ou caso nao seja valido retorna null*/
	public Authentication getAuthentication(HttpServletRequest request) {
		
		/*Pega o TOKEN enviado no cabecalho HTTP*/
		String token = request.getHeader(HEADER_STRING);
		
		
		if(token != null) {
			
			/*Faz a validação do token do usuario na requisição*/
			String user = Jwts.parser().setSigningKey(SECRET)
					.parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
					.getBody().getSubject();/*User*/
			if(user != null) {
				
				Usuario usuario = ApplicationContextLoad.getApplicationContext().getBean(UsuarioRepository.class).findUserByLogin(user);
				
				/*Retornar user logado*/
				if(usuario != null) {
					
					return new UsernamePasswordAuthenticationToken(usuario.getLogin(), usuario.getSenha(), usuario.getAuthorities());
					
				}
				
			}
			
		}
			
		
		return null; /*Nao autorizado*/
				
	}
	
}
