package br.com.fiap.fintech.teste;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

import br.com.fiap.fintech.bean.Despesa;
import br.com.fiap.fintech.bean.Receita;
import br.com.fiap.fintech.bean.Usuario;
import br.com.fiap.fintech.dao.DespesaDAO;
import br.com.fiap.fintech.dao.ReceitaDAO;
import br.com.fiap.fintech.dao.UsuarioDAO;
import br.com.fiap.fintech.exception.DBException;
import br.com.fiap.fintech.factory.DAOFactory;
import br.com.fiap.fintech.util.CriptografiaUtils;
import br.com.fiap.store.bean.Categoria;

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

