package com.datatypes;

public class DataImagen {
	
	public DataImagen(){}
	
	private int idImagen;
	private String nombre;
	private byte[] datos;
	private String mime;
	public int getIdImagen() {
		return idImagen;
	}
	public void setIdImagen(int idImagen) {
		this.idImagen = idImagen;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public byte[] getDatos() {
		return datos;
	}
	public void setDatos(byte[] datos) {
		this.datos = datos;
	}
	public String getMime() {
		return mime;
	}
	public void setMime(String mime) {
		this.mime = mime;
	}
}
