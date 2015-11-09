package com.datatypes;

import java.util.Date;
import java.util.List;

public class DataReporteProductoGenerico {

	public DataReporteProductoGenerico(){		
	}
	
	private int idProductoGenerico;
	private String nombre;
	private String descripcion;
	private DataImagen imagen;
	private float precio;
	private boolean estaActivo;
	private Date fechaAlta;
	private String atributos;
	private String nombreCategoria;

	private int cantidadUsos;

	public int getIdProductoGenerico() {
		return idProductoGenerico;
	}

	public void setIdProductoGenerico(int idProductoGenerico) {
		this.idProductoGenerico = idProductoGenerico;
	}

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

	public DataImagen getImagen() {
		return imagen;
	}

	public void setImagen(DataImagen imagen) {
		this.imagen = imagen;
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

	public String getNombreCategoria() {
		return nombreCategoria;
	}

	public void setNombreCategoria(String nombreCategoria) {
		this.nombreCategoria = nombreCategoria;
	}

	public int getCantidadUsos() {
		return cantidadUsos;
	}

	public void setCantidadUsos(int cantidadUsos) {
		this.cantidadUsos = cantidadUsos;
	}
	
}
