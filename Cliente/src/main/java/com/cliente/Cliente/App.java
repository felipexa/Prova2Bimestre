package com.cliente.Cliente;

import com.cliente.view.ControllerCliente;
import com.cliente.view.ControllerVendedor;

/**
 * Hello world!
 *
 */
public class App 
{
	public static void main(String[] args) {		
		new ControllerCliente().execute();
		new ControllerVendedor().execute();
	}
}
