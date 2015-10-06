package com.sapo.dao;

import javax.ejb.Remote;

import com.sapo.entidades.Administrador;

@Remote
public interface AdministradorDAORemote {
	public Administrador getAdministrador(String email);
	public boolean existeAdministrador(String email);
	public void insertarAdministrador(com.sapo.datatypes.Administrador a);
	public void actualizarAdministrador(Administrador a);
}
