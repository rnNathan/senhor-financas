package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.time.LocalDate;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import model.vo.ReceitaVO;

public class ReceitaDAO {

	public boolean verificarCadastroReceitaBaseDadosDAO(ReceitaVO receitaVO) {

		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		ResultSet resultado = null;
		boolean retorno = false;

		String query = "SELECT idreceita FROM receita WHERE idreceita = " + receitaVO.getIdReceita();

		try {
			resultado = stmt.executeQuery(query);
			if (resultado.next()) {
				retorno = true;
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());

		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);

		}

		return retorno;
	}

	public ReceitaVO cadastrarReceitaDAO(ReceitaVO receitaVO) {

		String query = "insert into receita (idusuario, descricao, valor, datareceita) values (?, ?, ?, ?)";
		Connection conn = Banco.getConnection();
		PreparedStatement pstmt = Banco.getPreparedStatementWithPk(conn, query);

		try {
			pstmt.setInt(1, receitaVO.getIdUsuario());
			pstmt.setString(2, receitaVO.getDescricao());
			pstmt.setDouble(3, receitaVO.getValor());
			if(receitaVO.getDataReceita() != null) {
			pstmt.setObject(4, receitaVO.getDataReceita());
			}
			pstmt.execute();

			ResultSet resultado = pstmt.getGeneratedKeys();
			if (resultado.next()) {
				receitaVO.setIdReceita(resultado.getInt(1));
			}
			
		} catch (SQLException e) {
			System.out.println("Erro: cadastrarReceitaDAO");

		} finally {
			Banco.closeStatement(pstmt);
			Banco.closeConnection(conn);
		}

		return receitaVO;
	}

	public ArrayList<ReceitaVO> consultarTodasReceitasDAO(int idUsu) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		ResultSet resultado = null;
		ArrayList<ReceitaVO> listaReceita = new ArrayList<>();
		String query = "select idreceita, idusuario, descricao, valor, datareceita from receita where idusuario="
				+ idUsu;
		try {
			resultado = stmt.executeQuery(query);
			while (resultado.next()) {

				ReceitaVO receita = new ReceitaVO();

				receita.setIdReceita(Integer.parseInt(resultado.getString(1)));
				receita.setIdUsuario(Integer.parseInt(resultado.getString(2)));
				receita.setDescricao(resultado.getString(3));
				receita.setValor(resultado.getDouble(4));
				receita.setDataReceita(LocalDate.parse(resultado.getString(5)));
				listaReceita.add(receita);
			}

		} catch (SQLException e) {

			System.out.println("Erro: consultarTodasReceitas");
			System.out.println("Erro: " + e.getMessage());
		} finally {

			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);

		}
		return listaReceita;
	}

	public ReceitaVO consultarReceitaDAO(int idReceita) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		ResultSet resultado = null;

		ReceitaVO receita = new ReceitaVO();
		String query = "select idreceita, idusuario, descricao, valor, datareceita from receita where idreceita = "
				+ idReceita;

		try {
			resultado = stmt.executeQuery(query);
			if (resultado.next()) {
				receita.setIdReceita(resultado.getInt(1));
				receita.setIdUsuario(resultado.getInt(2));
				receita.setDescricao(resultado.getString(3));
				receita.setValor(resultado.getDouble(4));
				receita.setDataReceita(LocalDate.parse(resultado.getString(5)));
			}

		} catch (SQLException e) {
			System.out.println("\nErro: consultarReceitaDAO");
			System.out.println("\nErro: " + e.getMessage());

		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}

		return receita;
	}

	public boolean atualizarReceitaDAO(ReceitaVO receitaVO) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		

		boolean retorno = false;
		String query = "UPDATE receita SET descricao = '" + receitaVO.getDescricao() + "', dataReceita = '"
				+ receitaVO.getDataReceita() + "', valor = " + receitaVO.getValor() + " where idreceita = "
				+ receitaVO.getIdReceita();

		try {
			if (stmt.executeUpdate(query) == 1) {
				retorno = true;

			}

		} catch (SQLException e) {
			System.out.println("Erro: atualizarReceitaDAO");
			System.out.println("Erro: " + e.getMessage());

		} finally {
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}

		return retorno;
	}

	public Boolean excluirReceitaDAO(int idreceita) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		
		boolean retorno = false;
		String query = "delete from receita where idreceita = " + idreceita;
		try {
			if (stmt.executeUpdate(query) == 1) {
				retorno = true;
			}
		} catch (SQLException e) {
			System.out.println("Erro: excluirReceitaDAO");
			System.out.println("erro: " + e.getMessage());
		} finally {
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return retorno;
	}

}
