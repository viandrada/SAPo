package com.sapo.beans;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class NavigationAreaBean {
	public NavigationAreaBean() {
		super();
		this.renderContent = "altaAlmacen.xhtml";
	}

	private String redirectTo;
	private String renderContent;
	private int idAlmacenActual;
	private int idProductoActual;
	private int idPlantillaSeleccionada;

	public String getRedirectTo() {
		return redirectTo;
	}

	public void setRedirectTo(String redirectTo) {
		this.redirectTo = redirectTo;
	}

	public String getRenderContent() {
		return renderContent;
	}

	public void setRenderContent(String renderContent) {
		this.renderContent = renderContent;
	}

	public int getIdAlmacenActual() {
		return idAlmacenActual;
	}

	public void setIdAlmacenActual(int idAlmacenActual) {
		this.idAlmacenActual = idAlmacenActual;
	}

	public int getIdProductoActual() {
		return idProductoActual;
	}

	public void setIdProductoActual(int idProductoActual) {
		this.idProductoActual = idProductoActual;
	}

	public int getIdPlantillaSeleccionada() {
		return idPlantillaSeleccionada;
	}

	public void setIdPlantillaSeleccionada(int idPlantillaSeleccionada) {
		this.idPlantillaSeleccionada = idPlantillaSeleccionada;
	}

	public String goTo(String redirectTo) {
		this.redirectTo = redirectTo;
		return "index.xhtml";
	}

	public String irAlmacen(int idAlmacen) {
		this.idAlmacenActual = idAlmacen;
		this.redirectTo = "almacen.xhtml";
		return "index.xhtml";
	}

	public void irPlantilla(int idProductoGenerico) {
		this.idPlantillaSeleccionada = idProductoGenerico;
		this.redirectTo = "altaProductoConPlantilla.xhtml";
	}

	public String setIdProducto(int idProducto) {
		this.idProductoActual = idProducto;
		this.redirectTo = "detalleProducto.xhtml";
		return "index.xhtml";
	}

	@PostConstruct
	public void init() {
		this.redirectTo = "homeUsuario.xhtml";
	}

}
