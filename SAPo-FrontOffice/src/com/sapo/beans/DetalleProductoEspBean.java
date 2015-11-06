package com.sapo.beans;

import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import com.datatypes.DataProducto;
import com.sapo.ejb.ProductoNegocio;

@ManagedBean
@RequestScoped
public class DetalleProductoEspBean {
	public DetalleProductoEspBean() {
	}
	
	
	@EJB
	ProductoNegocio productoNegocio;
	@ManagedProperty(value = "#{navigationAreaBean}")
	NavigationAreaBean nav;
	@ManagedProperty(value="#{param.id}")
	private int id; // +setter
	
	private DataProducto producto;

	public DataProducto getProducto() {
		return producto;
	}

	public void setProducto(DataProducto producto) {
		this.producto = producto;
	}
	
	public NavigationAreaBean getNav() {
		return nav;
	}

	public void setNav(NavigationAreaBean nav) {
		this.nav = nav;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void obtenerProducto(int id){
		this.producto = this.productoNegocio.getProductoPorId(id);
	}
	
	
	@PostConstruct
	public void init(int id){
		//Map<String,String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		  //String id = params.get("Id");
		obtenerProducto(id);
	}
}
