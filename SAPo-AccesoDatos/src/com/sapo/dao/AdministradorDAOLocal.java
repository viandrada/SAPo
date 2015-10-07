package com.sapo.dao;

import javax.ejb.Local;

import com.sapo.datatypes.DataAdministrador;
import com.sapo.entidades.Administrador;

@Local
public interface AdministradorDAOLocal {

	public Administrador getAdministrador(String email);
	public boolean existeAdministrador(String email);
	public void insertarAdministrador(DataAdministrador admin);
	public void actualizarAdministrador(Administrador a);
}
