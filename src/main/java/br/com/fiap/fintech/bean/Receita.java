package br.com.fiap.fintech.bean;

import java.text.NumberFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class Receita {

	private int codigo;
	private double valor;
	private Calendar data;
	private String categoria;
	private String descricao;
	private Conta conta;
	
	public Receita () {
		super();
	}

	public Receita(int codigo, double valor, Calendar data, String categoria, String descricao) {
		super();
		this.codigo = codigo;
		this.valor = valor;
		this.data = data;
		this.categoria = categoria;
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

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categotia) {
		this.categoria = categotia;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}
	
}
