package br.com.fiap.fintech.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.fiap.fintech.bean.Receita;
import br.com.fiap.fintech.dao.ContaDAO;
import br.com.fiap.fintech.dao.ReceitaDAO;
import br.com.fiap.fintech.dao.UsuarioDAO;
import br.com.fiap.fintech.exception.DBException;
import br.com.fiap.fintech.factory.DAOFactory;

/**
 * Servlet implementation class ReceitaServlet
 */
@WebServlet("/receita")
public class ReceitaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    private ReceitaDAO receitaDao;
    
    @Override
    public void init() throws ServletException{
    	super.init();
    	receitaDao = DAOFactory.getReceitaDAO();
    	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String acao = request.getParameter("acao");
		
		switch (acao) {
		case "cadastrar":
			cadastrar(request, response);
			break;
		}
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
			
		}catch(DBException db) {
			db.printStackTrace();
			request.setAttribute("erro", "Erro ao cadastrar");
		}catch(Exception e){
			e.printStackTrace();
			request.setAttribute("erro","Por favor, valide os dados");
		}
		request.getRequestDispatcher("cadastro-receita.jsp").forward(request, response);
	}	
}
