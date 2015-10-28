package com.sapo.beans;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import com.datatypes.DataProducto;
import com.sapo.ejb.ProductoNegocio;

@ManagedBean
@RequestScoped
public class ListarProductoGenericoBean {

	public ListarProductoGenericoBean() {
		// TODO Auto-generated constructor stub
	}

	@EJB
	ProductoNegocio productoNegocio;
	@ManagedProperty(value = "#{navigationAreaBean}")
	NavigationAreaBean nav;
	private List<DataProducto> productosGenericos;
	private String nombreProductoSeleccionado;
	private int idProductoSeleccionado;

	public List<DataProducto> getProductosGenericos() {
		return productosGenericos;
	}

	public void setProductosGenericos(List<DataProducto> productosGenericos) {
		this.productosGenericos = productosGenericos;
	}

	public String getNombreProductoSeleccionado() {
		return nombreProductoSeleccionado;
	}

	public void setNombreProductoSeleccionado(String nombreProductoSeleccionado) {
		this.nombreProductoSeleccionado = nombreProductoSeleccionado;
	}

	public int getIdProductoSeleccionado() {
		return idProductoSeleccionado;
	}

	public void setIdProductoSeleccionado(int idProductoSeleccionado) {
		this.idProductoSeleccionado = idProductoSeleccionado;
	}

	public NavigationAreaBean getNav() {
		return nav;
	}

	public void setNav(NavigationAreaBean nav) {
		this.nav = nav;
	}

	@PostConstruct
	public void init() {
		obtenerProductosGenericos();
	}

	public void obtenerProductosGenericos() {
		this.productosGenericos = productoNegocio.getProductosGenericos();
	}

}
