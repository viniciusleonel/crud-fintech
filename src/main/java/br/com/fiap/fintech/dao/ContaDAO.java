package br.com.fiap.fintech.dao;

import java.util.List;

import br.com.fiap.fintech.bean.Conta;
import br.com.fiap.fintech.exception.DBException;

public interface ContaDAO {

	void insert (Conta conta) throws DBException;
		
	void update (Conta conta) throws DBException;
	
	void delete (int cd) throws DBException;
	
	void setUsuarioConta(int cd_codigo, int cd_conta) throws DBException;
	
	Conta getById(int cd);
	
	List<Conta> getAll();
}
