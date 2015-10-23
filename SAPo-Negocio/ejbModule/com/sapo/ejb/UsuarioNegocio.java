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
			Usuario existeUsuario = usuarioDAO.getUsuarioPorEmail(dataUsuario
					.getEmail());
			if (existeUsuario == null) {// Si no existe se da de alta.
				usuarioDAO.insertarUsuario(usuario);
			}else if ((existeUsuario != null && existeUsuario.getPassword() == null)) {// Si existe pero no tiene password porque se logueó con terceros, se actualiza.
				existeUsuario.setEmail(usuario.getEmail());
				existeUsuario.setPassword(usuario.getPassword());
				existeUsuario.setEstaActivo(true);
				existeUsuario.setNombre(usuario.getNombre());
				
				usuarioDAO.insertarUsuario(existeUsuario);
			}
			
			altaExitosa = true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return altaExitosa;
	}

	public boolean login(String email, String password) {
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

	public DataUsuario loginExterno(DataUsuario dataUsuario) {
		Usuario usuario = new Usuario();
		DataUsuario usuarioPersistir = new DataUsuario();
		usuarioPersistir.setEmail(dataUsuario.getEmail());
		/*usuario = usuarioDAO.getUsuarioPorEmail(dataUsuario.getEmail());
		if (usuario.getEmail() == null) {
			Usuario usu = new Usuario();
			usu.setEmail(dataUsuario.getEmail());
			usuarioDAO.insertarUsuario(usu);
		}*/
		this.altaUsuario(usuarioPersistir);
		usuario = usuarioDAO.getUsuarioPorEmail(dataUsuario.getEmail());
		DataUsuario dUsuario = new DataUsuario();
		dUsuario.setEmail(usuario.getEmail());
		dUsuario.setFecha(usuario.getFecha());
		dUsuario.setIdUsuario(usuario.getIdUsuario());
		dUsuario.setNombre(usuario.getNombre());
		
		return dUsuario;
	}
}
