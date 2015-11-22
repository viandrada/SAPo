package com.datatypes;

public class DataNotificacionGenericaConfig {
	
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
