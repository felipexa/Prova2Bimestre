package com.cliente.entity;

public class Cliente extends Pessoa {

	private String cep;
	private String endereco;
	private Double desconto;
	
	public Cliente() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Cliente(int id, String nome, String cpf,  String cep, String endereco, Double desconto) {
		super(id, nome, cpf);
		this.cep = cep;
		this.endereco = endereco;
		this.desconto = desconto;		
	}
	public Cliente(String nome, String cpf, String cep, String endereco, Double desconto) {
		super(nome, cpf);
		this.cep = cep;
		this.endereco = endereco;
		this.desconto = desconto;		
	}
	
	public String getCep() {
		return cep;
	}
	public void setCep(String cep) {
		this.cep = cep;
	}
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	public Double getDesconto() {
		return desconto;
	}
	public void setDesconto(Double desconto) {
		this.desconto = desconto;
	}
	@Override
	public String toString() {
		return "Cliente [id=" + getId() + ", cep=" + cep + ", endereco=" + endereco + ", desconto=" + desconto + ", nome=" + getNome()+", cpf=" + getCpf()+"]";
	}
	
	
	
}
