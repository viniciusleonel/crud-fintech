package br.com.fiap.fintech.dao;

import java.util.List;

import br.com.fiap.fintech.bean.Despesa;
import br.com.fiap.fintech.exception.DBException;

public interface DespesaDAO {

	void insert (Despesa despesa) throws DBException;
	
	void update (Despesa despesa) throws DBException;
	
	void delete (int cd) throws DBException;
	
	Despesa getById(int cd);
	
	List<Despesa> getAll();
}
