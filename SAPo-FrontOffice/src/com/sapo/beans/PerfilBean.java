package com.sapo.beans;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

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
	
	public void getDatosUsuario(String email){
		DataUsuario dataUser = usuarioNegocio.getUsuarioPorEmail(email); 
		this.email = dataUser.getEmail();
		this.nombre = dataUser.getNombre();
		this.premium = dataUser.isPremium();
	}
}
