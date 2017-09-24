package com.monroy.pizzeria;

import java.io.Serializable;

public abstract class Pizza implements Serializable {
	private Tamanho tamanno;
	
	public Pizza(Tamanho tam){
		setTamanno(tam);
	}

	public Tamanho getTamanno() {
		return tamanno;
	}

	public void setTamanno(Tamanho tamanno) {
		this.tamanno = tamanno;
	}

	@Override
	public String toString() {
		return "Pizza: " + tamanno;
	}
	
	
	
	
}
