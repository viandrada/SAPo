package com.sapo.beans;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import com.datatypes.DataAlmacen;
import com.datatypes.DataProducto;
import com.sapo.ejb.AlmacenNegocio;

@ManagedBean
@RequestScoped
public class AlmacenBean {

	public AlmacenBean(){}
	
	private DataAlmacen almacen;
	private List<DataProducto> productos;
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
	
	public List<DataProducto> getProductos() {
		return productos;
	}

	public void setProductos(List<DataProducto> productos) {
		this.productos = productos;
	}

	public void obtenerAlmacen(){
		this.almacen = almacenNegocio.getAlmacenPorId(nav.getIdAlmacenActual());
	}
	
	public void obtenerProductos(){}
	
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
