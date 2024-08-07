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
import javax.servlet.http.HttpSession;

import br.com.fiap.fintech.bean.Conta;
import br.com.fiap.fintech.bean.Usuario;
import br.com.fiap.fintech.dao.ContaDAO;
import br.com.fiap.fintech.dao.DespesaDAO;
import br.com.fiap.fintech.dao.InvestimentoDAO;
import br.com.fiap.fintech.dao.ReceitaDAO;
import br.com.fiap.fintech.dao.UsuarioDAO;
import br.com.fiap.fintech.exception.DBException;
import br.com.fiap.fintech.factory.DAOFactory;
import br.com.fiap.fintech.singleton.ConnectionManager;
import br.com.fiap.fintech.util.CriptografiaUtils;

@WebServlet("/usuario")
public class UsuarioServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    private UsuarioDAO dao;
    private ContaDAO contaDao;
    private ReceitaDAO receitaDao;
    private DespesaDAO despesaDao;
    private InvestimentoDAO investimentoDAO;
    
    private Connection conexao;
    
    @Override
    public void init() throws ServletException{
    	super.init();
    	dao = DAOFactory.getUsuarioDAO();
    	contaDao = DAOFactory.getContaDAO();
    	receitaDao = DAOFactory.getReceitaDAO();
    	despesaDao = DAOFactory.getDespesaDAO();
    	investimentoDAO = DAOFactory.getInvestimentoDAO();
    	}

    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String acao = request.getParameter("acao");
    	
    	switch (acao) {
		case "listar":
			listar(request, response);
			break;
		case "minha-conta":
			minhaConta(request, response);
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
    	HttpSession session = request.getSession();
    	int codigoUsuario = (int) session.getAttribute("id");
    	Usuario usuario = new Usuario();
    	usuario = dao.getById(codigoUsuario);
    	System.out.println(usuario.getNome());
    	request.setAttribute("usuario", usuario);
    	request.getRequestDispatcher("lista-usuario.jsp").forward(request, response);
	}
    
    private void minhaConta(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	HttpSession session = request.getSession();
    	int codigoUsuario = (int) session.getAttribute("id");
    	Usuario usuario = new Usuario(
    			codigoUsuario,
    			dao.getById(codigoUsuario).getNome(),
    			dao.getById(codigoUsuario).getCpf(),
    			dao.getById(codigoUsuario).getLogin(),
    			dao.getById(codigoUsuario).getEmail(),
    			dao.getById(codigoUsuario).getSenha());
    	
    	request.setAttribute("usuario", usuario);
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

			request.setAttribute("msg", "Usuário cadastrado, efetue o login!");

			setContaUsuario(request,response);

		}catch(DBException db) {
			db.printStackTrace();
			request.setAttribute("erro", "Erro ao cadastrar");

		}catch(Exception e){
			e.printStackTrace();
			request.setAttribute("erro","Por favor, valide os dados");
		}
		request.getRequestDispatcher("home.jsp").forward(request, response);
	}

	private void editar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			int codigo = Integer.parseInt(request.getParameter("codigo"));
			String nome = request.getParameter("nome");
			String cpf = request.getParameter("cpf");
			String login = request.getParameter("login");
			String email = request.getParameter("email");
			String senha = request.getParameter("senha");
			
			senha = CriptografiaUtils.criptografar(senha);	
			
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
		response.sendRedirect(request.getContextPath() + "/usuario?acao=listar");
	}
	
	
	
	private void excluir(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
    	int codigoUsuario = (int) session.getAttribute("id");

		int idConta = (int) session.getAttribute("idConta");
		try {
			receitaDao.autoDelete(idConta);
			despesaDao.autoDelete(idConta);
			investimentoDAO.autoDelete(idConta);
			contaDao.delete(codigoUsuario);
			dao.delete(codigoUsuario);
			request.getRequestDispatcher("home.jsp").forward(request, response);
		} catch (DBException e) {
			e.printStackTrace();
			request.setAttribute("erro", "Erro ao atualizar");
		}		
	}

	protected void setContaUsuario(HttpServletRequest request, HttpServletResponse response) 
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
		        }

		        if (rs2.next()) {
		            proximoValorConta = rs2.getInt(1);
		        }

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
			
		}catch(Exception e){
			e.printStackTrace();
		}
		request.getRequestDispatcher("home.jsp").forward(request, response);
	}	

}
