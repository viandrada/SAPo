package com.sapo.ejb;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.NamingException;

import com.sapo.dao.AdministradorDAO;
import com.sapo.dao.AdministradorDAORemote;
import com.sapo.datatypes.Administrador;
import com.sapo.utils.JNDILookup;

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
		//adminDAO = new AdministradorDAO();
		admin = new Administrador();
	}

	//@EJB
	//private AdministradorDAO adminDAO;
	private Administrador admin;
	
	AdministradorDAORemote manager = null;
	Context context = null;

	@Override
	public boolean altaAdmin(String nombre, String email, String password) {
		boolean altaExitosa = false;
		admin.setNombre(nombre);
		admin.setEmail(email);
		admin.setPassword(password);
		// try {
		try {
			context = JNDILookup.getInitialContext();
			manager = (AdministradorDAORemote) context
					.lookup("ejb:SAPo-EAR/SAPo-AccesoDatos//AdministradorDAO!com.sapo.dao.AdministradorDAORemote");
			manager.insertarAdministrador(admin);
			//manager.altaAdmin(this.nombre,this.email,this.password);
		} catch (NamingException e) {
			e.printStackTrace();
		}
		//adminDAO.insertarAdministrador(admin);
		altaExitosa = true;
		// } catch (Exception e) {
		// TODO: handle exception
		// }
		return altaExitosa;
	}

/*
	public AdministradorDAO getAdminDAO() {
		return adminDAO;
	}

	public void setAdminDAO(AdministradorDAO adminDAO) {
		this.adminDAO = adminDAO;
	}
*/
	public Administrador getAdmin() {
		return admin;
	}

	public void setAdmin(Administrador admin) {
		this.admin = admin;
	}

}
