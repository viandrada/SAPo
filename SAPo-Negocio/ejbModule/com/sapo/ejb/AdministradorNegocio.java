package com.sapo.ejb;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

import com.sapo.dao.AdministradorDAO;
import com.sapo.datatypes.DataAdministrador;

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
	}

	@EJB(beanName="adminDAO")
	private AdministradorDAO adminDAO;
	@Inject
	private DataAdministrador admin;
	

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


	public AdministradorDAO getAdminDAO() {
		return adminDAO;
	}

	public void setAdminDAO(AdministradorDAO adminDAO) {
		this.adminDAO = adminDAO;
	}


	public DataAdministrador getAdmin() {
		return admin;
	}


	public void setAdmin(DataAdministrador admin) {
		this.admin = admin;
	}


}
