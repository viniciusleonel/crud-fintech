package br.com.fiap.fintech.teste;

import java.util.List;

import br.com.fiap.fintech.bean.Despesa;
import br.com.fiap.fintech.dao.DespesaDAO;
import br.com.fiap.fintech.dao.UsuarioDAO;
import br.com.fiap.fintech.exception.DBException;
import br.com.fiap.fintech.factory.DAOFactory;

public class Teste2 {
    public static void main(String[] args) throws DBException {
    		
    	UsuarioDAO userDao = DAOFactory.getUsuarioDAO();
    	
    	DespesaDAO despesaDao = DAOFactory.getDespesaDAO();
    	
//    	Usuario usuario = new Usuario(5, "vinny", "12345678910","teste2", "teste2@gmail.com", "123456");
//    	
////    	userDao.insert(usuario);	
//    	System.out.println();
//    	if(!(userDao.validarUsuario(usuario))) {
//    		userDao.insert(usuario);
//    		System.out.println("Cadastrado");
//    	}
//    	else {
//    		System.out.println("Cadastro existente");
//    	}
  
    	
    	List<Despesa> lista = despesaDao.getAllById(8);
		for (Despesa despesa : lista) {
			System.out.println(despesa.getCategoria() + " " + despesa.getDescricao());
		}
    	
//    	Usuario usuario = new Usuario();
//		usuario = userDao.getById(1);
//		System.out.println(usuario.getNome());
    }
    }

