package com.cliente.view;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import com.cliente.DAO.ClienteDAO;
import com.cliente.entity.Cliente;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ControllerCliente extends Application implements Initializable{

	
	
    @FXML
    private TextField textFieldNome;

    @FXML
    private TextField textFieldCpf;

    @FXML
    private TextArea textAreaListClientes;

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
    private TextField textFieldCEP;

    @FXML
    private TextField textFieldEndereco;

    @FXML
    private TextField textFieldDesconto;
   
    
    
    public static void main(String[] args) {
		launch();
	}
    
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {			
		listarClientes();
	}
    
    public void execute() {
		launch();		
	}

	@Override
	public void start(Stage stage) throws Exception {
		try {
			AnchorPane pane = (AnchorPane) FXMLLoader.load(getClass().getResource("TelaPrincipal.fxml"));
			Scene sc = new Scene(pane);
			stage.setScene(sc);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public  boolean validacao() {
    	if(textFieldNome.getText().isEmpty() | textFieldCpf.getText().isEmpty() | 
    			textFieldCEP.getText() == null |  textFieldEndereco.getText() == null | textFieldDesconto.getText().isEmpty()){
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
    public void inserirCliente(ActionEvent event) {
    	if(validacao()) {
	    	String nome = textFieldNome.getText();
	    	String cpf = textFieldCpf.getText();
	    	String cep = textFieldCEP.getText();
	    	String endereco = textFieldEndereco.getText();
	    	Double desconto = Double.parseDouble(textFieldDesconto.getText());
	    	
	    	
	    	ClienteDAO clienteDAO = new ClienteDAO();
	    	Cliente cliente = new Cliente(nome,cpf, cep, endereco, desconto);
	    	if(cliente != null) {
	    		clienteDAO.inserir(cliente);
	    		System.out.println("Cliente inserido com sucesso");
	    	}
	
	    	listarClientes();
    	} 
  	


    }
    
    
    @FXML
    void alterarCliente(ActionEvent event) {
    	if(validacao()) {
	    	Cliente cliente= pegaDadosID();
	    	if(String.valueOf(cliente.getId()) == null || String.valueOf(cliente.getId()) =="") {
	    		Alert alert = new Alert(AlertType.WARNING);
	    		alert.setTitle("Alerta");
	    		alert.setHeaderText("Cliente não selecionado");
	    		alert.setContentText("Selecione um Cliente para alterar");
	    	}
	    	else {
	    	
	    		Alert a = new Alert(AlertType.CONFIRMATION);
	        	a.setTitle("Alterar Cliente");
	        	a.setHeaderText("Você está prestes a alterar um Cliente");
	        	a.setContentText("Tem certeza que deseja alterar o Cliente?");
	        	Optional<ButtonType> result = a.showAndWait();
	        	if (result.get() == ButtonType.OK){
	        	
	    		limpaCampos();
	    		int qtde = new ClienteDAO().alterar(cliente);
	    		listarClientes();
	    		
	        	
	        	}
	    	}  
    	}  
    }
    
    
    @FXML
    void excluirCliente(ActionEvent event) {
    	if(validacao()) {
    	
	    	Alert alert = new Alert(AlertType.CONFIRMATION);
	    	alert.setTitle("Deletar cliente");
	    	alert.setHeaderText("Você está prestes a deletar um cliente");
	    	alert.setContentText("Tem certeza que deseja deletar o cliente?");
	    	Optional<ButtonType> result = alert.showAndWait();
	    	if (result.get() == ButtonType.OK){
	    		Cliente cliente= pegaDadosID();
	        	int qtde = new ClienteDAO().deletar(cliente.getId());
	        	limpaCampos();
	        	listarClientes();
	    	}
	    	
    	}
    	
    }

    @FXML
    void buscarCliente(ActionEvent event) {
    	
    	if(textFieldId.getText().isEmpty()) {
    		Alert alert = new Alert(AlertType.WARNING);
    		alert.setTitle("Alerta");
    		alert.setHeaderText("Campos não preenchidos!");
    		alert.setContentText("Preencha os campos!");
    		alert.showAndWait();    		
    		
    	} else {
    		String idString = textFieldId.getText();
    		Cliente cliente = null;
    		if (!idString.equals("")) {
    			try {
    				int id = Integer.valueOf(idString);
    				cliente = new ClienteDAO().buscar(id);
    			} catch (Exception e) {
    			}
    			if (cliente != null) {	
    				
    		    			
    				textFieldNome.setText(cliente.getNome());
    				textFieldCpf.setText(cliente.getCpf());
    				textFieldCEP.setText(cliente.getCep());
    				textFieldEndereco.setText(cliente.getEndereco());      				
    				textFieldDesconto.setText(Double.toString((cliente.getDesconto())));    				
    				
    			}
    		}	    		
    	}   	
    	
    
    }
    
    
    
	private void limpaCampos() {
		textFieldCpf.clear();				
		textFieldNome.clear();
		textFieldNome.requestFocus();	
		textFieldCEP.clear();
		textFieldDesconto.clear();
		textFieldEndereco.clear();
	}
    
    
	private Cliente pegaDados() {		
		return new Cliente(textFieldNome.getText(), textFieldCpf.getText(), 
				textFieldCEP.getText(), textFieldEndereco.getText(), Double.parseDouble(textFieldDesconto.getText()));
	}
	
	private Cliente pegaDadosID() {		
		return new Cliente(Integer.valueOf(textFieldId.getText()), textFieldNome.getText(), textFieldCpf.getText(), 
				textFieldCEP.getText(), textFieldEndereco.getText(), Double.parseDouble(textFieldDesconto.getText()));
	}

	private void listarClientes() {
		
		textAreaListClientes.clear();
		List<Cliente> listaCliente = new ClienteDAO().listar();
		listaCliente.forEach(Cliente -> {
			textAreaListClientes.appendText(Cliente.toString() + "\n");			
		});
		
	}
    



   

	
	
}
