package com.sapo.beans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;

import com.datatypes.DataCategoria;
import com.sapo.ejb.CategoriaNegocio;


//@RequestScoped
//
@ManagedBean
public class listarCategoriasBean /*implements Serializable*/{
	//private static final long serialVersionUID = 1L;
		
	@EJB
	CategoriaNegocio cNegocio;
		
	@PostConstruct
	public void constructor()
	{
		System.out.println("carge las listas");
		listDataCat=cNegocio.listDataCategorias();
		listDataCatGeneticas=cNegocio.listDataCategoriasGenericas();
		nombreCatSeleccionada="vacio";
		idCatSeleccionada=1;
		
	}
			
	public listarCategoriasBean(){
		
		
	}
	
	private List<DataCategoria> listDataCat;
	private List<DataCategoria> listDataCatGeneticas;
	
	private int idCatpromover;
	
	public String nombreCatSeleccionada;
	public int idCatSeleccionada;
	
	public void promoverCategoria(int idCatpromover){
		this.idCatpromover=idCatpromover;
		cNegocio.promoCat(this.idCatpromover);
	}	
	
	public void modificarCat(){
		
		System.out.println("estoy en modificar CATEGORIA: "+this.nombreCatSeleccionada+" id: "+ idCatSeleccionada);
		cNegocio.modivicarNombreCat(idCatSeleccionada, nombreCatSeleccionada);
	};
		
	public List<DataCategoria> getListDataCat() {
		return listDataCat;
	}

	public void setListDataCat(List<DataCategoria> listDataCat) {
		this.listDataCat = listDataCat;
	}

	
	public List<DataCategoria> getListDataCatGeneticas() {
		return listDataCatGeneticas;
	}

	public int getIdCatpromover() {
		return idCatpromover;
	}

	public void setIdCatpromover(int idCatpromover) {
		this.idCatpromover = idCatpromover;
	}

	public String getNombreCatSeleccionada() {
		return nombreCatSeleccionada;
	}

	public void setNombreCatSeleccionada(String nombreCatSeleccionada) {
		this.nombreCatSeleccionada = nombreCatSeleccionada;
	}

	public int getIdCatSeleccionada() {
		return idCatSeleccionada;
	}

	public void setIdCatSeleccionada(int idCatSeleccionada) {
		this.idCatSeleccionada = idCatSeleccionada;
	}

	public void setListDataCatGeneticas(List<DataCategoria> listDataCatGeneticas) {
		this.listDataCatGeneticas = listDataCatGeneticas;
	}

}
