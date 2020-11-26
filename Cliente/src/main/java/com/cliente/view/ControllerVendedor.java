package com.cliente.view;


import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import com.cliente.DAO.VendedorDAO;
import com.cliente.entity.Vendedor;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ControllerVendedor extends Application implements Initializable{

	
	
	@FXML
    private TextField textFieldNome;

    @FXML
    private TextField textFieldCpf;

    @FXML
    private TextArea textAreaListVendedores;

    @FXML
    private Button buttonInserir;

    @FXML
    private Button buttonAlterar;

    @FXML
    private Button buttonExcluir;

    @FXML
    private Button buttonBuscar;

    @FXML
    private TextField textFieldId;

    @FXML
    private TextField textFieldComissao;
   
    
    
    public static void main(String[] args) {
		launch();
	}
    
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {			
		listarVendedores();
	}
    
    public void execute() {
		launch();		
	}

	@Override
	public void start(Stage stage) throws Exception {
		try {
			AnchorPane pane = (AnchorPane) FXMLLoader.load(getClass().getResource("TelaVendedor.fxml"));
			Scene sc = new Scene(pane);
			stage.setScene(sc);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public  boolean validacao() {
    	if(textFieldNome.getText().isEmpty() | textFieldCpf.getText().isEmpty() | 
    			textFieldComissao.getText() == null){
    		Alert alert = new Alert(AlertType.WARNING);
    		alert.setTitle("Alerta");
    		alert.setHeaderText("Campos não preenchidos!");
    		alert.setContentText("Preencha os campos!");
    		alert.showAndWait();
    		return false;
    	} 
    	return true;
    	
    }

    @FXML
    public void inserirVendedor(ActionEvent event) {
    	if(validacao()) {
	    	String nome = textFieldNome.getText();
	    	String cpf = textFieldCpf.getText();
	    	Double comissao = Double.parseDouble(textFieldComissao.getText());
	    
	    	
	    	
	    	VendedorDAO vendedorDAO = new VendedorDAO();
	    	Vendedor vendedor = new Vendedor(nome,cpf, comissao);
	    	if(vendedor != null) {
	    		vendedorDAO.inserir(vendedor);
	    		System.out.println("Vendedor inserido com sucesso");
	    	}
	
	    	listarVendedores();
    	} 
  	


    }
    
    
    @FXML
    void alterarVendedor(ActionEvent event) {
    	if(validacao()) {
	    	Vendedor vendedor= pegaDadosID();
	    	if(String.valueOf(vendedor.getId()) == null || String.valueOf(vendedor.getId()) =="") {
	    		Alert alert = new Alert(AlertType.WARNING);
	    		alert.setTitle("Alerta");
	    		alert.setHeaderText("Vendedor não selecionado");
	    		alert.setContentText("Selecione um Vendedor para alterar");
	    	}
	    	else {
	    	
	    		Alert a = new Alert(AlertType.CONFIRMATION);
	        	a.setTitle("Alterar Vendedor");
	        	a.setHeaderText("Você está prestes a alterar um Vendedor");
	        	a.setContentText("Tem certeza que deseja alterar o Vendedor?");
	        	Optional<ButtonType> result = a.showAndWait();
	        	if (result.get() == ButtonType.OK){
	        	
	    		limpaCampos();
	    		int qtde = new VendedorDAO().alterar(vendedor);
	    		listarVendedores();
	    		
	        	
	        	}
	    	}  
    	}  
    }
    
    
    @FXML
    void excluirVendedor(ActionEvent event) {
    	if(validacao()) {
    	
	    	Alert alert = new Alert(AlertType.CONFIRMATION);
	    	alert.setTitle("Deletar vendedor");
	    	alert.setHeaderText("Você está prestes a deletar um vendedor");
	    	alert.setContentText("Tem certeza que deseja deletar o vendedor?");
	    	Optional<ButtonType> result = alert.showAndWait();
	    	if (result.get() == ButtonType.OK){
	    		Vendedor vendedor= pegaDadosID();
	        	int qtde = new VendedorDAO().deletar(vendedor.getId());
	        	limpaCampos();
	        	listarVendedores();
	    	}
	    	
    	}
    	
    }

    @FXML
    void buscarVendedor(ActionEvent event) {
    	
    	if(textFieldId.getText().isEmpty()) {
    		Alert alert = new Alert(AlertType.WARNING);
    		alert.setTitle("Alerta");
    		alert.setHeaderText("Campos não preenchidos!");
    		alert.setContentText("Preencha os campos!");
    		alert.showAndWait();    		
    		
    	} else {
    		String idString = textFieldId.getText();
    		Vendedor vendedor = null;
    		if (!idString.equals("")) {
    			try {
    				int id = Integer.valueOf(idString);
    				vendedor = new VendedorDAO().buscar(id);
    			} catch (Exception e) {
    			}
    			if (vendedor != null) {	
    				    		    		
    				textFieldNome.setText(vendedor.getNome());
    				textFieldCpf.setText(vendedor.getCpf());
    				textFieldComissao.setText(Double.toString(vendedor.getComissao()));    							
    				
    			}
    		}	    		
    	}   	
    	
    
    }
        
    
	private void limpaCampos() {
		textFieldCpf.clear();				
		textFieldNome.clear();
		textFieldNome.requestFocus();	
		textFieldComissao.clear();		
	}
    
    
	private Vendedor pegaDados() {		
		return new Vendedor(textFieldNome.getText(), textFieldCpf.getText(), 
				Double.parseDouble(textFieldComissao.getText()));
	}
	
	private Vendedor pegaDadosID() {		
		return new Vendedor(Integer.valueOf(textFieldId.getText()), textFieldNome.getText(), textFieldCpf.getText(), 
				Double.parseDouble(textFieldComissao.getText()));
	}
	

	private void listarVendedores() {
		
		textAreaListVendedores.clear();
		List<Vendedor> listaVendedor = new VendedorDAO().listar();
		listaVendedor.forEach(Vendedor -> {		
			textAreaListVendedores.appendText(Vendedor.toString() + "\n");		
		});
		
	}
     
	
}
