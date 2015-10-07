package com.sapo.ejb;

import javax.ejb.Local;


@Local
public interface AdministradorNegocioLocal {
	public boolean altaAdmin(String nombre, String email, String password);
	public boolean login(String email, String password);
}
