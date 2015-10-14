package com.sapo.entidades;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Categoria
 *
 */
@Entity
public class Categoria implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idCategoria;
	private String nombre;
	private boolean esGenerica;
	@ManyToMany(mappedBy = "categorias")
	private List<Almacen> almacenes;

	public Categoria() {
		super();
	}

	public Categoria(int id) {
		super();
		this.idCategoria = id;
	}

	public int getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(int idCategoria) {
		this.idCategoria = idCategoria;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public boolean EsGenerica() {
		return esGenerica;
	}

	public void setEsGenerica(boolean esGenerica) {
		this.esGenerica = esGenerica;
	}

}
