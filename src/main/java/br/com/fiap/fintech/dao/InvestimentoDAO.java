package br.com.fiap.fintech.dao;

import java.util.List;

import br.com.fiap.fintech.bean.Investimento;
import br.com.fiap.fintech.exception.DBException;

public interface InvestimentoDAO {
	
	void insert (Investimento investimento) throws DBException;

	void update (Investimento investimento) throws DBException;
	
	void delete (int cd) throws DBException;
	
	void autoDelete(int cd) throws DBException;

	double calcularTotal(List<Investimento> investimentos);
	
	List<Investimento> getAll();
	
	List<Investimento> getAllById(int id);
	
	Investimento getById(int cd);
	
}
