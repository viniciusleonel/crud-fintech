package br.com.fiap.fintech.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.fiap.fintech.bean.Conta;
import br.com.fiap.fintech.bean.Usuario;
import br.com.fiap.fintech.dao.ContaDAO;
import br.com.fiap.fintech.dao.UsuarioDAO;
import br.com.fiap.fintech.exception.DBException;
import br.com.fiap.fintech.factory.DAOFactory;
import br.com.fiap.fintech.singleton.ConnectionManager;

/**
 * Servlet implementation class SetContaUser
 */
@WebServlet("/SetContaUser")
public class SetContaUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private UsuarioDAO dao;
    private ContaDAO contaDao;
    
    private Connection conexao;
    
    @Override
    public void init() throws ServletException{
    	super.init();
    	dao = DAOFactory.getUsuarioDAO();
    	contaDao = DAOFactory.getContaDAO();
    	}
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		int proximoValorUser = 0;
		int proximoValorConta = 0;
		
		try {
		    conexao = ConnectionManager.getInstance().getConnection();

		    String sql = "SELECT cd_usuario FROM tb_fin_usuario ORDER BY cd_usuario DESC FETCH FIRST 1 ROWS ONLY";
		    String sql2 = "SELECT cd_conta FROM tb_fin_conta ORDER BY cd_conta DESC FETCH FIRST 1 ROWS ONLY";

		    try (PreparedStatement pstmt = conexao.prepareStatement(sql);
		         PreparedStatement pstmt2 = conexao.prepareStatement(sql2)) {

		        ResultSet rs = pstmt.executeQuery();
		        ResultSet rs2 = pstmt2.executeQuery();

		        if (rs.next()) {
		            proximoValorUser = rs.getInt(1);
		            System.out.println("O próximo valor da sequência de tb_fin_usuario é: " + proximoValorUser);
		        }

		        if (rs2.next()) {
		            proximoValorConta = rs2.getInt(1);
		            System.out.println("O próximo valor da sequência de tb_fin_conta é: " + proximoValorConta);
		        }
		        
		        // Agora, você pode executar as atualizações
		        String updateSqlUser = "UPDATE tb_fin_usuario SET CD_CONTA = ? WHERE CD_USUARIO = ?";
		        String updateSqlConta = "UPDATE tb_fin_conta SET CD_USUARIO = ? WHERE CD_CONTA = ?";

		        try (PreparedStatement updateStmtUser = conexao.prepareStatement(updateSqlUser);
		             PreparedStatement updateStmtConta = conexao.prepareStatement(updateSqlConta)) {

		            updateStmtUser.setInt(1, proximoValorConta);
		            updateStmtUser.setInt(2, proximoValorUser);
		            updateStmtUser.executeUpdate();

		            updateStmtConta.setInt(1, proximoValorUser);
		            updateStmtConta.setInt(2, proximoValorConta);
		            updateStmtConta.executeUpdate();

		            System.out.println("Registros atualizados com sucesso!");
		        }
		    }
			
	
			request.setAttribute("msg", "Usuário cadastrado!");
			
		}catch(Exception e){
			e.printStackTrace();
			request.setAttribute("erro","Por favor, valide os dados");
		}
		request.getRequestDispatcher("cadastro-usuario.jsp").forward(request, response);
	}	
	

}
