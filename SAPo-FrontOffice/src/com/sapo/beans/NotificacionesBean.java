package com.sapo.beans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;

import com.datatypes.DataNotificacion;
import com.sapo.ejb.NotificacionNegocio;

@ManagedBean
@SessionScoped
public class NotificacionesBean {

	public NotificacionesBean() {
	}

	private List<DataNotificacion> notificaciones;
	private int cantNotificaciones;
	@EJB
	NotificacionNegocio notificacionService;
	@ManagedProperty(value = "#{loginBean}")
	LoginBean usuarioLogueado;

	public List<DataNotificacion> getNotificaciones() {
		return notificaciones;
	}

	public void setNotificaciones(List<DataNotificacion> notificaciones) {
		this.notificaciones = notificaciones;
	}

	public LoginBean getUsuarioLogueado() {
		return usuarioLogueado;
	}

	public void setUsuarioLogueado(LoginBean usuarioLogueado) {
		this.usuarioLogueado = usuarioLogueado;
	}

	public int getCantNotificaciones() {
		return cantNotificaciones;
	}

	public void setCantNotificaciones(int cantNotificaciones) {
		this.cantNotificaciones = cantNotificaciones;
	}

	@PostConstruct
	public void init() {
		this.notificaciones = new ArrayList<DataNotificacion>();
	}


}
