package com.sapo.ejb;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.datatypes.DataAlmacen;
import com.datatypes.DataUsuario;
import com.sapo.dao.UsuarioDAO;
import com.sapo.entidades.Usuario;
import com.sapo.utils.Fabrica;


@Stateless
@LocalBean
public class ReporteNegocio {

	/**
	 * Default constructor.
	 */
	public ReporteNegocio() {
		
	}

	@EJB
	private UsuarioDAO usuarioDAO;
	
	public float ganancias(){
		
		Fabrica f = new Fabrica();
		List<DataUsuario> dataUsrLista = new ArrayList<DataUsuario>();
		dataUsrLista = f.convertirUsu(this.usuarioDAO.getUsuarios());
		
		float gana = 0;
		
		for (int i = 0; i < dataUsrLista.size(); i++) {
			
			if  (dataUsrLista.get(i).isPremium()){
				
				gana = gana + dataUsrLista.get(i).getMonto();
			}
		};
		return gana;
	}

//	private Usuario usuario;

/*	public boolean altaUsuario(DataUsuario dataUsuario) {
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
			}else if ((existeUsuario != null && existeUsuario.getPassword() == null)) {// Si existe pero no tiene password porque se logue� con terceros, se actualiza.
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
	}*/

	/*public boolean login(String email, String password) {
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
	}*/
/*	



	public boolean pasarAPremium(String usuarioLogueado) {
		
		usuario = usuarioDAO.getUsuarioPorEmail(usuarioLogueado); 
		usuario.setPremium(true);
		
		try {
			usuarioDAO.actualizarUsuario(usuario);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
	public DataUsuario getUsuarioPorEmail(String email){
		Fabrica f = new Fabrica();
		DataUsuario dataUser = f.convertirUserAData(usuarioDAO.getUsuarioPorEmail(email));
		return dataUser;
	}
	
	public DataUsuario getUsuarioPorId(int id){
		Fabrica f = new Fabrica();
		DataUsuario dataUser = f.convertirUserAData(usuarioDAO.getUsuario(id));
		return dataUser;
		
	}
	
	public List<DataUsuario> getUsuarios(){
		Fabrica f = new Fabrica();
		List<DataUsuario> dataUsrLista = new ArrayList<DataUsuario>();
		dataUsrLista = f.convertirUsu(this.usuarioDAO.getUsuarios());
		return dataUsrLista;
	}*/
}
