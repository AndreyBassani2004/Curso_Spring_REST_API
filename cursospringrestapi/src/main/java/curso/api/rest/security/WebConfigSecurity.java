package curso.api.rest.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import curso.api.rest.service.ImplementacaoUserDatailsService;

@Configuration
@EnableWebSecurity
public class WebConfigSecurity extends WebSecurityConfigurerAdapter{

	@Autowired
	private ImplementacaoUserDatailsService implementacaoUserDatailsService;
	
	/*Configura as solicitaçoes de acesso por http*/
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		/*Ativando a protecaçao contra usuarios nao estao validados por token*/
		http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
		
		/*Ativando a permição para pagina index*/
		.disable().authorizeRequests().antMatchers("/").permitAll()
		.antMatchers("/index").permitAll()
		
		/*URL de logout - redireciona apos o user deslogar do sistema*/
		.anyRequest().authenticated().and().logout().logoutSuccessUrl("/index")
		
		/*Mapeia URL de logout e invalidader usuário*/
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
		
		/*Filtra requisições de login para autenticação*/
		.and().addFilterBefore(new JWTLoginFilter("/login", authenticationManager()), UsernamePasswordAuthenticationFilter.class)
		
		/*Filtra demais requisiçoes para verificar a preseça do TOKEN JWT no HEADER HTTP*/
		.addFilterBefore(new JwtApiAutenticacaoFilter(), UsernamePasswordAuthenticationFilter.class);
		
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		
		/*Service que ira consultar usuario no banco de dados*/
		auth.userDetailsService(implementacaoUserDatailsService)
		
		/*Padrao codificação de senha*/
		.passwordEncoder(new BCryptPasswordEncoder());
		
	}
	
}
