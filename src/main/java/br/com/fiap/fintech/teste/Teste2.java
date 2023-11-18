package br.com.fiap.fintech.teste;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.fiap.fintech.util.CriptografiaUtils;

public class Teste2 {
    public static void main(String[] args) {
    		try {
    			System.out.println(CriptografiaUtils.criptografar("123456"));
    			System.out.println(CriptografiaUtils.criptografar("123456"));
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
    	}
    }

