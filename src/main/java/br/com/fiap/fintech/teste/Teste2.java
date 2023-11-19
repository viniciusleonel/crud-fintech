package br.com.fiap.fintech.teste;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import br.com.fiap.fintech.bean.Receita;
import br.com.fiap.fintech.dao.ReceitaDAO;
import br.com.fiap.fintech.exception.DBException;
import br.com.fiap.fintech.factory.DAOFactory;
import br.com.fiap.fintech.util.CriptografiaUtils;

public class Teste2 {
    public static void main(String[] args) throws DBException {
    		
    		ReceitaDAO receitaDao = DAOFactory.getReceitaDAO();
    		
    		Receita receita = new Receita(0, 2500, Calendar.getInstance(), "comida", "teste");
    	
    		receitaDao.insert(receita);
    
    
    }
    }

