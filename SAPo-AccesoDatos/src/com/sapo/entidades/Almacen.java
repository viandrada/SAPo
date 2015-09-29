package com.sapo.entidades;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Almacen
 *
 */
@Entity

public class Almacen implements Serializable {

	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idAlmacen;
	private String nombre;
	private String descripcion;
	@OneToOne(cascade={CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE})
	private Imagen foto;
	private Date fechaAlta;
	
	public Almacen() {
		super();
	}

	public int getIdAlmacen() {
		return idAlmacen;
	}

	public void setIdAlmacen(int idAlmacen) {
		this.idAlmacen = idAlmacen;
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

	public Imagen getFoto() {
		return foto;
	}

	public void setFoto(Imagen foto) {
		this.foto = foto;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}
   
}
