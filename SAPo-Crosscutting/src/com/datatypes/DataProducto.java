package com.datatypes;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DataProducto {
	public DataProducto() {
	}

	private int idProducto;
	private String nombre;
	private String descripcion;
	private List<DataImagen> fotos;
	private float precio;
	private boolean estaActivo;
	private Date fechaAlta;
	private String atributos;
	private int stock;
	private int idCategoria;
	private String nombreCategoria;
	private int idProductoGenerico;// En caso de usar un producto genérico como
									// plantilla.

	public int getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(int idProducto) {
		this.idProducto = idProducto;
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

	public List<DataImagen> getFotos() {
		return fotos;
	}

	public void setFotos(List<DataImagen> fotos) {
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

	public int getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(int idCategoria) {
		this.idCategoria = idCategoria;
	}

	public String getNombreCategoria() {
		return nombreCategoria;
	}

	public void setNombreCategoria(String nombreCategoria) {
		this.nombreCategoria = nombreCategoria;
	}

	public int getIdProductoGenerico() {
		return idProductoGenerico;
	}

	public void setIdProductoGenerico(int idProductoGenerico) {
		this.idProductoGenerico = idProductoGenerico;
	}
}
