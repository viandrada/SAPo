package com.sapo.entidades;

import java.io.Serializable;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Notificacion
 *
 */
@NamedQueries({ @NamedQuery(name = "Notificacion.getNotificaciones", query = "SELECT n FROM Notificacion n WHERE n.usuario.idUsuario = :idUsuario and n.leida = FALSE") })
@Entity
public class Notificacion implements Serializable {

	private static final long serialVersionUID = 1L;

	public Notificacion() {
		super();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idNotificacion;
	private String mensaje;
	private boolean leida;
	@OneToOne(cascade = { CascadeType.PERSIST, CascadeType.REMOVE,
			CascadeType.MERGE })
	private Usuario usuario;

	public int getIdNotificacion() {
		return idNotificacion;
	}

	public void setIdNotificacion(int idNotificacion) {
		this.idNotificacion = idNotificacion;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public boolean isLeida() {
		return leida;
	}

	public void setLeida(boolean leida) {
		this.leida = leida;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}
