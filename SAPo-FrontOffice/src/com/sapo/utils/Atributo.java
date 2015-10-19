package com.sapo.utils;

public class Atributo {
	
	public Atributo() {
	}

	public Atributo(String nombreAtr, String tipoDatoAtr, String valorAtr) {
		this.nombre = nombreAtr;
		this.tipoDato = tipoDatoAtr;
		this.valor = valorAtr;
	}
	
	private String nombre;
	private String tipoDato;
	private String valor;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTipoDato() {
		return tipoDato;
	}

	public void setTipoDato(String tipoDato) {
		this.tipoDato = tipoDato;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

}
