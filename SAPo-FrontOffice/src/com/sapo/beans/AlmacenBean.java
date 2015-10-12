package com.sapo.beans;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import com.datatypes.DataAlmacen;
import com.sapo.ejb.AlmacenNegocio;

@ManagedBean
@RequestScoped
public class AlmacenBean {

	public AlmacenBean(){}
	
	private DataAlmacen almacen;
	@EJB
	AlmacenNegocio almacenNegocio;
	@ManagedProperty(value = "#{navigationAreaBean}")
	NavigationAreaBean nav;

	public DataAlmacen getAlmacen() {
		return almacen;
	}

	public void setAlmacen(DataAlmacen almacen) {
		this.almacen = almacen;
	}
	
	public void obtenerAlmacen(){
		this.almacen = almacenNegocio.getAlmacenPorId(nav.getIdAlmacenActual());
	}
	
	public NavigationAreaBean getNav() {
		return nav;
	}

	public void setNav(NavigationAreaBean nav) {
		this.nav = nav;
	}

	@PostConstruct
	public void init(){
		obtenerAlmacen();
	}
}
