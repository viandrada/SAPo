package com.sapo.beans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.datatypes.DataProducto;
import com.sapo.ejb.ProductoNegocio;

@ManagedBean
@RequestScoped
public class VerProductosBean {
	public VerProductosBean(){
		this.productosGenericos = new ArrayList<DataProducto>();
	}
	
	@EJB
	ProductoNegocio productoNegocio;
	private List<DataProducto> productosGenericos;
	private String nombreProductoSeleccionado;
	private int idProductoSeleccionado;
	
	
	public void setProductosGenericos(List<DataProducto> productosGenericos) {
		this.productosGenericos = productosGenericos;
	}

	public List<DataProducto> getProductosGenericos() {
		return productosGenericos;
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

	@PostConstruct
	public void init(){
		obtenerProductosGenericos();
	}
	
	public void obtenerProductosGenericos(){
		this.productosGenericos = productoNegocio.getProductosGenericos();
	}

}