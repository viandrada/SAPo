package com.sapo.beans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;

import com.datatypes.DataProducto;
import com.sapo.ejb.ProductoNegocio;

@ManagedBean
@ViewScoped
public class VerProductosBean {
	public VerProductosBean() {
		this.productosGenericos = new ArrayList<DataProducto>();
	}

	@EJB
	ProductoNegocio productoNegocio;
	private List<DataProducto> productosGenericos;
	private String nombreProductoSeleccionado;
	private int idProductoSeleccionado;
	private String idProdEliminar;

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

	public String getIdProdEliminar() {
		return idProdEliminar;
	}

	public void setIdProdEliminar(String idProdEliminar) {
		this.idProdEliminar = idProdEliminar;
	}

	@PostConstruct
	public void init() {
		obtenerProductosGenericos();
		  FacesContext facesContext = FacesContext.getCurrentInstance();
	        this.idProdEliminar = facesContext.getExternalContext().getRequestParameterMap().get("idProdEliminar");
	}

	public void obtenerProductosGenericos() {
		this.productosGenericos = productoNegocio.getProductosGenericos();
	}

	public String eliminar() {
		this.productoNegocio.eliminarProductoGenerico(Integer.parseInt(this.idProdEliminar));
		this.idProdEliminar = "0";
		this.productosGenericos = productoNegocio.getProductosGenericos();
		return "/index.xhtml?faces-redirect=true";
	}
}
