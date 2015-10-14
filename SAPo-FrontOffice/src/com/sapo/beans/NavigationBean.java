package com.sapo.beans;


import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class NavigationBean {
	
	
	public NavigationBean() {
		super();
		this.renderContent = "";
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
	
	@PostConstruct
	public void init(){
		this.redirectTo = "home.xhtml";
	}

}
