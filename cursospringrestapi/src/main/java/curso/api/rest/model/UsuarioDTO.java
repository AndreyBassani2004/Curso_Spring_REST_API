package curso.api.rest.model;

import java.io.Serializable;

public class UsuarioDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String userLogin;
	private String userName;
	private String userCpf;
	
	public UsuarioDTO(Usuario usuario) {
		
		this.userLogin = usuario.getLogin();
		this.userName = usuario.getNome();
		this.userCpf = usuario.getCpf();
		
	}
	
	public String getUserLogin() {
		return userLogin;
	}
	public void setUserLogin(String userLogin) {
		this.userLogin = userLogin;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserCpf() {
		return userCpf;
	}
	public void setUserCpf(String userCpf) {
		this.userCpf = userCpf;
	}
	
	
	
}
