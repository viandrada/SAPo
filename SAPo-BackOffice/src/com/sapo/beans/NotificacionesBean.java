package com.sapo.beans;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import com.datatypes.DataNotificacionGenericaConfig;
import com.sapo.ejb.NotificacionNegocio;

@ManagedBean
@RequestScoped
public class NotificacionesBean {

	public NotificacionesBean() {
		this.dn = new DataNotificacionGenericaConfig();
	}

	private boolean activa;
	private int cantAlmacenes;
	DataNotificacionGenericaConfig dn;

	@EJB
	NotificacionNegocio notificacionService;

	public boolean isActiva() {
		return activa;
	}

	public void setActiva(boolean activa) {
		this.activa = activa;
	}

	public int getCantAlmacenes() {
		return cantAlmacenes;
	}

	public void setCantAlmacenes(int cantAlmacenes) {
		this.cantAlmacenes = cantAlmacenes;
	}

	@PostConstruct
	public void init() {
		obtenerNotificacionPorLimiteAlmacen();
	}

	public void obtenerNotificacionPorLimiteAlmacen() {
		dn = this.notificacionService
				.getNotificacionGenericaConfig("cantidadAlmacenes");
		if (dn != null) {
			this.activa = dn.isActiva();
			this.cantAlmacenes = dn.getValor();
		}
	}

	public void guardarNotificacion() {
		DataNotificacionGenericaConfig n = new DataNotificacionGenericaConfig();
		n.setActiva(this.activa);
		n.setIdNotificacionGenericaConfig(this.dn
				.getIdNotificacionGenericaConfig());
		n.setNombreParametro("cantidadAlmacenes");
		n.setValor(this.cantAlmacenes);
		this.notificacionService.guardarNotificacionGenericaConfig(n);
		
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "OK", "Notificación guardada.");
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
}
