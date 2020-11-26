package com.cliente.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoHSQLDB {
	
	private String usuario = "SA";
	private String senha = "";
	private String PathBase = "C:\\Users\\felii\\Documents\\Git\\Avaliacao2bimestre\\JAVAFX - Prova2Bimestre\\Cliente\\Dados";
	private String URL = "jdbc:hsqldb:file:" + PathBase + ";";
	
	public Connection conectar() {		
		try {
			return DriverManager.getConnection(URL, usuario, senha);
		} catch (SQLException e) {
			throw new Error("SQLException");			
		}
	}
}
