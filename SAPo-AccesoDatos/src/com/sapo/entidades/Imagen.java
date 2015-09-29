package com.sapo.entidades;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Imagen implements Serializable 
{
	@Id @GeneratedValue private int id;
	private byte[] datos;
	private String mime;
	
	public Imagen(){}
	
	public Imagen(byte[] datos, String mime)
	{
		this.datos = datos;
		this.mime = mime;
	}
	
	
	
	
	public int getId() {
		return id;
	}
	public byte[] getDatos() {
		return datos;
	}




	private static final long serialVersionUID = 1L;
}
