package com.sapo.ejb;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.datatypes.DataUsuario;
import com.sapo.dao.UsuarioDAO;
import com.sapo.entidades.Imagen;
import com.sapo.entidades.Usuario;
import com.sapo.utils.Fabrica;

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
		usuario.setEstilo(dataUsuario.getEstilo());

		usuario.setEstaActivo(true);
		usuario.setMonto(0f);
		usuario.setPremium(false);
		usuario.setFecha(new Date());

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
				existeUsuario.setFecha(new Date());
				existeUsuario.setEstilo(usuario.getEstilo());
				
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
		usuarioPersistir.setPremium(false);
		usuarioPersistir.setEstilo("areaTrabajo.css");
		usuarioPersistir.setEstaActivo(true);
		usuarioPersistir.setFecha(new Date());
		usuarioPersistir.setNombre(dataUsuario.getEmail().split("@")[0]);
		
		
		
		//usuarioPersistir.setLatitud(dataUsuario.getLatitud());
		//usuarioPersistir.setLongitud(dataUsuario.getLongitud());
		
		
		
		this.altaUsuario(usuarioPersistir);
		
		usuario = usuarioDAO.getUsuarioPorEmail(dataUsuario.getEmail());
		
		
		actualizarPosicion(dataUsuario.getLatitud(), dataUsuario.getLongitud(),
				usuario.getIdUsuario());
		
		
		
		DataUsuario dUsuario = new DataUsuario();
		dUsuario.setEmail(usuario.getEmail());
		dUsuario.setFecha(usuario.getFecha());
		dUsuario.setIdUsuario(usuario.getIdUsuario());
		dUsuario.setNombre(usuario.getNombre());
		dUsuario.setEstaActivo(usuario.getEstaActivo());
		dUsuario.setEstilo(usuario.getEstilo());
		dUsuario.setMonto(usuario.getMonto());
		dUsuario.setPremium(usuario.isPremium());
		
		dUsuario.setLatitud(usuario.getLatitud());
		dUsuario.setLongitud(usuario.getLongitud());
		
		
		return dUsuario;
	}

	public boolean pasarAPremium(String usuarioLogueado) {
		
		usuario = usuarioDAO.getUsuarioPorEmail(usuarioLogueado); 
		usuario.setFechaPago(new Date());
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
	}
	
	public void guardarEstilo(int idUsuario, String estilo){
		Usuario u = new Usuario();
		u = this.usuarioDAO.getUsuario(idUsuario);
		u.setEstilo(estilo);
		this.usuarioDAO.actualizarUsuario(u);
	}
	
	public boolean existeUsuario(String email){
		return usuarioDAO.existeUsuario(email);
	}
	
	public void guardarCambios(DataUsuario d){
		Usuario u = new Usuario();
		u = this.usuarioDAO.getUsuario(d.getIdUsuario());
		u.setEmail(d.getEmail());
		u.setNombre(d.getNombre());
		if(d.getBytesFoto()!= null){
			Imagen img = new Imagen();
			img.setDatos(d.getBytesFoto());
			u.setFoto(img);
		}
		this.usuarioDAO.actualizarUsuario(u);
	}
	
	public void cambiarPassword(DataUsuario d){
		Usuario u = new Usuario();
		u = this.usuarioDAO.getUsuario(d.getIdUsuario());
		u.setPassword(d.getPassword());
		this.usuarioDAO.actualizarUsuario(u);
	}
	
	public void actualizarPosicion(double latitud, double longitud, int idUsuario){
		Usuario u = new Usuario();
		u = this.usuarioDAO.getUsuario(idUsuario);
		u.setLatitud(latitud);
		u.setLongitud(longitud);
		this.usuarioDAO.actualizarUsuario(u);
		
	}
}
