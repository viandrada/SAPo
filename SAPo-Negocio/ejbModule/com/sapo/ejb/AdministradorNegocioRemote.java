package com.sapo.ejb;

import javax.ejb.Remote;

import com.sapo.datatypes.DataAdministrador;

@Remote
public interface AdministradorNegocioRemote {
	public boolean altaAdmin(String nombre, String email, String password);
}
