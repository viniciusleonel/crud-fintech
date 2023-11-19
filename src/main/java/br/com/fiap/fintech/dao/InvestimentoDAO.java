package br.com.fiap.fintech.dao;

import java.util.List;

import br.com.fiap.fintech.bean.Investimento;
import br.com.fiap.fintech.exception.DBException;

public interface InvestimentoDAO {
	
	void insert (Investimento investimento) throws DBException;

	void update (Investimento investimento) throws DBException;
	
	void delete (int cd) throws DBException;
	
	Investimento getById(int cd);
	
	List<Investimento> getAll();

	void autoDelete(int cd) throws DBException;

	double calcularTotal(List<Investimento> investimentos);
}
