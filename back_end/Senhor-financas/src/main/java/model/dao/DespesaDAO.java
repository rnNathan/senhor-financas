package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import model.vo.DespesaVO;

public class DespesaDAO {

	public boolean verificarCadastroDespesaDAO(DespesaVO despesaVO) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		ResultSet resultado = null;
		boolean retorno = false;

		String query = "SELECT iddespesa from despesa where iddespesa = " + despesaVO.getIdDespesa();

		try {
			resultado = stmt.executeQuery(query);
			if (resultado.next()) {
				retorno = true;
			}

		} catch (SQLException e) {
			System.out.println("Erro verificarCadastroDespesaDAO");
			System.out.println("Erro: " + e.getMessage());

		} finally {

			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);

		}

		return retorno;
	}

	public DespesaVO cadastrarDespesaDAO(DespesaVO despesaVO) {

		String query = "insert into despesa (idusuario, descricao, valor, datavencimento, datapagamento) values (?, ?, ?, ?, ?)";
		Connection conn = Banco.getConnection();
		PreparedStatement pstmt = Banco.getPreparedStatementWithPk(conn, query);

		try {

			pstmt.setInt(1, despesaVO.getIdUsuario());
			pstmt.setString(2, despesaVO.getDescricao());
			pstmt.setDouble(3, despesaVO.getValor());
			pstmt.setObject(4, despesaVO.getDataVencimento());
			if (despesaVO.getDataPagamento() != null) {
				pstmt.setObject(5, despesaVO.getDataPagamento());
			}
			pstmt.execute();

			ResultSet resultado = pstmt.getGeneratedKeys();
			if (resultado.next()) {
				despesaVO.setIdDespesa(resultado.getInt(1));

			}
		} catch (SQLException e) {
			System.out.println("Erro: cadastrarDespesaDAO");
			System.out.println("Erro: " + e.getMessage());

		} finally {
			Banco.closeStatement(pstmt);
			Banco.closeConnection(conn);

		}
		return despesaVO;
	}

	public ArrayList<DespesaVO> consultarTodasDespesasDAO(int idUsu) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		ResultSet resultado = null;
		ArrayList<DespesaVO> listaDespesa = new ArrayList<DespesaVO>();
		String query = "select iddespesa, idusuario, descricao, valor, datavencimento, datapagamento from despesa where idusuario ="
				+ idUsu;
		try {
			resultado = stmt.executeQuery(query);
			while (resultado.next()) {
				DespesaVO despesa = new DespesaVO();

				despesa.setIdDespesa(resultado.getInt(1));
				despesa.setIdUsuario((resultado.getInt(2)));
				despesa.setDescricao(resultado.getString(3));
				despesa.setValor(resultado.getDouble(4));
				despesa.setDataVencimento(LocalDate.parse(resultado.getString(5), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
				if (resultado.getObject(6) != null) {
					despesa.setDataPagamento(LocalDate.parse(resultado.getString(6), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
				}
				listaDespesa.add(despesa);
			}

		} catch (SQLException e) {
			System.out.println("Erro: consultarTodasDespesasDAO");
			System.out.println("Erro: " + e.getMessage());

		} finally {

			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}

		return listaDespesa;
	}

	public DespesaVO consultarDespesaDAO(int iddespesa) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		ResultSet result = null;
		DespesaVO despesa = new DespesaVO();

		String query = "SELECT iddespesa, idusuario, descricao, valor, datavencimento, datapagamento FROM despesa WHERE iddespesa ="
				+ iddespesa;

		try {
			result = stmt.executeQuery(query);
			if (result.next()) {
				despesa.setIdDespesa(result.getInt(1));
				despesa.setIdUsuario(result.getInt(2));
				despesa.setDescricao(result.getString(3));
				despesa.setValor(result.getDouble(4));
				despesa.setDataVencimento(LocalDate.parse(result.getString(5)));
				if (result.getObject(6) != null) {
					despesa.setDataPagamento(LocalDate.parse(result.getString(6)));
				}
			}
		} catch (SQLException e) {
			System.out.println("Erro: pesquisarDespesaDAO");
			System.out.println("erro: " + e.getMessage());
		} finally {
			Banco.closeResultSet(result);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}

		return despesa;
	}

	public boolean atualizarDespesaDAO(DespesaVO despesaVO) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		boolean resultado = false;

		String query = "UPDATE despesa SET descricao = '" + despesaVO.getDescricao() + "', dataVencimento = '"
				+ despesaVO.getDataVencimento() + "' , dataPagamento = '" + despesaVO.getDataPagamento() + "', valor = "
				+ despesaVO.getValor() + " where iddespesa = "
				+ despesaVO.getIdDespesa();
		try {
			if (stmt.executeUpdate(query) == 1) {
				resultado = true;

			}

		} catch (SQLException e) {
			System.out.println("Erro: atualizarDespesaDAO");
			System.out.println("Erro: " + e.getMessage());

		} finally {
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);

		}

		return resultado;
	}

	public boolean exluirDespesaDAO(DespesaVO despesaVO) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		boolean retorno = false;

		String query = "delete from despesa where iddespesa = " + despesaVO.getIdDespesa();

		try {
			if (stmt.executeUpdate(query) == 1) {
				retorno = true;
			}

		} catch (SQLException e) {
			System.out.println("Erro: exluirDespesaDAO");
			System.out.println("ERRO " + e.getMessage());

		} finally {
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}

		return retorno;
	}

}
