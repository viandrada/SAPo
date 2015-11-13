package com.sapo.beans;

import java.util.HashMap;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import com.datatypes.DataUsuario;
import com.sapo.ejb.UsuarioNegocio;

@ManagedBean
@RequestScoped
public class PerfilBean {

	public PerfilBean() {
	}

	private String nombre;
	private String email;
	private String passwordActual;
	private String passwordNueva;
	private boolean premium;
	private String css;
	private HashMap<String,String> listaCSS;

	@ManagedProperty(value = "#{loginBean}")
	LoginBean usuarioLogueado;
	
	@EJB
	UsuarioNegocio usuarioNegocio;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPasswordActual() {
		return passwordActual;
	}

	public void setPasswordActual(String passwordActual) {
		this.passwordActual = passwordActual;
	}

	public String getPasswordNueva() {
		return passwordNueva;
	}

	public void setPasswordNueva(String passwordNueva) {
		this.passwordNueva = passwordNueva;
	}

	public LoginBean getUsuarioLogueado() {
		return usuarioLogueado;
	}

	public void setUsuarioLogueado(LoginBean usuarioLogueado) {
		this.usuarioLogueado = usuarioLogueado;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public boolean isPremium() {
		return premium;
	}

	public void setPremium(boolean premium) {
		this.premium = premium;
	}

	public String getCss() {
		return css;
	}

	public void setCss(String css) {
		this.css = css;
	}

	public HashMap<String, String> getListaCSS() {
		return listaCSS;
	}

	public void setListaCSS(HashMap<String, String> listaCSS) {
		this.listaCSS = listaCSS;
	}

	public void getDatosUsuario(String email) {
		DataUsuario dataUser = usuarioNegocio.getUsuarioPorEmail(email);
		this.email = dataUser.getEmail();
		this.nombre = dataUser.getNombre();
		this.premium = dataUser.isPremium();
	}

	@PostConstruct
	public void inti() {
		//TODO Cargar nombres de css -> como mejora: levantar los nombres de la
		// carpeta css de resources.
		this.listaCSS = new HashMap<String, String>();
		this.listaCSS.put("Default","areaTrabajo.css");
		this.listaCSS.put("Rosado","rosado.css");
		this.listaCSS.put("Verde","verde.css");
		
	}
	
	public void guardarEstilo(){
		this.usuarioNegocio.guardarEstilo(this.usuarioLogueado.getIdUsuario(), this.css);
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "OK", "Estilo guardado. Debes volver a iniciar sesión para poder ver tu nuevo estilo.");
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
}
