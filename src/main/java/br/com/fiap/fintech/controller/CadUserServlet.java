package br.com.fiap.fintech.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.fiap.fintech.bean.Conta;
import br.com.fiap.fintech.bean.Receita;
import br.com.fiap.fintech.bean.Usuario;
import br.com.fiap.fintech.dao.ContaDAO;
import br.com.fiap.fintech.dao.ReceitaDAO;
import br.com.fiap.fintech.dao.UsuarioDAO;
import br.com.fiap.fintech.exception.DBException;
import br.com.fiap.fintech.factory.DAOFactory;
import br.com.fiap.fintech.util.CriptografiaUtils;

@WebServlet("/usuario")
public class CadUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    private UsuarioDAO dao;
    private ContaDAO contaDao;
    
    @Override
    public void init() throws ServletException{
    	super.init();
    	dao = DAOFactory.getUsuarioDAO();
    	contaDao = DAOFactory.getContaDAO();
    	}

    @Override
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
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
    		throws ServletException, IOException {
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
		List<Usuario> lista = dao.getDetails();	
    	request.setAttribute("usuarios", lista);
    	request.getRequestDispatcher("lista-usuario.jsp").forward(request, response);
	}
    
    private void abrirFormEdicao(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("codigo"));
		Usuario usuario = dao.getById(id);
		request.setAttribute("usuario", usuario);
		request.getRequestDispatcher("edicao-usuario.jsp").forward(request, response);
	}

    private void abrirFormCadastro(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
    	
		request.getRequestDispatcher("cadastro-usuario.jsp").forward(request, response);
	}

	private void cadastrar(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		try{
			String nome = request.getParameter("nome");
			String cpf = request.getParameter("cpf");
			String login = request.getParameter("login");
			String email = request.getParameter("email");
			String senha = request.getParameter("senha");
			
			senha = CriptografiaUtils.criptografar(senha);
			
			Usuario usuario = new Usuario(0, nome, cpf, login, email, senha);
			Conta conta = new Conta (0,0,0, "Ativa");
			
			usuario.setConta(conta);
			conta.setCd_usuario(usuario);
			
			dao.insert(usuario);
			contaDao.insert(conta);
	
			request.setAttribute("msg", "Usuário cadastrado!");
			
			request.getRequestDispatcher("SetContaUser").forward(request, response);
		}catch(DBException db) {
			db.printStackTrace();
			request.setAttribute("erro", "Erro ao cadastrar");
		}catch(Exception e){
			e.printStackTrace();
			request.setAttribute("erro","Por favor, valide os dados");
		}
		request.getRequestDispatcher("SetContaUser").forward(request, response);
	}	
	
	private void editar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			int codigo = Integer.parseInt(request.getParameter("codigo"));
			String nome = request.getParameter("nome");
			String cpf = request.getParameter("cpf");
			String login = request.getParameter("login");
			String email = request.getParameter("email");
			String senha = request.getParameter("senha");
			
			
			Usuario usuario = new Usuario(codigo, nome, cpf, login, email, senha); 
			dao.update(usuario, codigo);

			request.setAttribute("msg", "Usuário atualizado!");
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
			dao.delete(codigoUser);
			contaDao.delete(codigoUser);
			request.setAttribute("msg", "Usuário removido!");
		} catch (DBException e) {
			e.printStackTrace();
			request.setAttribute("erro", "Erro ao atualizar");
		}
		listar(request,response);
	}
	
}
