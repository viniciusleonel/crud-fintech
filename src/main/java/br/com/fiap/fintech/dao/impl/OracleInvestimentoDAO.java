package br.com.fiap.fintech.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.com.fiap.fintech.bean.Investimento;
import br.com.fiap.fintech.dao.InvestimentoDAO;
import br.com.fiap.fintech.exception.DBException;
import br.com.fiap.fintech.singleton.ConnectionManager;

public class OracleInvestimentoDAO implements InvestimentoDAO{

	private Connection conexao;

	@Override
	public void insert(Investimento investimento) throws DBException {
		PreparedStatement stmt = null;

		try {
			conexao = ConnectionManager.getInstance().getConnection();
			
			stmt = conexao.prepareStatement(
					"INSERT INTO TB_FIN_INVESTIMENTO (CD_INVESTIMENTO, VL_INVESTIMENTO, DT_DESPESA, CATEGORIA_DESPESA, DESCRICAO_DESPESA) " 
					+ "VALUES (SQ_TB_FIN_INVESTIMENTO.NEXTVAL, ?, ?, ?, ? )");
			stmt.setDouble(1, investimento.getValor());
			java.sql.Date data = new java.sql.Date(investimento.getData().getTimeInMillis());
			stmt.setDate(2, data);
			stmt.setString(3, investimento.getCategotia());
			stmt.setString(4, investimento.getDescricao());
			stmt.executeUpdate();
			
			System.out.println("Investimento " + investimento.getCodigo() + " registrado!");
			
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
	public void update(Investimento investimento) throws DBException {
		PreparedStatement stmt = null;

		try {
			conexao = ConnectionManager.getInstance().getConnection();
			
			stmt = conexao.prepareStatement(
					"UPDATE TB_FIN_INVESTIMENTO "
					+ "SET VL_INVESTIMENTO = ?, "
					+ "DT_INVESTIMENTO = ?, "
					+ "CATEGORIA_INVESTIMENTO = ?, "
					+ "DESCRICAO_INVESTIMENTO = ? "
					+ "WHERE CD_INVESTIMENTO = ?");
			stmt.setDouble(1, investimento.getValor());
			java.sql.Date data = new java.sql.Date(investimento.getData().getTimeInMillis());
			stmt.setDate(2, data);
			stmt.setString(3, investimento.getCategotia());
			stmt.setString(4, investimento.getDescricao());
			stmt.setInt(5, investimento.getCodigo());
			stmt.executeUpdate();
			
			System.out.println("Investimento " + investimento.getCodigo() + "  atualizado!");
			
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
					"DELETE FROM TB_FIN_INVESTIMENTO "
						+ "WHERE CD_INVESTIMENTO = ? ");
			stmt.setInt(1, cd);
			stmt.executeUpdate();
			
			System.out.println("Investimento " + cd + " removido!");
			
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
	public Investimento getById(int cd) {
		PreparedStatement stmt = null;
		Investimento investimento = null;
		ResultSet rs = null;

		try {
			conexao = ConnectionManager.getInstance().getConnection();
			
			stmt = conexao.prepareStatement("SELECT * FROM TB_FIN_INVESTIMENTO WHERE CD_INVESTIMENTO = ?");
		    stmt.setInt(1, cd);
		    rs = stmt.executeQuery();
		    
		    if (rs.next()) {
		    	int cd_investimento = rs.getInt("CD_INVESTIMENTO");
			    double vl_investimento = rs.getDouble("VL_INVESTIMENTO");
			    
			    java.sql.Date data = rs.getDate("DT_INVESTIMENTO");
				Calendar dt_investimento = Calendar.getInstance();
				dt_investimento.setTimeInMillis(data.getTime());
			    
			    String categoria_investimento = rs.getString("CATEGORIA_INVESTIMENTO");
			    String descricao_investimento = rs.getString("DESCRICAO_INVESTIMENTO");
			    
			    investimento = new Investimento(cd_investimento, vl_investimento, dt_investimento, categoria_investimento, descricao_investimento);
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
		return investimento;
	}

	@Override
	public List<Investimento> getAll() {
		PreparedStatement stmt = null;
		List<Investimento> lista = new ArrayList<Investimento>();
		ResultSet rs = null;

		try {
			conexao = ConnectionManager.getInstance().getConnection();
			
			stmt = conexao.prepareStatement("SELECT * FROM TB_FIN_INVESTIMENTO ORDER BY CD_INVESTIMENTO ASC");
			rs = stmt.executeQuery();
			
			while (rs.next()) {
				
				int cd_investimento = rs.getInt("CD_INVESTIMENTO");
			    double vl_investimento = rs.getDouble("VL_INVESTIMENTO");
			    
			    java.sql.Date data = rs.getDate("DT_INVESTIMENTO");
				Calendar dt_investimento = Calendar.getInstance();
				dt_investimento.setTimeInMillis(data.getTime());
			    
			    String categoria_investimento = rs.getString("CATEGORIA_INVESTIMENTO");
			    String descricao_investimento = rs.getString("DESCRICAO_INVESTIMENTO");
			    
			    Investimento investimento  = new Investimento(cd_investimento, vl_investimento, dt_investimento, categoria_investimento, descricao_investimento);
				
				lista.add(investimento);
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