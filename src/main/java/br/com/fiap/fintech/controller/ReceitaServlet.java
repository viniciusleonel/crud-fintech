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
import javax.servlet.http.HttpSession;

import br.com.fiap.fintech.bean.Conta;
import br.com.fiap.fintech.bean.Receita;
import br.com.fiap.fintech.dao.ReceitaDAO;
import br.com.fiap.fintech.exception.DBException;
import br.com.fiap.fintech.factory.DAOFactory;
import br.com.fiap.fintech.singleton.ConnectionManager;

/**
 * Servlet implementation class ReceitaServlet
 */
@WebServlet("/receita")
public class ReceitaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    private ReceitaDAO receitaDao;
    
    private Connection conexao;
    
    @Override
    public void init() throws ServletException{
    	super.init();
    	receitaDao = DAOFactory.getReceitaDAO();
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
		HttpSession session = request.getSession();
    	int codigoConta = (int) session.getAttribute("idConta");
		
		List<Receita> lista = receitaDao.getAllById(codigoConta);	
		
		double totalReceitas = receitaDao.calcularTotal(lista);
		
		request.setAttribute("totalReceitas", totalReceitas);
    	request.setAttribute("receitas", lista);
    	request.getRequestDispatcher("lista-receitas.jsp").forward(request, response);
	}
	
	private void abrirFormEdicao(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("codigo"));
		Receita receita = receitaDao.getById(id);
		request.setAttribute("receita", receita);
		request.getRequestDispatcher("edicao-receita.jsp").forward(request, response);
	}
	
	private void abrirFormCadastro(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
    	
		request.getRequestDispatcher("cadastro-receita.jsp").forward(request, response);
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
			
			Receita receita = new Receita(0, valor, data, categoria, descricao);
			receitaDao.insert(receita);

			request.setAttribute("msg", "Receita cadastrada!");

			setContaReceita(request,response);

		}catch(DBException db) {
			db.printStackTrace();
			request.setAttribute("erro", "Erro ao cadastrar");
		}catch(Exception e){
			e.printStackTrace();
			request.setAttribute("erro","Por favor, valide os dados");
		}
		request.getRequestDispatcher("cadastro-receita.jsp").forward(request, response);
	}
	
	protected void setContaReceita(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		int codigoConta = (int) session.getAttribute("idConta");
		
		int proximoValorReceita = 0;
		
		try {
		    conexao = ConnectionManager.getInstance().getConnection();

		    String sql = "SELECT cd_receita FROM tb_fin_receita ORDER BY cd_receita DESC FETCH FIRST 1 ROWS ONLY";
		   
		    try (PreparedStatement pstmt = conexao.prepareStatement(sql)) {

		        ResultSet rs = pstmt.executeQuery();

		        if (rs.next()) {
		        	proximoValorReceita = rs.getInt(1);
		        }
 
		        String updateSqlUser = "UPDATE tb_fin_receita SET CD_CONTA = ? WHERE CD_receita = ?";

		        try (PreparedStatement updateStmtUser = conexao.prepareStatement(updateSqlUser)) {

		            updateStmtUser.setInt(1, codigoConta);
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
	
	private void editar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			int codigo = Integer.parseInt(request.getParameter("codigo"));
			double valor = Double.parseDouble(request.getParameter("valor"));
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			Calendar data = Calendar.getInstance();
			data.setTime(format.parse(request.getParameter("data")));
			String categoria = request.getParameter("categoria");
			String descricao = request.getParameter("descricao");
			
			
			Receita receita = new Receita(codigo, valor, data, categoria, descricao);
			
			receitaDao.update(receita);

			request.setAttribute("msg", "Receita atualizada!");
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
			receitaDao.delete(codigoUser);
			request.setAttribute("msg", "Receita removida!");
		} catch (DBException e) {
			e.printStackTrace();
			request.setAttribute("erro", "Erro ao atualizar");
		}
		listar(request,response);
	}
}
