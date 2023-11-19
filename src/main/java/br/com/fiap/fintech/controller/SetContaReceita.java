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

import br.com.fiap.fintech.dao.ContaDAO;
import br.com.fiap.fintech.dao.ReceitaDAO;
import br.com.fiap.fintech.dao.UsuarioDAO;
import br.com.fiap.fintech.factory.DAOFactory;
import br.com.fiap.fintech.singleton.ConnectionManager;

/**
 * Servlet implementation class SetContaReceita
 */
@WebServlet("/SetContaReceita")
public class SetContaReceita extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private ReceitaDAO receitaDao;
    private ContaDAO contaDao;
    
    private Connection conexao;
    
    @Override
    public void init() throws ServletException{
    	super.init();
    	receitaDao = DAOFactory.getReceitaDAO();
    	contaDao = DAOFactory.getContaDAO();
    	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int proximoValorReceita = 0;
		int proximoValorConta = 0;
		
		try {
		    conexao = ConnectionManager.getInstance().getConnection();

		    String sql = "SELECT cd_receita FROM tb_fin_receita ORDER BY cd_receita DESC FETCH FIRST 1 ROWS ONLY";
		    String sql2 = "SELECT cd_conta FROM tb_fin_conta ORDER BY cd_conta DESC FETCH FIRST 1 ROWS ONLY";

		    try (PreparedStatement pstmt = conexao.prepareStatement(sql);
		         PreparedStatement pstmt2 = conexao.prepareStatement(sql2)) {

		        ResultSet rs = pstmt.executeQuery();
		        ResultSet rs2 = pstmt2.executeQuery();

		        if (rs.next()) {
		        	proximoValorReceita = rs.getInt(1);
		        }

		        if (rs2.next()) {
		            proximoValorConta = rs2.getInt(1);
		        }
		        
		        // Agora, você pode executar as atualizações
		        String updateSqlUser = "UPDATE tb_fin_receita SET CD_CONTA = ? WHERE CD_receita = ?";


		        try (PreparedStatement updateStmtUser = conexao.prepareStatement(updateSqlUser)) {

		            updateStmtUser.setInt(1, proximoValorConta);
		            updateStmtUser.setInt(2, proximoValorReceita);
		            updateStmtUser.executeUpdate();


		            System.out.println("Registros atualizados com sucesso!");
		        }
		    }
			
		}catch(Exception e){
			e.printStackTrace();
		}
		request.getRequestDispatcher("cadastro-receita.jsp").forward(request, response);
	}	

}
