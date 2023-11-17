package br.com.fiap.fintech.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
import br.com.fiap.fintech.sequencia.SequenciaUser;


@WebServlet("/home")
public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private UsuarioDAO dao;
    private ContaDAO contaDao;
       
    
    @Override
    public void init() throws ServletException{
    	super.init();
    	dao = DAOFactory.getUsuarioDAO();
    	contaDao = DAOFactory.getContaDAO();
    	}

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {

	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String acao = request.getParameter("acao");
		
		switch (acao) {
		case "cadastrar":
			cadastrar(request, response);
			break;
//		case "editar":
//			editar(request,response);
//			break;
//		case "excluir":
//			excluir(request, response);
//			break;
//		}
	}
	}

	private void cadastrar(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		SequenciaUser novaSequencia = new SequenciaUser();
		
		List<Integer> listaCodUser = new ArrayList<>();
		
		listaCodUser = novaSequencia.novoCodigoUser(listaCodUser);
		int cd_user = listaCodUser.get(listaCodUser.size() - 1);
		
		listaCodUser = novaSequencia.novoCodigoUser(listaCodUser);
		int cd_conta = listaCodUser.get(listaCodUser.size() - 1);
		
		try{
			String nome = request.getParameter("nome");
			String cpf = request.getParameter("cpf");
			String login = request.getParameter("login");
			String email = request.getParameter("email");
			String senha = request.getParameter("senha");
			
			Usuario usuario = new Usuario(cd_user, nome, cpf, login, email, senha);
			Conta conta = new Conta (cd_conta,0,0, "Ativa");
			
			dao.insert(usuario);
			contaDao.insert(conta);
			
			
			
			
			request.setAttribute("msg", "Usuário cadastrado!");
		}catch(DBException db) {
			db.printStackTrace();
			request.setAttribute("erro", "Erro ao cadastrar");
		}catch(Exception e){
			e.printStackTrace();
			request.setAttribute("erro","Por favor, valide os dados");
		}
		request.getRequestDispatcher("cadastro-usuario.jsp").forward(request, response);
	}	
}
