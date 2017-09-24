package com.monroy.pizzeria;


import java.io.Serializable;
import java.util.LinkedList;

public class ColaPedidos implements Serializable{
	
	private LinkedList<Pedido> listaDePedidos;
	
	public ColaPedidos(){
		listaDePedidos = new LinkedList<Pedido>();
	}
	
	public void agregarPedido(Pedido p){
		listaDePedidos.offer(p);
	}
	
	public Pedido atenderPedido() throws PizzaException{
		Pedido pedido;
		if(listaDePedidos.size()== 0)
			throw new PizzaException("ERROR. No hay ningún pedido en cola.");
		pedido = listaDePedidos.poll();
		return pedido;
	}

	
	public int numeroDePedidosEnCola() {
		return listaDePedidos.size();
		
	}
	
	
	
	

}
