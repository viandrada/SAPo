package com.sapo.ejb;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.datatypes.DataUsuario;
import com.sapo.dao.UsuarioDAO;
import com.sapo.entidades.Usuario;

/**
 * Session Bean implementation class UsuarioNegocio
 */
@Stateless
@LocalBean
public class UsuarioNegocio {

    /**
     * Default constructor. 
     */
    public UsuarioNegocio() {
        usuario = new Usuario();
    }

	@EJB
	private UsuarioDAO usuarioDAO;
	
	private Usuario usuario;
	
	public boolean altaUsuario(DataUsuario dataUsuario) {
		boolean altaExitosa = false;
		usuario.setNombre(dataUsuario.getNombre());
		usuario.setEmail(dataUsuario.getEmail());
		usuario.setPassword(dataUsuario.getPassword());
		
		usuario.setEstaActivo(true);
		usuario.setMonto(0f);
		usuario.setPremium(false);
		
		try {
			usuarioDAO.insertarUsuario(usuario);
			altaExitosa = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return altaExitosa;
	}
	
	public boolean login(String email, String password){
		boolean loginOK = false;
		Usuario usuario = new Usuario();
		usuario.setEmail(email);
		usuario.setPassword(password);
		try {
			loginOK = usuarioDAO.login(usuario);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return loginOK;
	}
}
