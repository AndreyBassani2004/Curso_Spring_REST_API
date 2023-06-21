package curso.api.rest.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import curso.api.rest.model.Usuario;
import curso.api.rest.repositoy.UsuarioRepository;

@RestController//Arquitetura REST
@RequestMapping(value = "/usuario")
public class IndexController {

	@Autowired //se fosse CDI seria @Inject
	private UsuarioRepository usuarioRepository;
	
	//Serviço RESTFul
		@GetMapping(value = "/{id}/codvenda/{venda}", produces = "application/json")
		public ResponseEntity<Usuario> relatorio(@PathVariable (value = "id") Long id,
				@PathVariable (value = "venda") Long venda) {
			
		Optional<Usuario> usuarios = usuarioRepository.findById(id);
			
		return new ResponseEntity("RELATORIO: PDF : " + usuarios + " COD " + venda, HttpStatus.OK);
		}
	
	//Serviço RESTFul
	@GetMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity<Usuario> init(@PathVariable (value = "id") Long id) {
		
	Optional<Usuario> usuarios = usuarioRepository.findById(id);
		
		return new ResponseEntity(usuarios, HttpStatus.OK);
	}
	
	
	@GetMapping(value = "/", produces = "application/json")
	public ResponseEntity<List<Usuario>> usuario (){
		
		List<Usuario> list = (List<Usuario>) usuarioRepository.findAll();
		
		return new ResponseEntity<List<Usuario>>(list, HttpStatus.OK);
		
	}
	

}
