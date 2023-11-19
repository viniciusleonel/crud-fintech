package br.com.fiap.fintech.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.fiap.fintech.bean.Conta;
import br.com.fiap.fintech.bean.Usuario;
import br.com.fiap.fintech.dao.UsuarioDAO;
import br.com.fiap.fintech.exception.DBException;
import br.com.fiap.fintech.singleton.ConnectionManager;

public class OracleUsuarioDAO implements UsuarioDAO{
	
	private Connection conexao;
	
	@Override
	public boolean validarUsuario(Usuario usuario) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conexao = ConnectionManager.getInstance().getConnection();
			stmt = conexao.prepareStatement("SELECT * FROM TB_FIN_USUARIO WHERE EMAIL_USUARIO = ? AND SENHA_USUARIO = ?");
			stmt.setString(1, usuario.getEmail());
			stmt.setString(2, usuario.getSenha());
			rs = stmt.executeQuery();

			if (rs.next()){
				return true;
			}
			
		} catch (SQLException e) {
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
		return false;
	}
	
	@Override
	public void insert(Usuario usuario) throws DBException {
		
		PreparedStatement stmt = null;

		int proximoValor = 0;
		
		try {
			conexao = ConnectionManager.getInstance().getConnection();
			String sql = "SELECT SQ_TB_FIN_USUARIO.NEXTVAL FROM DUAL";
			
            try (PreparedStatement pstmt = conexao.prepareStatement(sql)) {
                ResultSet rs = pstmt.executeQuery();

                
                if (rs.next()) {
                    proximoValor = rs.getInt(1);
                }
            }
			
			stmt = conexao.prepareStatement(
					"INSERT INTO TB_FIN_USUARIO "
					+ "(CD_USUARIO, NM_USUARIO, CPF_USUARIO, LOGIN_USUARIO, "
					+ "EMAIL_USUARIO, SENHA_USUARIO) " 
						+ "VALUES (? , ?, ?, ?, ?, ? )");
			stmt.setInt(1, proximoValor);
			stmt.setString(2, usuario.getNome());
			stmt.setString(3, usuario.getCpf());
			stmt.setString(4, usuario.getLogin());
			stmt.setString(5, usuario.getEmail());
			stmt.setString(6, usuario.getSenha());
			stmt.executeUpdate();
						
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
	public void update(Usuario usuario,int cd_conta) 
			throws DBException {
		PreparedStatement stmt = null;

		try {
			conexao = ConnectionManager.getInstance().getConnection();
			
			String sql = "UPDATE TB_FIN_USUARIO "
						+ "SET NM_USUARIO = ?, "
						+ "CPF_USUARIO = ?, "
						+ "LOGIN_USUARIO = ?, "
						+ "EMAIL_USUARIO = ?, "
						+ "SENHA_USUARIO = ?,"
						+ "CD_CONTA = ? "
							+ "WHERE CD_USUARIO = ?";
			stmt = conexao.prepareStatement(sql);
			stmt.setString(1, usuario.getNome());
			stmt.setString(2, usuario.getCpf());
			stmt.setString(3, usuario.getLogin());
			stmt.setString(4, usuario.getEmail());
			stmt.setString(5, usuario.getSenha());
			stmt.setInt(6, usuario.getCodigo());
			stmt.setInt(7, cd_conta);
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

	@Override
	public void delete(int cd) throws DBException {
		PreparedStatement stmt = null;

		try {
			conexao = ConnectionManager.getInstance().getConnection();
			
			stmt = conexao.prepareStatement(
					"DELETE FROM TB_FIN_USUARIO "
						+ "WHERE CD_USUARIO = ? ");
			stmt.setInt(1, cd);
			stmt.executeUpdate();
						
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
	public Usuario getById(int cd) {
		PreparedStatement stmt = null;
		Usuario usuario = null;
		ResultSet rs = null;

		try {
			conexao = ConnectionManager.getInstance().getConnection();
			
			stmt = conexao.prepareStatement(
					"SELECT "
							+ "TB_FIN_USUARIO.CD_USUARIO, "
							+ "TB_FIN_USUARIO.NM_USUARIO, "
							+ "TB_FIN_USUARIO.CPF_USUARIO, "
							+ "TB_FIN_USUARIO.LOGIN_USUARIO, "
							+ "TB_FIN_USUARIO.EMAIL_USUARIO, "
							+ "TB_FIN_USUARIO.SENHA_USUARIO,"
							+ "TB_FIN_CONTA.NUM_CONTA, "
							+ "TB_FIN_CONTA.SALDO_CONTA "
						+ "FROM TB_FIN_USUARIO "
						+ "JOIN TB_FIN_CONTA ON TB_FIN_USUARIO.CD_CONTA = TB_FIN_CONTA.CD_CONTA "
							+ "WHERE TB_FIN_USUARIO.CD_USUARIO = ?");
		    stmt.setInt(1, cd);
		    rs = stmt.executeQuery();
		    
		    if (rs.next()) {
		    	int cd_usuario = rs.getInt("CD_USUARIO");
				String nm_usuario = rs.getString("NM_USUARIO");
			    String cpf_usuario = rs.getString("CPF_USUARIO");
			    String login_usuario = rs.getString("LOGIN_USUARIO");
			    String email_usuario = rs.getString("EMAIL_USUARIO");
			    String senha_usuario = rs.getString("SENHA_USUARIO");
			    int num_conta = rs.getInt("NUM_CONTA");
			    double saldo_conta = rs.getDouble("SALDO_CONTA");

			    
			    usuario = new Usuario(
			    		cd_usuario, nm_usuario, cpf_usuario, login_usuario, 
			    		email_usuario, senha_usuario);
			    Conta conta = new Conta ();
			    conta.setNum(num_conta);
			    conta.setSaldo(saldo_conta);
			    
			    usuario.setConta(conta);
				
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
		return usuario;
	}

	@Override
	public List<Usuario> getDetails() {
		PreparedStatement stmt = null;
		List<Usuario> lista = new ArrayList<Usuario>();
		ResultSet rs = null;

			try {
				conexao = ConnectionManager.getInstance().getConnection();
				
				stmt = conexao.prepareStatement(
						"SELECT "
							+ "TB_FIN_USUARIO.CD_USUARIO, "
							+ "TB_FIN_USUARIO.NM_USUARIO, "
							+ "TB_FIN_USUARIO.CPF_USUARIO, "
							+ "TB_FIN_USUARIO.LOGIN_USUARIO, "
							+ "TB_FIN_USUARIO.EMAIL_USUARIO, "
							+ "TB_FIN_USUARIO.SENHA_USUARIO,"
							+ "TB_FIN_CONTA.NUM_CONTA, "
							+ "TB_FIN_CONTA.SALDO_CONTA "
						+ "FROM TB_FIN_USUARIO "
						+ "JOIN TB_FIN_CONTA ON TB_FIN_USUARIO.CD_CONTA = TB_FIN_CONTA.CD_CONTA");
				rs = stmt.executeQuery();
				
				while (rs.next()) {
					
					int cd_usuario = rs.getInt("CD_USUARIO");
					String nm_usuario = rs.getString("NM_USUARIO");
				    String cpf_usuario = rs.getString("CPF_USUARIO");
				    String login_usuario = rs.getString("LOGIN_USUARIO");
				    String email_usuario = rs.getString("EMAIL_USUARIO");
				    String senha_usuario = rs.getString("SENHA_USUARIO");
				    int num_conta = rs.getInt("NUM_CONTA");
				    double saldo_conta = rs.getDouble("SALDO_CONTA");

				    
				    Usuario usuario = new Usuario(
				    		cd_usuario, nm_usuario, cpf_usuario, login_usuario, 
				    		email_usuario, senha_usuario);
				    Conta conta = new Conta ();
				    conta.setNum(num_conta);
				    conta.setSaldo(saldo_conta);
				    
				    usuario.setConta(conta);
					
					lista.add(usuario);
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
	public void setContaUsuario(int cd_conta, int cd_usuario) throws DBException {
		PreparedStatement stmt = null;

		try {
			conexao = ConnectionManager.getInstance().getConnection();	
			
			
			String sql = "UPDATE TB_FIN_USUARIO "
						+ "SET CD_CONTA = ? "
							+ "WHERE CD_USUARIO = ?";
			stmt = conexao.prepareStatement(sql);
			stmt.setInt(1, cd_conta);
			stmt.setInt(2, cd_usuario);
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
