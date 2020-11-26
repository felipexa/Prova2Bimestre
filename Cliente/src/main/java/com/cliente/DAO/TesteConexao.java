package com.cliente.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.cliente.db.ConexaoHSQLDB;

public class TesteConexao {

	public static void main(String[] args) {
		ConexaoHSQLDB connect = new ConexaoHSQLDB();
		Connection connection = connect.conectar();
		System.out.println(connection);
//INSERT Dados
		final String Insert_SQL = " INSERT INTO cliente (id, nome) VALUES (?,?) ";
		PreparedStatement pst;
		try {
			pst = connection.prepareStatement(Insert_SQL);
			pst.setString(0, "Dionney");			
			int quantidade = pst.executeUpdate();
			System.out.println("Quantidade de cadastros na tabela: " + quantidade);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//LISTA DADOS
		final String Select_Sql = "Select * FROM cliente";
		try {
		pst = connection.prepareStatement(Select_Sql);
		ResultSet RS = pst.executeQuery();
		while(RS.next()) {
			int id = RS.getInt("ID");
			String nome = RS.getNString("NOME");			
			System.out.println(id+" "+ nome);
		}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		}

}