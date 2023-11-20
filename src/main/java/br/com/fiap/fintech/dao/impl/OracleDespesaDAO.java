package br.com.fiap.fintech.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.com.fiap.fintech.bean.Conta;
import br.com.fiap.fintech.bean.Despesa;
import br.com.fiap.fintech.bean.Receita;
import br.com.fiap.fintech.dao.DespesaDAO;
import br.com.fiap.fintech.exception.DBException;
import br.com.fiap.fintech.singleton.ConnectionManager;

public class OracleDespesaDAO implements DespesaDAO{

	private Connection conexao;

	@Override
	public void insert(Despesa despesa) throws DBException {
		PreparedStatement stmt = null;

		int proximoValor = 0;
		
		try {
			conexao = ConnectionManager.getInstance().getConnection();
			String sql = "SELECT SQ_TB_FIN_DESPESA.NEXTVAL FROM DUAL";
			
            try (PreparedStatement pstmt = conexao.prepareStatement(sql)) {
                ResultSet rs = pstmt.executeQuery();

                
                if (rs.next()) {
                    proximoValor = rs.getInt(1);
                }
            }
			
			stmt = conexao.prepareStatement(
					"INSERT INTO TB_FIN_DESPESA (CD_DESPESA, VL_DESPESA, DT_DESPESA, CATEGORIA_DESPESA, DESCRICAO_DESPESA) " 
					+ "VALUES (?, ?, ?, ?, ? )");
			stmt.setInt(1, proximoValor);
			stmt.setDouble(2, despesa.getValor());
			java.sql.Date data = new java.sql.Date(despesa.getData().getTimeInMillis());
			stmt.setDate(3, data);
			stmt.setString(4, despesa.getCategoria());
			stmt.setString(5, despesa.getDescricao());
			stmt.executeUpdate();
			
			System.out.println("Despesa " + despesa.getCategoria() + " registrada!");
			
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
	public void update(Despesa despesa) throws DBException {
		PreparedStatement stmt = null;

		try {
			conexao = ConnectionManager.getInstance().getConnection();
			
			stmt = conexao.prepareStatement(
					"UPDATE TB_FIN_DESPESA "
					+ "SET VL_DESPESA = ?, "
					+ "DT_DESPESA = ?, "
					+ "CATEGORIA_DESPESA = ?, "
					+ "DESCRICAO_DESPESA = ? "
					+ "WHERE CD_DESPESA = ?");
			stmt.setDouble(1, despesa.getValor());
			java.sql.Date data = new java.sql.Date(despesa.getData().getTimeInMillis());
			stmt.setDate(2, data);
			stmt.setString(3, despesa.getCategoria());
			stmt.setString(4, despesa.getDescricao());
			stmt.setInt(5, despesa.getCodigo());
			stmt.executeUpdate();
			
			System.out.println("Despesa " + despesa.getCodigo() + "  atualizada!");
			
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
					"DELETE FROM TB_FIN_DESPESA "
						+ "WHERE CD_DESPESA = ? ");
			stmt.setInt(1, cd);
			stmt.executeUpdate();
			
			System.out.println("Despesa " + cd + " removida!");
			
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
	public void autoDelete(int cd) throws DBException {
		PreparedStatement stmt = null;

		try {
			conexao = ConnectionManager.getInstance().getConnection();
			
			stmt = conexao.prepareStatement(
					"DELETE FROM TB_FIN_DESPESA "
						+ "WHERE CD_CONTA = ? ");
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
	public Despesa getById(int cd) {
		PreparedStatement stmt = null;
		Despesa despesa = null;
		ResultSet rs = null;

		try {
			conexao = ConnectionManager.getInstance().getConnection();
			
			stmt = conexao.prepareStatement("SELECT * FROM TB_FIN_DESPESA WHERE CD_DESPESA = ?");
		    stmt.setInt(1, cd);
		    rs = stmt.executeQuery();
		    
		    if (rs.next()) {
		    	int cd_despesa = rs.getInt("CD_DESPESA");
			    double vl_despesa = rs.getDouble("VL_DESPESA");
			    
			    java.sql.Date data = rs.getDate("DT_DESPESA");
				Calendar dt_despesa = Calendar.getInstance();
				dt_despesa.setTimeInMillis(data.getTime());
			    
			    String categoria_despesa = rs.getString("CATEGORIA_DESPESA");
			    String descricao_despesa = rs.getString("DESCRICAO_DESPESA");
			    
			    despesa = new Despesa(cd_despesa, vl_despesa, dt_despesa, categoria_despesa, descricao_despesa);
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
		return despesa;
	}

	@Override
	public List<Despesa> getAll() {
		PreparedStatement stmt = null;
		List<Despesa> lista = new ArrayList<Despesa>();
		ResultSet rs = null;

		try {
			conexao = ConnectionManager.getInstance().getConnection();
			
			stmt = conexao.prepareStatement("SELECT "
					+ "TB_FIN_DESPESA.CD_DESPESA, "
					+ "TB_FIN_DESPESA.VL_DESPESA, "
					+ "TB_FIN_DESPESA.DT_DESPESA, "
					+ "TB_FIN_DESPESA.CATEGORIA_DESPESA, "
					+ "TB_FIN_DESPESA.DESCRICAO_DESPESA, "
					+ "TB_FIN_CONTA.NUM_CONTA, "
					+ "TB_FIN_CONTA.SALDO_CONTA "
				+ "FROM TB_FIN_DESPESA "
				+ "JOIN TB_FIN_CONTA ON TB_FIN_DESPESA.CD_CONTA = TB_FIN_CONTA.CD_CONTA");
			rs = stmt.executeQuery();
			
			while (rs.next()) {
				
				int cd_despesa = rs.getInt("CD_DESPESA");
			    double vl_despesa = rs.getDouble("VL_DESPESA");
			    
			    java.sql.Date data = rs.getDate("DT_DESPESA");
				Calendar dt_despesa = Calendar.getInstance();
				dt_despesa.setTimeInMillis(data.getTime());
			    
			    String categoria_despesa = rs.getString("CATEGORIA_DESPESA");
			    String descricao_despesa = rs.getString("DESCRICAO_DESPESA");
			    
			    int num_conta = rs.getInt("NUM_CONTA");
			    double saldo_conta = rs.getDouble("SALDO_CONTA");
			    
			    Despesa despesa = new Despesa(cd_despesa, vl_despesa, dt_despesa, categoria_despesa, descricao_despesa);
			    Conta conta = new Conta ();
			    conta.setNum(num_conta);
			    conta.setSaldo(saldo_conta);
			    despesa.setConta(conta);
				lista.add(despesa);
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
	
	@Override
	public List<Despesa> getAllById(int id) {
		PreparedStatement stmt = null;
		List<Despesa> lista = new ArrayList<Despesa>();
		ResultSet rs = null;

		try {
			conexao = ConnectionManager.getInstance().getConnection();
			
			stmt = conexao.prepareStatement("SELECT * FROM TB_FIN_DESPESA WHERE CD_CONTA = ?");
			stmt.setInt(1, id);
		    rs = stmt.executeQuery();
			
			while (rs.next()) {
				
				int cd_despesa = rs.getInt("CD_DESPESA");
			    double vl_despesa = rs.getDouble("VL_DESPESA");
			    
			    java.sql.Date data = rs.getDate("DT_DESPESA");
				Calendar dt_despesa = Calendar.getInstance();
				dt_despesa.setTimeInMillis(data.getTime());
			    
			    String categoria_despesa = rs.getString("CATEGORIA_DESPESA");
			    String descricao_despesa = rs.getString("DESCRICAO_DESPESA");
			    
			    Despesa despesa = new Despesa(cd_despesa, vl_despesa, dt_despesa, categoria_despesa, descricao_despesa);

				lista.add(despesa);
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
	
	@Override
	public double calcularTotal(List<Despesa> despesas) {
        double total = 0.0;
        
        for (Despesa despesa : despesas) {
            total += despesa.getValor();
        }
        
        return total;
    }
}
