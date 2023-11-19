package br.com.fiap.fintech.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.com.fiap.fintech.bean.Receita;
import br.com.fiap.fintech.dao.ReceitaDAO;
import br.com.fiap.fintech.exception.DBException;
import br.com.fiap.fintech.singleton.ConnectionManager;

public class OracleReceitaDAO implements ReceitaDAO{

	private Connection conexao;

	@Override
	public void insert(Receita receita) throws DBException {
		PreparedStatement stmt = null;

		int proximoValor = 0;
		
		try {
			conexao = ConnectionManager.getInstance().getConnection();
			String sql = "SELECT SQ_TB_FIN_RECEITA.NEXTVAL FROM DUAL";
			
            try (PreparedStatement pstmt = conexao.prepareStatement(sql)) {
                ResultSet rs = pstmt.executeQuery();

                
                if (rs.next()) {
                    proximoValor = rs.getInt(1);
                }
            }
			
			stmt = conexao.prepareStatement(
					"INSERT INTO TB_FIN_RECEITA (CD_RECEITA, VL_RECEITA, DT_RECEITA, CATEGORIA_RECEITA, DESCRICAO_RECEITA) " 
					+ "VALUES (?, ?, ?, ?, ? )");
			stmt.setInt(1, proximoValor);
			stmt.setDouble(2, receita.getValor());
			java.sql.Date data = new java.sql.Date(receita.getData().getTimeInMillis());
			stmt.setDate(3, data);
			stmt.setString(4, receita.getCategotia());
			stmt.setString(5, receita.getDescricao());
			stmt.executeUpdate();
			
			System.out.println("Receita " + receita.getCodigo() + " registrada!");
			
		}catch (SQLException e) {
			e.printStackTrace();
			throw new DBException("Erro ao cadastradar.");
		}finally {
			try {
				stmt.close();
				conexao.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}

	@Override
	public void update(Receita receita) throws DBException {
		PreparedStatement stmt = null;

		try {
			conexao = ConnectionManager.getInstance().getConnection();
			
			stmt = conexao.prepareStatement(
					"UPDATE TB_FIN_RECEITA "
					+ "SET VL_RECEITA = ?, "
					+ "DT_RECEITA = ?, "
					+ "CATEGORIA_RECEITA = ?, "
					+ "DESCRICAO_RECEITA = ? "
					+ "WHERE CD_RECEITA = ?");
			stmt.setDouble(1, receita.getValor());
			java.sql.Date data = new java.sql.Date(receita.getData().getTimeInMillis());
			stmt.setDate(2, data);
			stmt.setString(3, receita.getCategotia());
			stmt.setString(4, receita.getDescricao());
			stmt.setInt(5, receita.getCodigo());
			stmt.executeUpdate();
			
			System.out.println("Receita " + receita.getCodigo() + "  atualizada!");
			
		}catch (SQLException e) {
			e.printStackTrace();
			throw new DBException("Erro ao atualizar.");
		}finally {
			try {
				stmt.close();
				conexao.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}

	@Override
	public void delete(int cd) throws DBException {
		PreparedStatement stmt = null;

		try {
			conexao = ConnectionManager.getInstance().getConnection();
			
			stmt = conexao.prepareStatement(
					"DELETE FROM TB_FIN_RECEITA "
						+ "WHERE CD_RECEITA = ? ");
			stmt.setInt(1, cd);
			stmt.executeUpdate();
			
			System.out.println("Receita " + cd + " removida!");
			
		}catch (SQLException e) {
			e.printStackTrace();
			throw new DBException("Erro ao remover.");
		}finally {
			try {
				stmt.close();
				conexao.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}

	@Override
	public Receita getById(int cd) {
		PreparedStatement stmt = null;
		Receita receita = null;
		ResultSet rs = null;

		try {
			conexao = ConnectionManager.getInstance().getConnection();
			
			stmt = conexao.prepareStatement("SELECT * FROM TB_FIN_RECEITA WHERE CD_RECEITA = ?");
		    stmt.setInt(1, cd);
		    rs = stmt.executeQuery();
		    
		    if (rs.next()) {
		    	int cd_receita = rs.getInt("CD_RECEITA");
			    double vl_receita = rs.getDouble("VL_RECEITA");
			    
			    java.sql.Date data = rs.getDate("DT_RECEITA");
				Calendar dt_receita = Calendar.getInstance();
				dt_receita.setTimeInMillis(data.getTime());
			    
			    String categoria_receita = rs.getString("CATEGORIA_RECEITA");
			    String descricao_receita = rs.getString("DESCRICAO_RECEITA");
			    
			    receita = new Receita(cd_receita, vl_receita, dt_receita, categoria_receita, descricao_receita);
		    }
		    
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				stmt.close();
				rs.close();
				conexao.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return receita;
	}

	@Override
	public List<Receita> getAll() {
		PreparedStatement stmt = null;
		List<Receita> lista = new ArrayList<Receita>();
		ResultSet rs = null;

		try {
			conexao = ConnectionManager.getInstance().getConnection();
			
			stmt = conexao.prepareStatement("SELECT * FROM TB_FIN_RECEITA ORDER BY CD_RECEITA ASC");
			rs = stmt.executeQuery();
			
			while (rs.next()) {
				
				int cd_receita = rs.getInt("CD_RECEITA");
			    double vl_receita = rs.getDouble("VL_RECEITA");
			    
			    java.sql.Date data = rs.getDate("DT_RECEITA");
				Calendar dt_receita = Calendar.getInstance();
				dt_receita.setTimeInMillis(data.getTime());
			    
			    String categoria_receita = rs.getString("CATEGORIA_RECEITA");
			    String descricao_receita = rs.getString("DESCRICAO_RECEITA");
			    
			    Receita receita = new Receita(cd_receita, vl_receita, dt_receita, categoria_receita, descricao_receita);
				
				lista.add(receita);
			}
			
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				stmt.close();
				rs.close();
				conexao.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return lista;
	}
}
