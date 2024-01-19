package model.bo;

import model.vo.ReceitaVO;

import java.util.ArrayList;

import model.dao.ReceitaDAO;

public class ReceitaBO {

	public ReceitaVO cadastrarReceitaBO(ReceitaVO receitaVO) {
		ReceitaDAO receitaDAO = new ReceitaDAO();
		if (receitaDAO.verificarCadastroReceitaBaseDadosDAO(receitaVO)) {
			System.out.println("Receita já cadastrada na base de dados");
		} else {
			receitaVO = receitaDAO.cadastrarReceitaDAO(receitaVO);
		}

		return receitaVO;
	}

	public ArrayList<ReceitaVO> consultarTodasReceitasBO(int idUsu) {
 		ReceitaDAO receitaDAO = new ReceitaDAO();
		ArrayList<ReceitaVO> listaReceitaBO = receitaDAO.consultarTodasReceitasDAO(idUsu);

		if (listaReceitaBO.isEmpty()) {
			System.out.println("A lista de receita está vazia");
		}

		return listaReceitaBO;

	}

	public ReceitaVO consultarReceitaBO(int idReceita) {

		ReceitaDAO receitaDAO = new ReceitaDAO();
		ReceitaVO receita = receitaDAO.consultarReceitaDAO(idReceita);

		if (receita == null) {
			System.out.println("Receita não encontrada");
		}

		return receita;
	}

	public Boolean atualizarReceitaBO(ReceitaVO receitaVO) {
		boolean resultado = false;
		ReceitaDAO receita = new ReceitaDAO();

		if (receita.verificarCadastroReceitaBaseDadosDAO(receitaVO)) {
			resultado = receita.atualizarReceitaDAO(receitaVO);
		} else {
			System.out.println("Não existe essa receita no banco.");

		}

		return resultado;
	}

		public Boolean excluirReceitaBO(ReceitaVO receitaVO) {
		ReceitaDAO receitaDAO = new ReceitaDAO();
		boolean resultado = false;
		int delete = receitaVO.getIdReceita();
		if (receitaDAO.verificarCadastroReceitaBaseDadosDAO(receitaVO)) {
			resultado = receitaDAO.excluirReceitaDAO(delete);
		} else {
			System.out.println("receita não existe!");
		}
		return resultado;
	}
}
