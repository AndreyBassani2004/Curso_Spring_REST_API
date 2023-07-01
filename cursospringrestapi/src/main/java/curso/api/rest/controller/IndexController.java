package curso.api.rest.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import curso.api.rest.model.Usuario;
import curso.api.rest.repositoy.UsuarioRepository;

@CrossOrigin(origins = "*")
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
			
		return new ResponseEntity(usuarios, HttpStatus.OK);
		}
		
	
	//Serviço RESTFul
	@GetMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity<Usuario> init(@PathVariable (value = "id") Long id) {
		
	Optional<Usuario> usuarios = usuarioRepository.findById(id);
		
		return new ResponseEntity(usuarios, HttpStatus.OK);
	}
	
	//Serviço RESTFul header
	@GetMapping(value = "/{id}", produces = "application/json",  headers = "X-API-Version=V3")
	public ResponseEntity<Usuario> initheader(@PathVariable (value = "id") Long id) {
			
	Optional<Usuario> usuarios = usuarioRepository.findById(id);
	System.out.println("V3 HEADER");
			
	return new ResponseEntity(usuarios, HttpStatus.OK);
	}
	
	//Serviço RESTFul v1
		@GetMapping(value = "v1/{id}", produces = "application/json")
		public ResponseEntity<Usuario> init1(@PathVariable (value = "id") Long id) {
			
		Optional<Usuario> usuarios = usuarioRepository.findById(id);
			System.out.println("V1");
			return new ResponseEntity(usuarios, HttpStatus.OK);
		}
		
		//Serviço RESTFul v2
		@GetMapping(value = "v2/{id}", produces = "application/json")
		public ResponseEntity<Usuario> init2(@PathVariable (value = "id") Long id) {
					
		Optional<Usuario> usuarios = usuarioRepository.findById(id);
		System.out.println("V2");
	
		return new ResponseEntity(usuarios, HttpStatus.OK);
		}
	
	
	@DeleteMapping(value = "/{id}", produces = "application/text")
	public String delete(@PathVariable("id") Long id) {
		
		usuarioRepository.deleteById(id);
		
		return "OK";
	}
	
	
	@GetMapping(value = "/", produces = "application/json")
	public ResponseEntity<List<Usuario>> usuario (){
		
		List<Usuario> list = (List<Usuario>) usuarioRepository.findAll();
		
		return new ResponseEntity<List<Usuario>>(list, HttpStatus.OK);
		
	}
	
	
	@PostMapping(value="/", produces = "application/json")
	public ResponseEntity<Usuario> cadastrar(@RequestBody Usuario usuario){
		
		for(int pos = 0; pos < usuario.getTelefones().size(); pos ++) {
			usuario.getTelefones().get(pos).setUsuario(usuario);
		}
		
		String senhacriptografada = new BCryptPasswordEncoder().encode(usuario.getSenha());
		usuario.setSenha(senhacriptografada);
		
		Usuario usuarioSalvo = usuarioRepository.save(usuario);
		
		return new ResponseEntity<Usuario>(usuarioSalvo, HttpStatus.OK);
	}
	
	
	@PutMapping(value="/", produces = "application/json")
	public ResponseEntity<Usuario> atualizar(@RequestBody Usuario usuario){
		
		//Outras rotinas antes de atualizar
		for(int pos = 0; pos < usuario.getTelefones().size(); pos ++) {
			usuario.getTelefones().get(pos).setUsuario(usuario);
		}
		
		Usuario usertempo = usuarioRepository.findUserByLogin(usuario.getLogin());
		
		if(!usertempo.getSenha().equals(usuario.getSenha())) {
			/*Senhas diferentes*/
			String senhacriptografada = new BCryptPasswordEncoder().encode(usuario.getSenha());
			usuario.setSenha(senhacriptografada);
		}
		
		Usuario usuarioSalvo = usuarioRepository.save(usuario);
		
		return new ResponseEntity<Usuario>(usuarioSalvo, HttpStatus.OK);
	}
	
	@PostMapping(value="/{iduser}/idvenda/{idvenda}", produces = "application/json")
	public ResponseEntity cadastrarvenda(@PathVariable Long iduser, @PathVariable Long idvenda){
		
		//Seria o processo da venda
		//Usuario usuarioSalvo = usuarioRepository.save(usuario);
		
		return new ResponseEntity("id user :" +iduser+ ". idvenda"+ idvenda, HttpStatus.OK);
	}

}
