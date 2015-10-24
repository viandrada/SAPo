package com.sapo.beans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.validation.constraints.NotNull;

import com.datatypes.DataCategoria;
import com.sapo.ejb.CategoriaNegocio;


@ManagedBean
@RequestScoped
//@ViewScoped
public class CategoriaBean implements Serializable{
	private static final long serialVersionUID = 1L;

	@NotNull(message = "El nombre es requerido.")
	private String nombre;
	
	@EJB
	CategoriaNegocio cNegocio;
	
	//@ManagedProperty(value = "#{loginBean}")
	//LoginBean usuarioLogueado;
	
/*	@PostConstruct
	public void constructor()
	{
	
		listDataCat=cNegocio.listDataCategorias();
		listDataCatGeneticas=cNegocio.listDataCategoriasGenericas();
		
	}*/
		
	public CategoriaBean(){
	}
	
	/*private List<DataCategoria> listDataCat;
	private List<DataCategoria> listDataCatGeneticas;
	*/
	private int idCatpromover;
	
	private int idcatseleccionada = 1;
	
			
	public CategoriaBean(String nombre){
		super();
		this.nombre = nombre;
	}
	
	public String altaCategoria() {
		
		boolean ok = false;
	
		try {
			 cNegocio.altaCategoria(nombre);
			 ok=true;
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (ok) {
			System.out.println("Alta exitosa de categoria GENERICA");
			return "/index.xhtml?faces-redirect=true";
		} else {
			System.out.println("Error. La CATEGORIA no fue dado de alta GENERICA.");
			return null;
		}
	
	}
	
	/*public void promoverCategoria(int idCatpromover){
		this.idCatpromover=idCatpromover;
		cNegocio.promoCat(this.idCatpromover);
	}*/
	

	public String promoverCategoria(int idCatpromover){
		this.idCatpromover=idCatpromover;
		cNegocio.promoCat(this.idCatpromover);
		return "/index.xhtml?faces-redirect=true";
	}
	
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/*public List<DataCategoria> getListDataCat() {
		return listDataCat;
	}

	public void setListDataCat(List<DataCategoria> listDataCat) {
		this.listDataCat = listDataCat;
	}*/

	public int getIdcatseleccionada() {
		return idcatseleccionada;
	}

	public void setIdcatseleccionada(int idcatseleccionada) {
		this.idcatseleccionada = idcatseleccionada;
	}

	/*public List<DataCategoria> getListDataCatGeneticas() {
		return listDataCatGeneticas;
	}

	public void setListDataCatGeneticas(List<DataCategoria> listDataCatGeneticas) {
		this.listDataCatGeneticas = listDataCatGeneticas;
	}*/

	public int getIdCatpromover() {
		return idCatpromover;
	}

	public void setIdCatpromover(int idCatpromover) {
		this.idCatpromover = idCatpromover;
	}

}
