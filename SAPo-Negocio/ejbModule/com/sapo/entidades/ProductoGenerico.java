package com.sapo.entidades;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

@NamedQueries({

@NamedQuery(name = "ProductosGenericos.getProductos", query = "SELECT p FROM ProductoGenerico p where p.estaActivo = TRUE") })
@Entity
public class ProductoGenerico implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idProductoGenerico;
	private String nombre;
	private String descripcion;
	private float precio;
	private String atributos;
	private Date fechaAlta;
	private boolean estaActivo;

	@OneToOne(cascade = { CascadeType.PERSIST, CascadeType.REMOVE,
			CascadeType.MERGE })
	private Categoria categoria;

	@OneToOne(cascade = { CascadeType.PERSIST, CascadeType.REMOVE,
			CascadeType.MERGE })
	private Imagen foto;

	public ProductoGenerico() {
		super();
	}

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

	public void setEstaActivo(boolean estaActivo) {
		this.estaActivo = estaActivo;
	}

	public float getPrecio() {
		return precio;
	}

	public void setPrecio(float precio) {
		this.precio = precio;
	}

	public String getAtributos() {
		return atributos;
	}

	public void setAtributos(String atributos) {
		this.atributos = atributos;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public Boolean getEstaActivo() {
		return estaActivo;
	}

	public void setEstaActivo(Boolean estaActivo) {
		this.estaActivo = estaActivo;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Imagen getFoto() {
		return foto;
	}

	public void setFoto(Imagen foto) {
		this.foto = foto;
	}
}
