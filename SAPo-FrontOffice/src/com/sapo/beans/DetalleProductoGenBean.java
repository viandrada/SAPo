package com.sapo.beans;


import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import com.datatypes.DataProducto;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sapo.ejb.ProductoNegocio;
import com.sapo.utils.Atributo;

@ManagedBean
@RequestScoped
public class DetalleProductoGenBean {
	public DetalleProductoGenBean() {
		this.atributosVista = new ArrayList<Atributo>();
	}

	private int idProductoSeleccionado;
	private DataProducto dataProducto;
	private List<Atributo> atributosVista;
	
	private String descripcion;
	private String nombre;

	@EJB
	ProductoNegocio service;

	public int getIdProductoSeleccionado() {
		return idProductoSeleccionado;
	}

	public void setIdProductoSeleccionado(int idProductoSeleccionado) {
		this.idProductoSeleccionado = idProductoSeleccionado;
	}

	public DataProducto getDataProducto() {
		return dataProducto;
	}

	public void setDataProducto(DataProducto dataProducto) {
		this.dataProducto = dataProducto;
	}

	public List<Atributo> getAtributosVista() {
		return atributosVista;
	}

	public void setAtributosVista(List<Atributo> atributosVista) {
		this.atributosVista = atributosVista;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void init(int id) {
		//getProducto(this.idProductoSeleccionado);
		// TODO pasar de json a objeto los atributos
	}

	public void getProducto(String id) {
		
		this.dataProducto = service.getProductoGenericoPorId((Integer.parseInt(id)));
		Gson gson = new Gson();
		Type collectionType = new TypeToken<Collection<Atributo>>(){}.getType();
		Collection<Atributo> atribs = gson.fromJson(this.dataProducto.getAtributos(), collectionType);
		//Atributo a = gson.fromJson(this.dataProducto.getAtributos(), Atributo.class);
	
		for (Iterator<Atributo> iterator = atribs.iterator(); iterator.hasNext();) {
			Atributo atributo = (Atributo) iterator.next();
			this.atributosVista.add(atributo);
			
		}
		System.out.println(this.atributosVista);
	}
}
