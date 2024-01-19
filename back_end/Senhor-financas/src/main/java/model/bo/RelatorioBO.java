package model.bo;


import java.util.List;

import model.dao.RelatorioDAO;
import model.dto.RelatorioDTO;


public class RelatorioBO {
	
	public List<RelatorioDTO> consultarRelatorioBO(int idUsu, int ano) {
		RelatorioDAO relatorio = new RelatorioDAO();
		List<RelatorioDTO> relatorioTotal = relatorio.consultarRelatorioDAO(idUsu, ano);

		if (relatorioTotal.isEmpty()) {
			System.out.println("A lista de receita está vazia");
		}

		return relatorioTotal;

	}


}
