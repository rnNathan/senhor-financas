package controller;


import java.util.List;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import model.dao.RelatorioDAO;
import model.dto.RelatorioDTO;

@Path("/relatorio")
public class RelatorioRest {
	
	@GET
	@Path("/listar/{ano}/{idusuario}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public List <RelatorioDTO> consultarRelatorioController (@PathParam ("ano") int ano, @PathParam("idusuario") int idusuario){
		RelatorioDAO relatorio = new RelatorioDAO();
		return relatorio.consultarRelatorioDAO(ano, idusuario);
	}
	
}
