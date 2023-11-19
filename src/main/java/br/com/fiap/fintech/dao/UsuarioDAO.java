package br.com.fiap.fintech.dao;

import java.util.List;

import br.com.fiap.fintech.bean.Conta;
import br.com.fiap.fintech.bean.Usuario;
import br.com.fiap.fintech.exception.DBException;

public interface UsuarioDAO {
	
	boolean validarUsuario(Usuario usuario);

	void insert (Usuario usuario) throws DBException;
	
	void update (Usuario usuario, int cd_usuario) throws DBException;
	
	void delete (int cd) throws DBException;
	
	void setContaUsuario(int cd_codigo, int cd_conta) throws DBException;
	
	Usuario getById(int cd);
	
	List<Usuario> getDetails();

	int getCodigo(Usuario usuario);
	
}
