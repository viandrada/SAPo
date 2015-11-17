package com.sapo.entidades;

import java.io.Serializable;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: NotificacionGenericaConfig
 *
 */
@NamedQueries({
		@NamedQuery(name = "NotificacionGenericaConfig.getConfiguracionesActivas", query = "SELECT n FROM NotificacionGenericaConfig n WHERE n.activa = true"),
		@NamedQuery(name = "NotificacionGenericaConfig.getConfiguraciones", query = "SELECT n FROM NotificacionGenericaConfig n"),
		@NamedQuery(name = "NotificacionGenericaConfig.getConfiguracionePorNombre", query = "SELECT n FROM NotificacionGenericaConfig n WHERE n.nombreParametro = :nombreParametro")})
@Entity
public class NotificacionGenericaConfig implements Serializable {

	private static final long serialVersionUID = 1L;

	public NotificacionGenericaConfig() {
		super();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idNotificacionGenericaConfig;
	private String nombreParametro;
	private int valor;
	private boolean activa;

	public int getIdNotificacionGenericaConfig() {
		return idNotificacionGenericaConfig;
	}

	public void setIdNotificacionGenericaConfig(int idNotificacionGenericaConfig) {
		this.idNotificacionGenericaConfig = idNotificacionGenericaConfig;
	}

	public String getNombreParametro() {
		return nombreParametro;
	}

	public void setNombreParametro(String nombreParametro) {
		this.nombreParametro = nombreParametro;
	}

	public int getValor() {
		return valor;
	}

	public void setValor(int valor) {
		this.valor = valor;
	}

	public boolean isActiva() {
		return activa;
	}

	public void setActiva(boolean activa) {
		this.activa = activa;
	}

}
