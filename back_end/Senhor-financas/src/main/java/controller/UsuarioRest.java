package controller;


import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import model.bo.UsuarioBO;
import model.vo.UsuarioVO;


@Path ("/usuario")
public class UsuarioRest {
	
	@POST
	@Path("/cadastrar")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public UsuarioVO cadastrarUsuarioController (UsuarioVO usuarioVO) {
		UsuarioBO usu = new UsuarioBO ();
		return usu.cadastrarUsuarioBO(usuarioVO);
	}
	
	@POST
	@Path("/logar")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public UsuarioVO logarController (UsuarioVO usuarioVO) {
		UsuarioBO usu = new UsuarioBO();
		return usu.inserirLoginBO(usuarioVO);
	}
}
