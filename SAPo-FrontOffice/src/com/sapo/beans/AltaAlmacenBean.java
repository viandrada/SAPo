package com.sapo.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean
@RequestScoped
public class AltaAlmacenBean {

	public AltaAlmacenBean() {
		super();
	}

	private String nombre;
	private String descripcion;

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

	public String altaAlmacen() {
		boolean ok = false;
		if (ok) {
			return "success";
		} else {
			return "error";
		}
	}

}
