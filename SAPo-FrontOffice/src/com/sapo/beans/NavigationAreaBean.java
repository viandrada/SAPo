package com.sapo.beans;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean
@ApplicationScoped
public class NavigationAreaBean {
	public NavigationAreaBean() {
		super();
		this.renderContent = "altaAlmacen.xhtml";
	}
	
	private String redirectTo;
	private String renderContent;
	private int idAlmacenActual;
	
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


	public String goTo(String redirectTo){
		this.redirectTo = redirectTo;
		return "index.xhtml";
	}
	
	public String irAlmacen(int idAlmacen){
		this.idAlmacenActual = idAlmacen;
		this.redirectTo = "almacen.xhtml";
		return "index.xhtml";
	}
	
	@PostConstruct
	public void init(){
		this.redirectTo = "homeUsuario.xhtml";
	}

}
