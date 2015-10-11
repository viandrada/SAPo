package com.datatypes;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class DataProducto {
	public DataProducto(){}
	
	private String nombre;
	private String descripcion;
	private byte[] foto;//List<Imagen> foto;????
	private float precio;
	private boolean estaActivo;
	//private Date fechaAlta;NO VA
	//faltan los atributos	
	//CANTIDAD??
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public byte[] getFoto() {
		return foto;
	}
	public void setFoto(byte[] foto) {
		this.foto = foto;
	}
	public float getPrecio() {
		return precio;
	}
	public void setPrecio(float precio) {
		this.precio = precio;
	}
	public boolean isEstaActivo() {
		return estaActivo;
	}
	public void setEstaActivo(boolean estaActivo) {
		this.estaActivo = estaActivo;
	}
	/*public Date getFechaAlta() {
		return fechaAlta;
	}
	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}*/
	
	
}
