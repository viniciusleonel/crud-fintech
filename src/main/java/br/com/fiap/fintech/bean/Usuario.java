package br.com.fiap.fintech.bean;

import java.util.List;

public class Usuario {

	private int codigo;
	private String nome;
	private String cpf;
	private String login;
	private String email;
	private String senha;
	private Conta conta;
	
	
	public Usuario() {
		super();
	}
	
	public Usuario(int codigo, String nome, String cpf, String login, String email, String senha) {
		super();
		this.codigo = codigo;
		this.nome = nome;
		this.cpf = cpf;
		this.login = login;
		this.email = email;
		this.senha = senha;
	}

	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}
	
	
	
}
