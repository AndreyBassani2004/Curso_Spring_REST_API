package curso.api.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController//Arquitetura REST
@RequestMapping(value = "/usuario")
public class IndexController {

	//Serviço RESTFul
	@GetMapping(value = "/", produces = "application/json")
	public ResponseEntity init(@RequestParam (value="nome", required = true, defaultValue = "?") String nome, @RequestParam (value="salario", required = true, defaultValue = "0") Long Salario) {
		return new ResponseEntity("Olá Usuário REST Spring Boot " + nome +" salario " + Salario, HttpStatus.OK);
	}
	

}
