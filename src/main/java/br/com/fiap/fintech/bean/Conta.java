package br.com.fiap.fintech.bean;

public class Conta {

	private int codigo;
	private int num;
	private double saldo;
	private String status;
	private Usuario cd_usuario;
	
	public Conta() {
		super();
	}

	public Conta(int codigo, int num, double saldo, String status) {
		super();
		this.codigo = codigo;
		this.num = num;
		this.saldo = saldo;
		this.status = status;
	}

	public Conta(int cd_conta) {
		super();
		this.codigo = cd_conta;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public double getSaldo() {
		return saldo;
	}

	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Usuario getCd_usuario() {
		return cd_usuario;
	}

	public void setCd_usuario(Usuario cd_usuario) {
		this.cd_usuario = cd_usuario;
	};
	
	

}
