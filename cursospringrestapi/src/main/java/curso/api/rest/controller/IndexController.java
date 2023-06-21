package curso.api.rest.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import curso.api.rest.model.Usuario;

@RestController//Arquitetura REST
@RequestMapping(value = "/usuario")
public class IndexController {

	//Serviço RESTFul
	@GetMapping(value = "/", produces = "application/json")
	public ResponseEntity<Usuario> init() {
		
		Usuario usuario = new Usuario();
		usuario.setId(90l);
		usuario.setLogin("abc@email.com");
		usuario.setNome("abc");
		usuario.setSenha("123");
		
		Usuario usuario2 = new Usuario();
		usuario2.setId(91l);
		usuario2.setLogin("abc2@email.com");
		usuario2.setNome("abc2");
		usuario2.setSenha("123");
		
		List<Usuario> usuarios = new ArrayList<Usuario>();
		usuarios.add(usuario);
		usuarios.add(usuario2);
		
		return new ResponseEntity(usuarios, HttpStatus.OK);
	}
	

}
