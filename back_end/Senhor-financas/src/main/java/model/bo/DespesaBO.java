package model.bo;

import java.util.ArrayList;

import model.dao.DespesaDAO;
import model.vo.DespesaVO;

public class DespesaBO {

	public DespesaVO cadastrarDespesaBO(DespesaVO despesaVO) {

		DespesaDAO despesaDAO = new DespesaDAO();

		if (despesaDAO.verificarCadastroDespesaDAO(despesaVO)) {
			System.out.println("Despesa já cadastrado no sistema.");
		} else {
				despesaVO = despesaDAO.cadastrarDespesaDAO(despesaVO);
			}
		return despesaVO;
	}

	public ArrayList<DespesaVO> consultarTodasDespesaBO(int idUsu) {
		DespesaDAO despesaDAO = new DespesaDAO();
		ArrayList<DespesaVO> listaDespesa = despesaDAO.consultarTodasDespesasDAO(idUsu);

		if (listaDespesa.isEmpty()) {
			System.out.println("A lista de despesa está vazia");
		} 

		return listaDespesa;

	}

	public DespesaVO consultarDespesaBO(int idDes) {
		DespesaDAO despesaDAO = new DespesaDAO();

		DespesaVO despesa = despesaDAO.consultarDespesaDAO(idDes);
		if (despesa == null) {
			System.out.println("Despesa não cadastrada!");
		}

		return despesa;
	}

	public boolean atualizarDespesaBO(DespesaVO despesaVO) {
		boolean retorno = false;
		DespesaDAO despesaDAO = new DespesaDAO();
		if (despesaDAO.verificarCadastroDespesaDAO(despesaVO)) {
			retorno = despesaDAO.atualizarDespesaDAO(despesaVO);
		} else {
			System.out.println("Essa despesa não se encontrar no nosso banco de dados.");

		}

		return retorno;
	}

	public Boolean excluirDespesaBO(DespesaVO despesaVO) {
		boolean resultado = false;
		DespesaDAO despesaDAO = new DespesaDAO();

		if (despesaDAO.verificarCadastroDespesaDAO(despesaVO)) {
			resultado = despesaDAO.exluirDespesaDAO(despesaVO);
		} else {
			System.out.println("despesa não cadastrada!");
		}

		return resultado;
	}

}
