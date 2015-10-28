package com.sapo.beans;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import com.datatypes.DataUsuario;
import com.sapo.ejb.AlmacenNegocio;


@ManagedBean
@RequestScoped
public class CompartirAlmacenBean {

	public CompartirAlmacenBean() {
		
	
	}
	
	@PostConstruct
	public void init() {
		//listDataUsuarios=almacenNegocio.listDataUsuarios(usuarioLogueado.getEmail());
		listDataUsuarios=almacenNegocio.listDataUsuariosParaCompartir(usuarioLogueado.getEmail(),nav.getIdAlmacenActual());
		listDataUsuQueCompartenAlmacen=almacenNegocio.listDataUsuQueCompartenA(usuarioLogueado.getEmail(),nav.getIdAlmacenActual());
	}

	@EJB
	AlmacenNegocio almacenNegocio;
	
	@ManagedProperty(value = "#{loginBean}")
	LoginBean usuarioLogueado;
	
	@ManagedProperty(value = "#{navigationAreaBean}")
	NavigationAreaBean nav;
	
	private String nombreUsuSelect;
	private String emailUsuSelect;
	
	private List<DataUsuario> listDataUsuarios;
	private List<DataUsuario> listDataUsuQueCompartenAlmacen;
	

	public String compartirAlmacen(){
		almacenNegocio.compartirAlmacen(usuarioLogueado.getEmail(),emailUsuSelect, nav.getIdAlmacenActual());
		return "index?faces-redirect=true";
	}
	
	public String getNombreUsuSelect() {
		return nombreUsuSelect;
	}

	public void setNombreUsuSelect(String nombreUsuSelect) {
		this.nombreUsuSelect = nombreUsuSelect;
	}

	public String getEmailUsuSelect() {
		return emailUsuSelect;
	}

	public void setEmailUsuSelect(String emailUsuSelect) {
		this.emailUsuSelect = emailUsuSelect;
	}

	public List<DataUsuario> getListDataUsuarios() {
		return listDataUsuarios;
	}

	public void setListDataUsuarios(List<DataUsuario> listDataUsuarios) {
		this.listDataUsuarios = listDataUsuarios;
	}
	
	public LoginBean getUsuarioLogueado() {
		return usuarioLogueado;
	}

	public void setUsuarioLogueado(LoginBean usuarioLogueado) {
		this.usuarioLogueado = usuarioLogueado;
	}

	public NavigationAreaBean getNav() {
		return nav;
	}

	public void setNav(NavigationAreaBean nav) {
		this.nav = nav;
	}

	public List<DataUsuario> getListDataUsuQueCompartenAlmacen() {
		return listDataUsuQueCompartenAlmacen;
	}

	public void setListDataUsuQueCompartenAlmacen(
			List<DataUsuario> listDataUsuQueCompartenAlmacen) {
		this.listDataUsuQueCompartenAlmacen = listDataUsuQueCompartenAlmacen;
	}
}
