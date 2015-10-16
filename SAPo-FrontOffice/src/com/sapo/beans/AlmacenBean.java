package com.sapo.beans;

import java.util.ArrayList;
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
		this.almacen.setProductos(this.obtenerProductos(nav.getIdAlmacenActual()));
	}
	
	public List<DataProducto> obtenerProductos(int idAlmacen){
		List<DataProducto> dataProductos = new ArrayList<DataProducto>();
		dataProductos = almacenNegocio.getProductosDeAlmacen(idAlmacen);
		return dataProductos;
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
