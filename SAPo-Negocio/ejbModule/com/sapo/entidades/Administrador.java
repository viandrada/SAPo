package com.sapo.entidades;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Administrador
 *
 */
@NamedQueries({

		@NamedQuery(name = "Administrador.loginAdministrador.Email.Pass", query = "SELECT u "
				+ "FROM Administrador u "
				+ "WHERE u.email = :email and u.password = :pass"),
		@NamedQuery(name = "Administrador.getAdministradores", query = "SELECT u "
				+ "FROM Administrador u ") })
@Entity
public class Administrador implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idAdministrador;
	private String nombre;
	private String email;
	private String password;
	private int telefono;
	private Date fecha;
	private boolean estaActivo;

	public Administrador() {
		super();
	}

	public int getIdAdministrador() {
		return idAdministrador;
	}

	public void setIdAdministrador(int idAdministrador) {
		this.idAdministrador = idAdministrador;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public boolean EstaActivo() {
		return estaActivo;
	}

	public void setEstaActivo(boolean estactivo) {
		this.estaActivo = estactivo;
	}

	public int getTelefono() {
		return telefono;
	}

	public void setTelefono(int telefono) {
		this.telefono = telefono;
	}

}
