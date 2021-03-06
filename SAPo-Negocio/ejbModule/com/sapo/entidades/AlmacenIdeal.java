package com.sapo.entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Almacen
 *
 */

@Entity
public class AlmacenIdeal implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idAlmacenIdeal;
	private String nombre;
	private String descripcion;

	private Date fechaAlta;
	private boolean estaActivo;

	public AlmacenIdeal() {
		super();
		productos = new ArrayList<Producto>();
		productosGenericos = new ArrayList<ProductoGenerico>();
	}

	public AlmacenIdeal(int id) {
		super();
		this.idAlmacenIdeal = id;
	}

	@OneToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST,
			CascadeType.REMOVE, CascadeType.MERGE })
	private List<Producto> productos;

	@OneToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST,
			CascadeType.REMOVE, CascadeType.MERGE })
	private List<ProductoGenerico> productosGenericos;

	public int getIdAlmacenIdeal() {
		return idAlmacenIdeal;
	}

	public void setIdAlmacenIdeal(int idAlmacenIdeal) {
		this.idAlmacenIdeal = idAlmacenIdeal;
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

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public List<Producto> getProductos() {
		return productos;
	}

	public void setProductos(List<Producto> productos) {
		this.productos = productos;
	}

	public List<ProductoGenerico> getProductosGenericos() {
		return productosGenericos;
	}

	public void setProductosGenericos(List<ProductoGenerico> productosGenericos) {
		this.productosGenericos = productosGenericos;
	}

	public void setEstaActivo(boolean estaActivo) {
		this.estaActivo = estaActivo;
	}

	public Boolean isEstaActivo() {
		return estaActivo;
	}

	public void setEstaActivo(Boolean estaActivo) {
		this.estaActivo = estaActivo;
	}
}
