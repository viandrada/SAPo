package com.sapo.entidades;

import java.io.Serializable;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: NotificacionConfig
 *
 */
@NamedQueries({ @NamedQuery(name = "NotificacionConfig.getConfiguraciones", query = "SELECT n FROM NotificacionConfig n WHERE n.usuario.idUsuario = :idUsuario")})
@Entity
public class NotificacionConfig implements Serializable {

	
	private static final long serialVersionUID = 1L;

	public NotificacionConfig() {
		super();
	}
   
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idNotificacionConfig;
	private String nombreCampo;
	private String operador;
	private String valor;
	@OneToOne(cascade = { CascadeType.PERSIST, CascadeType.REMOVE,
			CascadeType.MERGE })
	private Producto producto;
	@OneToOne(cascade = { CascadeType.PERSIST, CascadeType.REMOVE,
			CascadeType.MERGE })
	private Usuario usuario;

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
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
	public Producto getProducto() {
		return producto;
	}
	public void setProducto(Producto producto) {
		this.producto = producto;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}
