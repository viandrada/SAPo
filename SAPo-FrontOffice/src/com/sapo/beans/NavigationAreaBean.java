package com.sapo.beans;

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
		return "index.xhtml";
	}
}
