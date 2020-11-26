package com.cliente.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.cliente.db.ConexaoHSQLDB;
import com.cliente.entity.Cliente;
import com.cliente.entity.Comissao;

public class ClienteDAO extends ConexaoHSQLDB implements Comissao {
	final String SQL_INSERT_Cliente = "INSERT INTO CLIENTE(NOME, CPF, CEP, ENDERECO, DESCONTO) VALUES ( ?, ?, ?, ?, ?)";
	final String SQL_SELECT_Cliente = "SELECT * FROM CLIENTE";
	final String SQL_SELECT_Cliente_ID = "SELECT * FROM CLIENTE WHERE ID = ?";
	final String SQL_ALTERA_Cliente = "UPDATE CLIENTE SET NOME=?, CPF=?, CEP=?, ENDERECO=?, DESCONTO=? WHERE ID = ?";
	final String SQL_DELETA_Cliente = "DELETE FROM CLIENTE WHERE ID = ?";
	
	public int inserir(Cliente Cliente) {
		int quantidade = 0;

		try (Connection connection = this.conectar();
				PreparedStatement pst = connection.prepareStatement(SQL_INSERT_Cliente);) {
			pst.setString(1, Cliente.getNome());
			pst.setString(2, Cliente.getCpf());		
			pst.setString(3, Cliente.getCep());
			pst.setString(4, Cliente.getEndereco());	
			pst.setDouble(5, Cliente.getDesconto());	
			quantidade = pst.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return quantidade;
	}
	
	public int alterar(Cliente Cliente) {
		int quantidade = 0;

		try (Connection connection = this.conectar();
				PreparedStatement pst = connection.prepareStatement(SQL_ALTERA_Cliente);) {
			pst.setString(1, Cliente.getNome());
			pst.setString(2, Cliente.getCpf());		
			pst.setString(3, Cliente.getCep());
			pst.setString(4, Cliente.getEndereco());	
			pst.setDouble(5, Cliente.getDesconto());					
			pst.setInt(6, Cliente.getId());
			quantidade = pst.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return quantidade;
	}
	
	public int deletar(int id) {
        int quantidade = 0;
        try (Connection connection = this.conectar();
                PreparedStatement pst = connection.prepareStatement(SQL_DELETA_Cliente);) {
            pst.setInt(1, id);
            quantidade = pst.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
        return quantidade;
    }
	
	
	public List<Cliente> listar(){
		List<Cliente> listaClientes = new ArrayList<Cliente>();
		
		try (Connection connection = this.conectar();
				PreparedStatement pst = connection.prepareStatement(SQL_SELECT_Cliente);){

			ResultSet rs = pst.executeQuery();
			
			while(rs.next()) {
				Cliente Cliente = new Cliente();
				
				Cliente.setId(rs.getInt("ID"));
				Cliente.setNome(rs.getString("NOME"));
				Cliente.setCpf(rs.getString("CPF"));				
				Cliente.setCep(rs.getString("CEP"));
				Cliente.setEndereco(rs.getString("ENDERECO"));
				Cliente.setDesconto(rs.getDouble("DESCONTO"));
				
				listaClientes.add(Cliente);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		return listaClientes;
	}

	public Cliente buscar(int id) {
		Cliente Cliente = null;
		try (Connection connection = this.conectar();
				PreparedStatement pst = connection.prepareStatement(SQL_SELECT_Cliente_ID);){

			pst.setInt(1, id);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()) {
				Cliente = new Cliente();
				
				Cliente.setId(rs.getInt("ID"));
				Cliente.setNome(rs.getString("NOME"));
				Cliente.setCpf(rs.getString("CPF"));				
				Cliente.setCep(rs.getString("CEP"));
				Cliente.setEndereco(rs.getString("ENDERECO"));
				Cliente.setDesconto(rs.getDouble("DESCONTO"));				
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		return Cliente;
	}

	@Override
	public double desconto_cliente(double valor) {
		double custoFinal = valor * 0.10;
		return custoFinal - valor;
	}

}