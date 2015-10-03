package com.sapo.entidades;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.*;


@Entity
public class CategoriaGenerica implements Serializable {

	private static final long serialVersionUID = 1L;
	
	//La clave no debe ser en nombre?

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idCategoriaGenerica;
	private String nombre;
	private boolean esGenerica;
	
	
	public CategoriaGenerica() {
		super();
	}
	
	public int getIdCategoriaGenerica() {
		return idCategoriaGenerica;
	}

	public void setIdCategoriaGenerica(int idCategoriaGenerica) {
		this.idCategoriaGenerica = idCategoriaGenerica;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public boolean isEsGenerica() {
		return esGenerica;
	}

	public void setEsGenerica(boolean esGenerica) {
		this.esGenerica = esGenerica;
	}
}
