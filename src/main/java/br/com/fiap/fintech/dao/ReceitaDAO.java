package br.com.fiap.fintech.dao;

import java.util.List;

import br.com.fiap.fintech.bean.Receita;
import br.com.fiap.fintech.exception.DBException;

public interface ReceitaDAO {

	void insert (Receita receita) throws DBException;
	
	void update (Receita receita) throws DBException;
	
	void delete (int cd) throws DBException;
	
	Receita getById(int cd);
	
	List<Receita> getAll();

	void autoDelete(int cd) throws DBException;

	double calcularTotal(List<Receita> lista);
}
