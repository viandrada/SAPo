package com.sapo.ejb;

import java.util.Date;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.datatypes.DataAdministrador;
import com.sapo.dao.AdministradorDAO;
import com.sapo.entidades.Administrador;

/**
 * Session Bean implementation class AdministradorNegocio
 */
@Stateless
@LocalBean
public class AdministradorNegocio /*
								 * implements AdministradorNegocioRemote,
								 * AdministradorNegocioLocal
								 */{

	/**
	 * Default constructor.
	 */
	/*
	 * public AdministradorNegocio() { admin = new Administrador(); }
	 */

	@EJB
	AdministradorDAO adminDAO;

	// private Administrador admin;

	// @Override
	/*
	 * public boolean altaAdmin(String nombre, String email, String password) {
	 * boolean altaExitosa = false; admin.setNombre(nombre);
	 * admin.setEmail(email); admin.setPassword(password);
	 * 
	 * try { adminDAO.insertarAdministrador(admin); } catch (Exception e) {
	 * e.printStackTrace(); }
	 * 
	 * altaExitosa = true; return altaExitosa; }
	 */

	public boolean altaAdmin(DataAdministrador dadmin) {

		Administrador admin = new Administrador();

		admin.setNombre(dadmin.getNombre());
		admin.setEmail(dadmin.getEmail());
		admin.setPassword(dadmin.getPassword());
		admin.setTelefono(dadmin.getTelefono());

		try {
			adminDAO.insertarAdministrador(admin);
			return true;

		} catch (Exception e) {

			e.printStackTrace();
			return false;
		}
	}

	// @Override
	/*
	 * public boolean login(String email, String password){ boolean loginOK =
	 * false; Administrador administrador = new Administrador();
	 * administrador.setEmail(email); administrador.setPassword(password);
	 * 
	 * try { loginOK = adminDAO.login(administrador); } catch (Exception e) {
	 * e.printStackTrace(); } return loginOK; }
	 */
	public boolean login(DataAdministrador dadmin) {

		Administrador admin = new Administrador();
		// admin.setNombre(dadmin.getNombre() );
		admin.setEmail(dadmin.getEmail());
		admin.setPassword(dadmin.getPassword());

		boolean loginOK = false;
		/*
		 * Administrador administrador = new Administrador();
		 * administrador.setEmail(email); administrador.setPassword(password);
		 */
		try {
			loginOK = adminDAO.login(admin);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return loginOK;
	}

	public void insertAdmin() {
		boolean hay = this.adminDAO.hayAdministradores();
		if(!hay){
			Administrador admin = new Administrador();
			admin.setEmail("admin");
			admin.setEstaActivo(true);
			admin.setFecha(new Date());
			admin.setNombre("admin");
			admin.setTelefono(555555555);
			admin.setPassword("1a1dc91c907325c69271ddf0c944bc72");
			this.adminDAO.insertarAdministrador(admin);
		}
	}
	/*
	 * public Administrador getAdmin() { return admin; }
	 * 
	 * public void setAdmin(Administrador admin) { this.admin = admin; }
	 */
}
