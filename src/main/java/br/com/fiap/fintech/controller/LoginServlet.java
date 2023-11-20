package br.com.fiap.fintech.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.com.fiap.fintech.bean.Conta;
import br.com.fiap.fintech.bean.Usuario;
import br.com.fiap.fintech.dao.ContaDAO;
import br.com.fiap.fintech.dao.UsuarioDAO;
import br.com.fiap.fintech.factory.DAOFactory;
import br.com.fiap.fintech.util.CriptografiaUtils;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private UsuarioDAO dao;
	private ContaDAO contaDao;
  
	public LoginServlet() {
        dao = DAOFactory.getUsuarioDAO();
        contaDao = DAOFactory.getContaDAO();
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		session.invalidate();
		request.getRequestDispatcher("home.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		String senha = request.getParameter("senha");
		
		try {
			senha = CriptografiaUtils.criptografar(senha);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Usuario usuario = new Usuario();
		Conta conta = new Conta();
		
		usuario.setEmail(email);
		usuario.setSenha(senha);
		
		if (dao.validarUsuario(usuario)) {
			int codigo = dao.getCodigo(usuario);
			usuario = dao.getById(codigo);
			
			String nome = usuario.getNome();	
			String cpf = usuario.getCpf();
			String login = usuario.getLogin();

			int contaNum = usuario.getConta().getNum();
			
			
			conta = contaDao.getByUser(codigo);
			
			int idConta = conta.getCodigo();
			HttpSession session = request.getSession();
			session.setAttribute("user", email);
			session.setAttribute("id", codigo);
			session.setAttribute("nome", nome);
			session.setAttribute("cpf", cpf);
			session.setAttribute("login", login);
			session.setAttribute("senha", senha);
			session.setAttribute("conta", contaNum);
			session.setAttribute("idConta", idConta);
			request.getRequestDispatcher("cadastro-receita.jsp").forward(request, response);
		}else {
			request.setAttribute("erro", "Usuário e/ou senha inválidos");
			request.getRequestDispatcher("home.jsp").forward(request, response);
		}
		
	}

}
