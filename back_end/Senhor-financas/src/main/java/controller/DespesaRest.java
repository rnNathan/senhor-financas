 package controller;

import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.PUT;
import java.util.List;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.MediaType;
import model.bo.DespesaBO;
import model.vo.DespesaVO;

@Path("/despesa")
public class DespesaRest {
 
	@POST
	@Path("/cadastrar")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public DespesaVO cadastrarDespesaController(DespesaVO despesaVO) {
		DespesaBO despesaV = new DespesaBO();
		return despesaV.cadastrarDespesaBO(despesaVO);
	}
 
	@GET
	@Path("/listar/{idusuario}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<DespesaVO> consultarTodasDespesaController(@PathParam("idusuario") int idusuario) {
		DespesaBO despesa = new DespesaBO();
		return despesa.consultarTodasDespesaBO(idusuario);
	}
 
	@GET
	@Path("/pesquisar/one/{iddespesa}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public DespesaVO consultarReceitaController(@PathParam("iddespesa") int iddespesa) {
		DespesaBO despesa = new DespesaBO();
		return despesa.consultarDespesaBO(iddespesa);
	}
 
	@PUT
	@Path("/atualizar")
	@Consumes(MediaType.APPLICATION_JSON)
	public Boolean atualizarDespesaController(DespesaVO despesavo) {
		DespesaBO despesa = new DespesaBO();
		return despesa.atualizarDespesaBO(despesavo);
		
	}
 
	@DELETE
	@Path("/excluir")
	@Consumes(MediaType.APPLICATION_JSON)
	public Boolean excluirDespesaController(DespesaVO despesaVO) {
		DespesaBO despesa = new DespesaBO();
		return despesa.excluirDespesaBO(despesaVO);
	}
}
