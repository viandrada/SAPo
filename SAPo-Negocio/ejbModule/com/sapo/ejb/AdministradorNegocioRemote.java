package com.sapo.ejb;

import javax.ejb.Remote;


@Remote
public interface AdministradorNegocioRemote {
	public boolean altaAdmin(String nombre, String email, String password);
	public boolean login(String email, String password);
}
