package model.bo;

import model.dao.UsuarioDAO;
import model.vo.UsuarioVO;

public class UsuarioBO {

	public UsuarioVO cadastrarUsuarioBO(UsuarioVO usuarioVO) {

		UsuarioDAO usuarioDAO = new UsuarioDAO();
		if (usuarioDAO.verificarCadastroUsuarioBaseDadosDAO(usuarioVO)) {
			System.out.println("\nPessoa já cadastrada na base dados.");
		} else {
			usuarioVO = usuarioDAO.cadastrarUsuarioDAO(usuarioVO);
		}
		return usuarioVO;
	}

	public UsuarioVO inserirLoginBO(UsuarioVO usuarioVO) {
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		if(usuarioDAO.realizarLoginDAO(usuarioVO).getLogin() != null) {
			return usuarioVO;
		}
		return null;
	}
}
