package com.sapo.ejb;

import javax.ejb.Local;

import com.sapo.datatypes.DataAdministrador;

@Local
public interface AdministradorNegocioLocal {
	public boolean altaAdmin(String nombre, String email, String password);
}
