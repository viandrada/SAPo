package com.sapo.entidades;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@Entity
public class Configuracion implements Serializable {

	
	private static final long serialVersionUID = 1L;

	@Id 
	private String clave;
	@Null
	private int valorInt;
	@Null
	private float valorFloat;
	
	
	
	public Configuracion() {
		super();
	}
	
	public Configuracion(String clave,int valorInt) {
		this.clave = clave;
		this.valorInt = valorInt;
	}
	
	public Configuracion(String clave,float valorFloat) {
		this.clave = clave;
		this.valorFloat = valorFloat;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public int getValorInt() {
		return valorInt;
	}

	public void setValorInt(int valorInt) {
		this.valorInt = valorInt;
	}

	public float getValorFloat() {
		return valorFloat;
	}

	public void setValorFloat(float valorFloat) {
		this.valorFloat = valorFloat;
	}
	
   
}
