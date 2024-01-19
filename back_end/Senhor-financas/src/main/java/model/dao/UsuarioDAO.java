package model.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.sql.PreparedStatement;
import model.vo.UsuarioVO;

public class UsuarioDAO {

	public boolean verificarCadastroUsuarioBaseDadosDAO(UsuarioVO usuarioVO) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		ResultSet resultado = null;
		boolean retorno = false;
		String query = "SELECT cpf FROM usuario WHERE cpf = '" + usuarioVO.getCpf() + "'";

		try {
			resultado = stmt.executeQuery(query);
			if (resultado.next()) {
				retorno = true;
			}

		} catch (SQLException e) {
			System.out.println("Erro verificarCadastroUsuarioDAO");
			System.out.println("Erro: " + e.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return retorno;
	}

	public UsuarioVO cadastrarUsuarioDAO(UsuarioVO usuarioVO) {

		String query = "insert into usuario (nome, cpf, email, datanascimento, login, senha) values (?, ?, ?, ?, ?, ?)";
		Connection conn = Banco.getConnection();
		PreparedStatement pstmt = Banco.getPreparedStatementWithPk(conn, query);

		try {

			pstmt.setString(1, usuarioVO.getNome());
			pstmt.setString(2, usuarioVO.getCpf());
			pstmt.setString(3, usuarioVO.getEmail());
			pstmt.setObject(4, usuarioVO.getDataNascimento());
			pstmt.setString(5, usuarioVO.getLogin());
			pstmt.setString(6, usuarioVO.getSenha());
			pstmt.execute();

			ResultSet resultado = pstmt.getGeneratedKeys();
			if (resultado.next()) {
				usuarioVO.setIdUsuario(resultado.getInt(1));
			}

		} catch (SQLException e) {
			System.out.println("Erro: cadastrarUsuarioDAO");
			System.out.println("Erro: " + e.getMessage());

		} finally {

			Banco.closeConnection(conn);
			Banco.closeStatement(pstmt);
		}

		return usuarioVO;
	}

	public UsuarioVO realizarLoginDAO(UsuarioVO usuarioVO) {
		String query = "SELECT idusuario, nome, cpf, email, datanascimento, login, senha FROM usuario WHERE login = '"
				+ usuarioVO.getLogin() + "' AND senha = '" + usuarioVO.getSenha() + "' ";
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		ResultSet resultado = null;
		try {
			resultado = stmt.executeQuery(query);
			if (resultado.next()) {
				usuarioVO.setIdUsuario(resultado.getInt(1));
				usuarioVO.setNome(resultado.getString(2));
				usuarioVO.setCpf(resultado.getString(3));
				usuarioVO.setEmail(resultado.getString(4));
				if (resultado.getString(5) != null) {
					usuarioVO.setDataNascimento(
							LocalDate.parse(resultado.getString(5), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
				}
				usuarioVO.setLogin(resultado.getString(6));
				usuarioVO.setSenha(resultado.getString(7));
			}

		} catch (SQLException e) {
			System.out.println("Erro: realizarLoginDAO");
			System.out.println("Erro: " + e.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);

		}
		return usuarioVO;
	}

}
