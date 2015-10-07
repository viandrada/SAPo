package com.sapo.entidades;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Imagen implements Serializable 
{
	private static final long serialVersionUID = 1L;
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idImagen;
	private byte[] datos;
	private String mime;
	
    public Imagen() {
			super();
	}
	
	public Imagen(byte[] datos, String mime)
	{
		this.datos = datos;
		this.setMime(mime);
	}
	
	public int getIdImagen() {
		return idImagen;
	}
	public byte[] getDatos() {
		return datos;
	}

	public String getMime() {
		return mime;
	}

	public void setMime(String mime) {
		this.mime = mime;
	}
	
}
