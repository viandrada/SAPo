package com.sapo.ejb;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.sapo.dao.AdministradorDAO;
import com.sapo.entidades.Administrador;

/**
 * Session Bean implementation class AdministradorNegocio
 */
@Stateless
@LocalBean
public class AdministradorNegocio implements AdministradorNegocioRemote,
		AdministradorNegocioLocal {

	/**
	 * Default constructor.
	 */
	public AdministradorNegocio() {
		admin = new Administrador();
	}

	@EJB
	private AdministradorDAO adminDAO;
	
	private Administrador admin;
	
	@Override
	public boolean altaAdmin(String nombre, String email, String password) {
		boolean altaExitosa = false;
		admin.setNombre(nombre);
		admin.setEmail(email);
		admin.setPassword(password);
		
		try {
			adminDAO.insertarAdministrador(admin);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		altaExitosa = true;
		return altaExitosa;
	}

	@Override
	public boolean login(String email, String password){
		boolean loginOK = false;
		Administrador administrador = new Administrador();
		administrador.setEmail(email);
		administrador.setPassword(password);
		try {
			loginOK = adminDAO.login(administrador);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return loginOK;
	}
	public Administrador getAdmin() {
		return admin;
	}

	public void setAdmin(Administrador admin) {
		this.admin = admin;
	}
}
