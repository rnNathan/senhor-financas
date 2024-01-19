package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.dto.RelatorioDTO;

public class RelatorioDAO {

	public List<RelatorioDTO> consultarRelatorioDAO(int idUsuario, int ano) {
	    List<RelatorioDTO> listaMeses = new ArrayList<>();
	    String query = "SELECT 'Receita' AS tipo, MONTH(datareceita) AS mes, YEAR(datareceita) AS ano, SUM(valor) AS total " +
	                   "FROM receita WHERE idusuario = ? AND YEAR(datareceita) = ? " +
	                   "GROUP BY YEAR(datareceita), MONTH(datareceita) " +
	                   "UNION ALL " +
	                   "SELECT 'Despesa' AS tipo, MONTH(datapagamento) AS mes, YEAR(datapagamento) AS ano, SUM(valor) AS total " +
	                   "FROM despesa WHERE idusuario = ? AND YEAR(datapagamento) = ? AND datapagamento IS NOT NULL " +
	                   "GROUP BY YEAR(datapagamento), MONTH(datapagamento);";

	    try (Connection conn = Banco.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(query)) {

	        pstmt.setInt(1, idUsuario);
	        pstmt.setInt(2, ano);
	        pstmt.setInt(3, idUsuario);
	        pstmt.setInt(4, ano);

	        try (ResultSet resultado = pstmt.executeQuery()) {
	            while (resultado.next()) {
	                RelatorioDTO mes = new RelatorioDTO();
	                mes.setAno(resultado.getInt("ano"));
	                mes.setTipo(resultado.getString("tipo"));
	                mes.setTotal(resultado.getDouble("total"));
	                listaMeses.add(mes);
	            }
	        }

	    } catch (SQLException e) {
	        System.out.println("Erro ao executar a query do método consultarRelatorioDAO");
	        System.out.println("Erro: " + e.getMessage());
	    }

	    return listaMeses;
	}

}
