package com.sapo.beans;


import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import com.sapo.ejb.AdministradorNegocio;
import com.sapo.ejb.AlmacenNegocio;

@ManagedBean(eager=true)
@ApplicationScoped
public class NavigationBean {
	
	
	public NavigationBean() {
		super();
		this.renderContent = "";
	}

	@EJB
	AdministradorNegocio adminNegocio;
	private String redirectTo;
	private String renderContent;
	
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


	public String goTo(String redirectTo){
		this.redirectTo = redirectTo;
		return "index";
	}
	
	public AdministradorNegocio getAdminNegocio() {
		return adminNegocio;
	}


	public void setAdminNegocio(AdministradorNegocio adminNegocio) {
		this.adminNegocio = adminNegocio;
	}


	@PostConstruct
	public void insertAdmin(){
		this.adminNegocio.insertAdmin();
	}

}
