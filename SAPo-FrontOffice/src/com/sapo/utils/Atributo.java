package com.sapo.utils;

import java.util.Date;

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
	private Date valorFecha;
	private double valorNumero;

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

	public Date getValorFecha() {
		return valorFecha;
	}

	public void setValorFecha(Date valorFecha) {
		this.valorFecha = valorFecha;
	}

	public double getValorNumero() {
		return valorNumero;
	}

	public void setValorNumero(double valorNumero) {
		this.valorNumero = valorNumero;
	}

}
