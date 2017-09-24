package com.monroy.pizzeria;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;

public class Pedido implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String direccion;
	private HashSet<Pizza> pizzas;
	
	public Pedido(String direccion){
		setDireccion(direccion);
		pizzas = new HashSet<Pizza>();
	}
	
	
	
	public void agregarPizza(Pizza a){
		pizzas.add(a);
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	@Override
	public String toString() {
		
		StringBuilder cadena = new StringBuilder();
		cadena.append("Dirección del Pedido: "+direccion);
			for(Pizza lst: pizzas){
				cadena.append("\n"+lst.toString()+"\n");
			}
		return cadena.toString();
	}
	
	
}
