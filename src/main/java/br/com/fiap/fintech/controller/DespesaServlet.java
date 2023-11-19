package br.com.fiap.fintech.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.fiap.fintech.bean.Despesa;
import br.com.fiap.fintech.dao.DespesaDAO;
import br.com.fiap.fintech.exception.DBException;
import br.com.fiap.fintech.factory.DAOFactory;
import br.com.fiap.fintech.singleton.ConnectionManager;

/**
 * Servlet implementation class DespesaServlet
 */
@WebServlet("/despesa")
public class DespesaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private DespesaDAO despesaDao;
    
    private Connection conexao;
    
    @Override
    public void init() throws ServletException{
    	super.init();
    	despesaDao = DAOFactory.getDespesaDAO();
    	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String acao = request.getParameter("acao");
		
    	switch (acao) {
		case "listar":
			listar(request, response);
			break;
		case "abrir-form-edicao":
			abrirFormEdicao(request, response);
			break;
		case "abrir-form-cadastro":
			abrirFormCadastro(request, response);
			break;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String acao = request.getParameter("acao");
		
		switch (acao) {
		case "cadastrar":
			cadastrar(request, response);
			break;
		case "editar":
			editar(request,response);
			break;
		case "excluir":
			excluir(request, response);
			break;
		}
}
	
	private void listar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Despesa> lista = despesaDao.getAll();	
    	request.setAttribute("despesas", lista);
    	request.getRequestDispatcher("lista-despesas.jsp").forward(request, response);
	}
	
	private void abrirFormEdicao(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("codigo"));
		Despesa despesa = despesaDao.getById(id);
		request.setAttribute("despesa", despesa);
		request.getRequestDispatcher("edicao-despesa.jsp").forward(request, response);
	}
	
	private void abrirFormCadastro(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
    	
		request.getRequestDispatcher("cadastro-despesa.jsp").forward(request, response);
	}
	
	private void cadastrar(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		try{
			double valor = Double.parseDouble(request.getParameter("valor"));
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			Calendar data = Calendar.getInstance();
			data.setTime(format.parse(request.getParameter("data")));
			String categoria = request.getParameter("categoria");
			String descricao = request.getParameter("descricao");
			
			
			Despesa despesa = new Despesa(0, valor, data, categoria, descricao);

			despesaDao.insert(despesa);
	
			request.setAttribute("msg", "Despesa cadastrada!");
			
			setContaDespesa(request,response);
			
		}catch(DBException db) {
			db.printStackTrace();
			request.setAttribute("erro", "Erro ao cadastrar");
		}catch(Exception e){
			e.printStackTrace();
			request.setAttribute("erro","Por favor, valide os dados");
		}
		request.getRequestDispatcher("cadastro-despesa.jsp").forward(request, response);
	}
	
	protected void setContaDespesa(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int proximoValorReceita = 0;
		int proximoValorConta = 0;
		
		try {
		    conexao = ConnectionManager.getInstance().getConnection();

		    String sql = "SELECT cd_despesa FROM tb_fin_despesa ORDER BY cd_despesa DESC FETCH FIRST 1 ROWS ONLY";
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
		        
		        String updateSqlUser = "UPDATE tb_fin_despesa SET CD_CONTA = ? WHERE CD_despesa = ?";


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
		request.getRequestDispatcher("cadastro-despesa.jsp").forward(request, response);
	}	
	
	private void editar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			int codigo = Integer.parseInt(request.getParameter("codigo"));
			double valor = Double.parseDouble(request.getParameter("valor"));
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			Calendar data = Calendar.getInstance();
			data.setTime(format.parse(request.getParameter("data")));
			String categoria = request.getParameter("categoria");
			String descricao = request.getParameter("descricao");
			
			
			Despesa despesa = new Despesa(codigo, valor, data, categoria, descricao);
			
			despesaDao.update(despesa);

			request.setAttribute("msg", "Despesa atualizada!");
		} catch (DBException db) {
			db.printStackTrace();
			request.setAttribute("erro", "Erro ao atualizar");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("erro", "Por favor, valide os dados");
		}
		listar(request,response);
	}
	
	private void excluir(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int codigoUser = Integer.parseInt(request.getParameter("codigo"));
		try {
			despesaDao.delete(codigoUser);
			request.setAttribute("msg", "Despesa removida!");
		} catch (DBException e) {
			e.printStackTrace();
			request.setAttribute("erro", "Erro ao atualizar");
		}
		listar(request,response);
	}
}