package br.com.fiap.fintech.bean;

import java.util.Calendar;

public class Receita {

	private int codigo;
	private double valor;
	private Calendar data;
	private String categotia;
	private String descricao;
	
	public Receita () {
		super();
	}

	public Receita(int codigo, double valor, Calendar data, String categotia, String descricao) {
		super();
		this.codigo = codigo;
		this.valor = valor;
		this.data = data;
		this.categotia = categotia;
		this.descricao = descricao;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public Calendar getData() {
		return data;
	}

	public void setData(Calendar data) {
		this.data = data;
	}

	public String getCategotia() {
		return categotia;
	}

	public void setCategotia(String categotia) {
		this.categotia = categotia;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	};
	
}
