package com.datatypes;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DataProducto {
	public DataProducto() {
	}

	private String nombre;
	private String descripcion;
	private List<byte[]> fotos;
	private float precio;
	private boolean estaActivo;
	private Date fechaAlta;
	private String atributos;
	private int stock;

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

	public List<byte[]> getFoto() {
		return fotos;
	}

	public void setFoto(List<byte[]> fotos) {
		this.fotos = fotos;
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

	public List<byte[]> getFotos() {
		return fotos;
	}

	public void setFotos(List<byte[]> fotos) {
		this.fotos = fotos;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public String getAtributos() {
		return atributos;
	}

	public void setAtributos(String atributos) {
		this.atributos = atributos;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

}
