package com.cliente.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.cliente.db.ConexaoHSQLDB;
import com.cliente.entity.Vendedor;

public class VendedorDAO extends ConexaoHSQLDB {
	final String SQL_INSERT_Vendedor = "INSERT INTO VENDEDOR(NOME, CPF, COMISSAO) VALUES ( ?, ?, ?)";
	final String SQL_SELECT_Vendedor = "SELECT * FROM VENDEDOR";
	final String SQL_SELECT_Vendedor_ID = "SELECT * FROM VENDEDOR WHERE ID = ?";
	final String SQL_ALTERA_Vendedor = "UPDATE VENDEDOR SET NOME=?, CPF=?, COMISSAO=? WHERE ID = ?";
	final String SQL_DELETA_Vendedor = "DELETE FROM VENDEDOR WHERE ID = ?";
	
	public int inserir(Vendedor Vendedor) {
		int quantidade = 0;

		try (Connection connection = this.conectar();
				PreparedStatement pst = connection.prepareStatement(SQL_INSERT_Vendedor);) {
			pst.setString(1, Vendedor.getNome());
			pst.setString(2, Vendedor.getCpf());		
			pst.setDouble(3, Vendedor.getComissao());			
			quantidade = pst.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return quantidade;
	}
	
	public int alterar(Vendedor Vendedor) {
		int quantidade = 0;

		try (Connection connection = this.conectar();
				PreparedStatement pst = connection.prepareStatement(SQL_ALTERA_Vendedor);) {
			pst.setString(1, Vendedor.getNome());
			pst.setString(2, Vendedor.getCpf());		
			pst.setDouble(3, Vendedor.getComissao());						
			pst.setInt(4, Vendedor.getId());
			quantidade = pst.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return quantidade;
	}
	
	public int deletar(int id) {
        int quantidade = 0;
        try (Connection connection = this.conectar();
                PreparedStatement pst = connection.prepareStatement(SQL_DELETA_Vendedor);) {
            pst.setInt(1, id);
            quantidade = pst.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
        return quantidade;
    }
	
	
	public List<Vendedor> listar(){
		List<Vendedor> listaVendedores = new ArrayList<Vendedor>();
		
		try (Connection connection = this.conectar();
				PreparedStatement pst = connection.prepareStatement(SQL_SELECT_Vendedor);){

			ResultSet rs = pst.executeQuery();
			
			while(rs.next()) {
				Vendedor Vendedor = new Vendedor();
				
				Vendedor.setId(rs.getInt("ID"));
				Vendedor.setNome(rs.getString("NOME"));
				Vendedor.setCpf(rs.getString("CPF"));				
				Vendedor.setComissao(rs.getDouble("COMISSAO"));
				
				listaVendedores.add(Vendedor);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		return listaVendedores;
	}

	public Vendedor buscar(int id) {
		Vendedor Vendedor = null;
		try (Connection connection = this.conectar();
				PreparedStatement pst = connection.prepareStatement(SQL_SELECT_Vendedor_ID);){

			pst.setInt(1, id);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()) {
				Vendedor = new Vendedor();
				
				Vendedor.setId(rs.getInt("ID"));
				Vendedor.setNome(rs.getString("NOME"));
				Vendedor.setCpf(rs.getString("CPF"));		
				Vendedor.setComissao(rs.getDouble("COMISSAO"));
							
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		return Vendedor;
	}

}