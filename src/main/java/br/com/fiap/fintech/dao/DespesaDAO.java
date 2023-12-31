package br.com.fiap.fintech.dao;

import java.util.List;

import br.com.fiap.fintech.bean.Despesa;
import br.com.fiap.fintech.exception.DBException;

public interface DespesaDAO {

	void insert (Despesa despesa) throws DBException;
	
	void update (Despesa despesa) throws DBException;
	
	void delete (int cd) throws DBException;
	
	void autoDelete(int cd) throws DBException;

	double calcularTotal(List<Despesa> despesas);
	
	List<Despesa> getAll();

	List<Despesa> getAllById(int id);
	
	Despesa getById(int cd);
}
