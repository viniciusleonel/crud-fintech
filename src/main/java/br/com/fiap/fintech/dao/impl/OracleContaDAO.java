package br.com.fiap.fintech.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.fiap.fintech.bean.Conta;
import br.com.fiap.fintech.dao.ContaDAO;
import br.com.fiap.fintech.exception.DBException;
import br.com.fiap.fintech.singleton.ConnectionManager;

public class OracleContaDAO implements ContaDAO{

	private Connection conexao;

	@Override
	public void insert(Conta conta) throws DBException {
		PreparedStatement stmt = null;

		int proximoValor = 0;
		
		try {
			conexao = ConnectionManager.getInstance().getConnection();
			String sql = "SELECT SQ_TB_FIN_CONTA.NEXTVAL FROM DUAL";
			
            try (PreparedStatement pstmt = conexao.prepareStatement(sql)) {
                ResultSet rs = pstmt.executeQuery();

                
                if (rs.next()) {
                    proximoValor = rs.getInt(1);
                    System.out.println("O próximo valor da sequência é: " + proximoValor);
                }

                // Agora você pode usar a variável 'proximoValor' conforme necessário
                // por exemplo, salvá-la em uma variável ou usá-la em outra parte do seu código.
            }
			
			stmt = conexao.prepareStatement(
					"INSERT INTO TB_FIN_CONTA ("
					+ "CD_CONTA, NUM_CONTA, SALDO_CONTA, STATUS_CONTA) " 
					+ "VALUES (?, SQ_FIN_NUM_CONTA.NEXTVAL, ?, ? )");
			stmt.setInt(1, proximoValor);
			stmt.setDouble(2, conta.getSaldo());
			stmt.setString(3, conta.getStatus());
			stmt.executeUpdate();
			
			System.out.println("Conta " + conta.getCodigo() + " registrada!");
			
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
	public void update(Conta conta, int cd_conta) throws DBException {
		PreparedStatement stmt = null;

		try {
			conexao = ConnectionManager.getInstance().getConnection();
			
			stmt = conexao.prepareStatement(
					"UPDATE TB_FIN_CONTA "
					+ "SET NUM_CONTA = ?, "
					+ "SALDO_CONTA = ?, "
					+ "STATUS_CONTA = ? "
					+ "CD_USUARIO = ? "
					+ "WHERE CD_CONTA = ?");
			stmt.setInt(1, conta.getNum());
			stmt.setDouble(2, conta.getSaldo());
			stmt.setString(3, conta.getStatus());
			stmt.setInt(4, conta.getCodigo());
			stmt.setInt(5, cd_conta);
			stmt.executeUpdate();
			
			System.out.println("Conta " + conta.getCodigo() + "  atualizada!");
			
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
					"DELETE FROM TB_FIN_CONTA "
						+ "WHERE CD_CONTA = ? ");
			stmt.setInt(1, cd);
			stmt.executeUpdate();
			
			System.out.println("Conta " + cd + " removida!");
			
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
	public Conta getById(int cd) {
		PreparedStatement stmt = null;
		Conta conta = null;
		ResultSet rs = null;

		try {
			conexao = ConnectionManager.getInstance().getConnection();
			
			stmt = conexao.prepareStatement("SELECT * FROM TB_FIN_CONTA WHERE CD_CONTA = ?");
		    stmt.setInt(1, cd);
		    rs = stmt.executeQuery();
		    
		    if (rs.next()) {
		    	int cd_conta = rs.getInt("CD_CONTA");
		    	int num_conta = rs.getInt("NUM_CONTA");
		    	double saldo_conta = rs.getDouble("SALDO_CONTA");
			    String status_conta = rs.getString("STATUS_CONTA");
			    
			    conta = new Conta(cd_conta, num_conta, saldo_conta, status_conta);
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
		return conta;
	}

	@Override
	public List<Conta> getAll() {
		PreparedStatement stmt = null;
		List<Conta> lista = new ArrayList<Conta>();
		ResultSet rs = null;

		try {
			conexao = ConnectionManager.getInstance().getConnection();
			
			stmt = conexao.prepareStatement("SELECT * FROM TB_FIN_CONTA ORDER BY CD_CONTA ASC");
			rs = stmt.executeQuery();
			
			while (rs.next()) {
				
				int cd = rs.getInt("CD_CONTA");
				int num = rs.getInt("NUM_CONTA");
				double saldo = rs.getDouble("SALDO_CONTA");
				String status = rs.getString("STATUS_CONTA");
				
				Conta conta = new Conta(cd, num, saldo, status);
				
				lista.add(conta);
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
	
	public void setUsuarioConta(int cd_usuario, int cd_conta) throws DBException {
		PreparedStatement stmt = null;

		try {
			conexao = ConnectionManager.getInstance().getConnection();	
			
			String sql = "UPDATE TB_FIN_CONTA "
						+ "SET CD_USUARIO = ? "
							+ "WHERE CD_CONTA = ?";
			stmt = conexao.prepareStatement(sql);
			stmt.setInt(1, cd_usuario);
			stmt.setInt(2, cd_conta);
			stmt.executeUpdate();
						
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
}
