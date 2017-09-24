package com.monroy.pizzeria;

import java.util.*;

public class PizzaIngrediente extends Pizza {

	private HashSet<Ingredientes> listaIngredientes;
	
	public PizzaIngrediente(Tamanho tam) {
		super(tam);
		listaIngredientes = new HashSet<Ingredientes>();
	}
	
	public void agregarIngrediente(Ingredientes ing){
		listaIngredientes.add(ing);
	}

	public String toString() {
		StringBuilder cadena = new StringBuilder();
		for(Ingredientes lst: listaIngredientes){
			cadena.append(lst+"\n");
		}
		return super.toString()+"\nIngredientes:\n" + cadena.toString();
	}
	
	
	
	
}
