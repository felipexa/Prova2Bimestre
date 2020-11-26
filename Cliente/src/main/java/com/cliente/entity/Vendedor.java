package com.cliente.entity;

public class Vendedor extends Pessoa {

	private double comissao;

	public double getComissao() {
		return comissao;
	}

	public void setComissao(double comissao) {
		this.comissao = comissao;
	}

	public Vendedor() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Vendedor(int id, String nome, String cpf, double comissao) {
		super(id, nome, cpf);
		this.comissao = comissao;
	}

	public Vendedor(String nome, String cpf,  double comissao) {
		super(nome, cpf);
		this.comissao = comissao;
	}

	@Override
	public String toString() {
		return "Vendedor [id=" + getId() + ", nome=" + getNome()+", cpf=" + getCpf()+"comissao=" + comissao + "]";
	}

	

	
	
	
}
