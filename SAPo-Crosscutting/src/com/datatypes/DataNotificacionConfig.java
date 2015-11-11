package com.datatypes;

public class DataNotificacionConfig {

	private int idNotificacionConfig;
	private String nombreCampo;
	private String operador;
	private double valor;
	private int idProducto;
	private int idUsuario;
	
	public int getIdNotificacionConfig() {
		return idNotificacionConfig;
	}
	public void setIdNotificacionConfig(int idNotificacionConfig) {
		this.idNotificacionConfig = idNotificacionConfig;
	}
	public String getNombreCampo() {
		return nombreCampo;
	}
	public void setNombreCampo(String nombreCampo) {
		this.nombreCampo = nombreCampo;
	}
	public String getOperador() {
		return operador;
	}
	public void setOperador(String operador) {
		this.operador = operador;
	}
	public double getValor() {
		return valor;
	}
	public void setValor(double valor) {
		this.valor = valor;
	}
	public int getIdProducto() {
		return idProducto;
	}
	public void setIdProducto(int idProducto) {
		this.idProducto = idProducto;
	}
	public int getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}
}
